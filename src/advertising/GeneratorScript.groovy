package advertising

import static advertising.KeywordType.*
import static advertising.Template.aTemplate

def generator = new KeywordGenerator(
    keywords : [
        (AREA):[

        ],
        (GOODS_GROUP):[
                "беговые дорожки"
        ],
        (NAME):[],

        (ACTION):["найти", "купить", "подобрать", "сравнить", "выбрать" ],
        (LOCATION):["киев"],
        (PLACE):["форум", "интернет магазин", "магазин", "склад", "поставщики"],
        (DESCRIPTON):["недорогие", "качественные", "эффективые","надежные","долговечные","американские","профессиональные", "домашние"]
    ],
    templates: [
            aTemplate().withType(PLACE).and().withType(GOODS_GROUP).and().withType(ACTION).and().withType(LOCATION).build(),
            aTemplate().withType(DESCRIPTON).and().withType(GOODS_GROUP).and().withType(ACTION).and().withType(LOCATION).build(),
            aTemplate().withType(AREA).and().withType(ACTION).build(),
            aTemplate().withType(AREA).and().withType(LOCATION).build(),
            aTemplate().withType(AREA).and().withType(PLACE).build(),
            aTemplate().withType(DESCRIPTON).and().withType(AREA).build(),
            aTemplate().withType(GOODS_GROUP).and().withType(ACTION).and().withType(LOCATION).build(),
            aTemplate().withType(GOODS_GROUP).and().withType(ACTION).and().withType(LOCATION).build(),aTemplate().withType(AREA).and().withType(ACTION).build(),
            aTemplate().withType(GOODS_GROUP).and().withType(LOCATION).build(),
            aTemplate().withType(GOODS_GROUP).and().withType(LOCATION).and().withType(PLACE).build(),
            aTemplate().withType(GOODS_GROUP).and().withType(PLACE).build(),
            aTemplate().withType(DESCRIPTON).and().withType(GOODS_GROUP).build(),
            aTemplate().withType(NAME).and().withType(ACTION).and().withType(LOCATION).build(),
            aTemplate().withType(NAME).and().withType(ACTION).and().withType(LOCATION).build(),aTemplate().withType(AREA).and().withType(ACTION).build(),
            aTemplate().withType(NAME).and().withType(LOCATION).build(),
            aTemplate().withType(NAME).and().withType(LOCATION).and().withType(PLACE).build(),
            aTemplate().withType(NAME).and().withType(PLACE).build(),
            aTemplate().withType(NAME).and().withType(ACTION).and().withType(LOCATION).build()
    ])

def keyphrases = generator.generateKeyphrases()

new File("d:/keyphrases.csv").withWriter() {out ->
    keyphrases.each { phrase ->
        out.writeLine("${phrase}")
    }
}