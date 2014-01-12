package advertising

import org.hamcrest.collection.IsIterableContainingInOrder

import static advertising.KeywordGeneratorProtoype.SIMPLE_GENERATOR
import static advertising.KeywordGeneratorProtoype.getPHRASES
import static advertising.KeywordGeneratorProtoype.getSIMPLE_GENERATOR
import static advertising.KeywordGeneratorProtoype.getTHREE_TYPE_TEMPLATE
import static advertising.KeywordGeneratorProtoype.getTHREE_TYPE_TEMPLATE
import static advertising.KeywordGeneratorProtoype.getTHREE_TYPE_TEMPLATE
import static org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test

import static advertising.KeywordType.ACTION
import static advertising.KeywordType.AREA
import static advertising.Template.aTemplate

import static org.junit.Assert.assertThat

/**
 * Created by user on 30.12.13.
 */
class KeywordGeneratorTest {

    KeywordGenerator generator
    def phrases

    @Before
    public void setUp() {
        generator = new KeywordGenerator()
        phrases = []
    }

    @Test
    public void addKeyword() {
        generator.addKeyword(AREA, "тренажеры")
        assertThat(generator.getKeywordsByType(AREA), is(["тренажеры"]))
    }

    @Test
    void initGenerator() {
        def generator = new KeywordGenerator(
        keywords:[
                (AREA):["тренажеры", "спотривный инвентарь", "сопртивное оборудование", "снаряды для спорта"],
                (ACTION):["найти", "купить", "подобрать", "сравнить", "выбрать" ]
        ],
        templates:[aTemplate().build()])

        assertThat(generator.templates, is(notNullValue()))
        assertThat(generator.keywords[ACTION], is(notNullValue()))
    }

    @Test
    void generateKeypraseUsingTempalateRecursively() {
        SIMPLE_GENERATOR.recursivePhraseGeneration(THREE_TYPE_TEMPLATE.getSequence(), null, null, phrases)
        assertThat(phrases, IsIterableContainingInOrder.contains(PHRASES.toArray()))
    }

    @Test
    void generateKyephrases() {
        assertThat(SIMPLE_GENERATOR.generateKeyphrases(), IsIterableContainingInOrder.contains(PHRASES.toArray()))
    }


}
