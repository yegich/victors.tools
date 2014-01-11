package advertising

import static advertising.KeywordType.*
import static advertising.TemplateWordType.ACTION
import static advertising.TemplateWordType.DESCRIPTON
import static advertising.TemplateWordType.LOCATION
import static advertising.TemplateWordType.PLACE

class KeywordGenerator {

    def keywords
    def templateWords

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

    def getKyewordsByType(KeywordType keywordType) {
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

    void addTemplateWord(TemplateWordType templateWordType, String word) {
        initTemplateWordType(templateWordType)
        templateWords.get(templateWordType).add(word)
    }

    List<String> getTemplateWordsByType(TemplateWordType templateWordType) {
        return templateWords.get(templateWordType)
    }


    private void initTemplateWordType(TemplateWordType templateWordType) {
        if (!templateWords.get(templateWordType)) {
            templateWords.put(templateWordType, [])
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
        getKyewordsByType(AREA).each {area ->
            allKeys.add(area)
            getKyewordsByType(GOODS_GROUP).each { goodsGroup ->
                allKeys.add("${area} ${goodsGroup}")
                allKeys.add(goodsGroup)
            }
        }

        getKyewordsByType(NAME).each {name ->
            allKeys.add(name)
            allKeys.addAll(modifyNamedKeywords([name]))
        }

        def allPhrases = []

        //TODO find better way to implement this
        recursivePrhaseGeneration(allPhrases, ACTION, null, [ACTION,  DESCRIPTON, PLACE, LOCATION])
        recursivePrhaseGeneration(allPhrases, DESCRIPTON, null, [ DESCRIPTON, PLACE, LOCATION])
        recursivePrhaseGeneration(allPhrases, PLACE, null, [ PLACE, LOCATION])
        recursivePrhaseGeneration(allPhrases, LOCATION, null, [ LOCATION])

        def keyphrases = []

        allKeys.each { key ->
            allPhrases.each{ phrase ->
                keyphrases.add("${key} ${phrase}")
            }
        }

        return  keyphrases
    }

    def recursivePrhaseGeneration(def allPhrases, TemplateWordType type, String processedWord, def orderedTemplateWordTypes) {
        def newOrder = []
        orderedTemplateWordTypes.each{if(type != it && getTemplateWordsByType(it)) newOrder << it}

        if(processedWord) {
            allPhrases.add(processedWord.trim())
        }

        if(!type) {
            return
        }

        def words = getTemplateWordsByType(type)
        words.each { word ->
            word = processedWord ?  "${processedWord} ${word}" : word
            recursivePrhaseGeneration(allPhrases, newOrder?newOrder.first():null, "${word}", newOrder)
        }
    }
}