package advertising

import static advertising.KeywordType.*
import static advertising.TemplateWordType.*

def generator = new KeywordGenerator()

generator.keywords = [
        (AREA):[
            "тренажеры", "спотривный инвентарь", "сопртивное оборудование", "снаряды для спорта"
        ],
        (GOODS_GROUP):[
                "беговые дорожки","велотренажер", "орбитреки", "тренажеры для гребли"
        ],
        (NAME):["USA STYLE SS-778", "USA Style SS-82000", "Life Gear 93570 MERCURY", "USA Style SS-7888", "Torneo Vento C-208", "Life Gear Evoke 93296"]
]

generator.templateWords = [
        (ACTION):["найти", "купить", "подобрать", "сравнить", "выбрать" ],
        (LOCATION):["киев","киевская область", "днепропетровск", "днепропетровская область", "полтава", "полтавская область"],
        (PLACE):["форум", "интернет магазин", "магазин", "склад", "поставщики"],
        (DESCRIPTON):["недорогой", "качественный", "эффективый"]
]

def keyphrases = generator.generateKeyphrases()

new File("d:/keyphrases.csv").withWriter("ASCII") {out ->
    keyphrases.each { phrase ->
        out.writeLine("${phrase}")
    }
}