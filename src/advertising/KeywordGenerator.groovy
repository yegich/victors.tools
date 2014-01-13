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
        if(keywordType == NAME){
            def names = []
            keywords[keywordType].each { name ->
                names << name
                names.addAll(modifyNamedKeywords(name))
            }
            return names
        }
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

   private boolean hasTypedKeywords(KeywordType type) {
       return keywords[type]
   }

   def skipInvalidTemplates() {
       def validTemplates = []
       templates.each { template ->
           boolean isValid = true
           template.getSequence().each {
               isValid &= hasTypedKeywords(it)
           }
           if(isValid) {
               validTemplates << template
           }
       }
       return validTemplates
   }

    List<String> modifyNamedKeywords(def name) {
        def modifications = []
        patternsWithTemplates.each { pattern, templates ->
            if (name ==~ pattern) {
                templates.each { template ->
                    modifications << name.replaceAll(pattern, template)
                }
            }
        }
        return modifications
    }

    List<String> generateKeyphrases() {
        def keyphrases = []
        skipInvalidTemplates().each { template ->
            recursivePhraseGeneration(template.getSequence(), null, null, keyphrases)
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