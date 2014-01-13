package advertising

import org.hamcrest.collection.IsIterableContainingInAnyOrder
import org.hamcrest.collection.IsIterableContainingInOrder
import org.junit.Before
import org.junit.Test
import org.testng.reporters.jq.TestPanel

import static advertising.KeywordGeneratorProtoype.*
import static advertising.KeywordType.*
import static advertising.Template.aTemplate
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue
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
    void modifyKeywordWithTypeName() {
        generator.addKeyword(NAME, KEYWORD_WITH_TYPE_NAME)
        assertThat(generator.getKeywordsByType(NAME), IsIterableContainingInAnyOrder.containsInAnyOrder(
                KEYWORD_WITH_TYPE_NAME,
                KEYWORD_WITHOUT_FIRST_SPACE,
                KEYWORD_WITHOUT_LAST_SPACE,
                KEYWORD_WITHOUT_DASH))
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
        new KeywordGenerator(keywords:(KEYWORDS), templates:(TEMPLATES)).recursivePhraseGeneration(THREE_TYPE_TEMPLATE.getSequence(), null, null, phrases)
        assertThat(phrases, IsIterableContainingInOrder.contains(PHRASES.toArray()))
    }

    @Test
    void generateKyephrases() {
        assertThat(new KeywordGenerator(keywords:(KEYWORDS), templates:(TEMPLATES)).generateKeyphrases(), IsIterableContainingInOrder.contains(PHRASES.toArray()))
    }

    @Test
    void skipsTemplates(){
        def actualTemplates = new KeywordGenerator(keywords:(KEYWORDS), templates:(TEMPLATES))
                    .skipInvalidTemplates()
        assertThat(actualTemplates, is(IsIterableContainingInOrder.contains(THREE_TYPE_TEMPLATE)))
    }

}
