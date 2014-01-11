package advertising

import static advertising.KeywordType.ACTION
import static advertising.KeywordType.AREA
import static advertising.KeywordType.LOCATION
import static advertising.Template.aTemplate;

/**
 * Created by user on 02.01.14.
 */
public class KeywordGeneratorProtoype {

    static final def KEYWORD_WITH_TYPE_NAME = 'USA STYLE SS-82001'
    static final def KEYWORD_WITHOUT_LAST_SPACE = 'USA STYLESS-82001'
    static final def KEYWORD_WITHOUT_FIRST_SPACE = 'USASTYLE SS-82001'


    static final Template THREE_TYPE_TEMPLATE = aTemplate().withType(AREA).and().withType(ACTION).and().withType(LOCATION).build()

    static final def SIMPLE_GENERATOR = new KeywordGenerator(
            keywords:[
                    (AREA):['тренажеры','сопртивное оборудование'],
                    (ACTION):['найти', 'купить'],
                    (LOCATION):['киев','нежин']
            ],
            templates:[THREE_TYPE_TEMPLATE])

    static final def PHRASES = [
            'тренажеры найти киев',
            'тренажеры найти нежин',
            'тренажеры купить киев',
            'тренажеры купить нежин',
            'сопртивное оборудование найти киев',
            'сопртивное оборудование найти нежин',
            'сопртивное оборудование купить киев',
            'сопртивное оборудование купить нежин'
    ]
}
