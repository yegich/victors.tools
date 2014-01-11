package advertising

import static advertising.KeywordType.*

class KeywordGenerator {

    def keywords
    def templates

    KeywordGenerator() {
        keywords = [:]
    }

    def patternsWithTemplates = [
            /^([\w\d]+)\ +([\w\d]+)\ +([\w\d-]+)/:[
                    "\$1 \$2\$3",
                    "\$1\$2 \$3"],
            /^([\w\d]+)\ +([\w\d]+)\ +([\w\d]+)\ +([\w\d-]+)/:[
                    "\$1 \$2 \$3\$4",
                    "\$1 \$2\$3 \$4",
                    "\$1\$2 \$3 \$4"],
            /^([\w\d]+)\ +([\w\d-]+)/:[
                    "\$1\$2"],
            /(.*)(\-{1})(.*)/:[
                    "\$1\$3"]
    ]

    def getKeywordsByType(KeywordType keywordType) {
        return keywords[keywordType]
    }

    void addKeyword(KeywordType keywordType, String keyword) {
        initKyewordType(keywordType)
        keywords.get(keywordType).add(keyword)
    }

    private void initKyewordType(KeywordType keywordType) {
        if (!keywords.get(keywordType)) {
            keywords.put(keywordType, [])
        }
    }

    List<String> modifyNamedKeywords(def names) {
        def modifications = []
        names.each { name ->
            patternsWithTemplates.each { pattern, templates ->
                if (name ==~ pattern) {
                    templates.each { template ->
                        modifications.add(name.replaceAll(pattern, template))
                    }
                }
            }
        }
        return modifications
    }

    List<String> generateKeyphrases() {
        def allKeys = []
        getKeywordsByType(AREA).each {area ->
            allKeys.add(area)
            getKeywordsByType(GOODS_GROUP).each { goodsGroup ->
                allKeys.add("${area} ${goodsGroup}")
                allKeys.add(goodsGroup)
            }
        }

        getKeywordsByType(NAME).each {name ->
            allKeys.add(name)
            allKeys.addAll(modifyNamedKeywords([name]))
        }

        def allPhrases = []

        //TODO find better way to implement this
        recursivePhraseGeneration(allPhrases, ACTION, null, [ACTION,  DESCRIPTON, PLACE, LOCATION])
        recursivePhraseGeneration(allPhrases, DESCRIPTON, null, [ DESCRIPTON, PLACE, LOCATION])
        recursivePhraseGeneration(allPhrases, PLACE, null, [ PLACE, LOCATION])
        recursivePhraseGeneration(allPhrases, LOCATION, null, [ LOCATION])

        def keyphrases = []

        allKeys.each { key ->
            allPhrases.each{ phrase ->
                keyphrases.add("${key} ${phrase}")
            }
        }

        return  keyphrases
    }

    def recursivePhraseGeneration(List<KeywordType> sequence, KeywordType type, String processedWord, List<String> phrases) {

        if(!sequence) {
            if(processedWord) {
                phrases.add(processedWord.trim())
            }
            return
        }

        def newSequence = []
        type = type ? type : sequence.first()
        sequence.each{
            if(type != it && getKeywordsByType(it)) {
                newSequence << it
            }
        }

        def words = getKeywordsByType(type)
        words.each { word ->
            word = processedWord ?  "${processedWord} ${word}" : word
            recursivePhraseGeneration(newSequence, newSequence?newSequence.first():null, "${word}", phrases)
        }
    }
}