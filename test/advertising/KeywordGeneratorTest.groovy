package advertising

import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test

import static advertising.KeywordType.*
import static advertising.KeywordType.AREA
import static advertising.TemplateWordType.*

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static org.junit.Assert.assertThat

/**
 * Created by user on 30.12.13.
 */
class KeywordGeneratorTest {

    KeywordGenerator generator

    @Before
    public void setUp() {
        generator = new KeywordGenerator()
    }

    @Test
    public void addKeyword() {
        generator.addKeyword(AREA, "тренажеры")
        assertThat(generator.getKyewordsByType(AREA), is(["тренажеры"]))
    }

    @Test
    public void addTemplateWord() {
        generator.addTemplateWord(ACTION, "найти")
        assertThat(generator.getTemplateWordsByType(ACTION), is(["найти"]))
    }

    @Test
    void initGenerator() {
        def generator = new KeywordGenerator()

        generator.keywords = [
                (AREA):["тренажеры", "спотривный инвентарь", "сопртивное оборудование", "снаряды для спорта"]
        ]

        generator.templateWords = [
                (ACTION):["найти", "купить", "подобрать", "сравнить", "выбрать" ]
        ]

        assertThat(generator.keywords[AREA], CoreMatchers.is(notNullValue()))
        assertThat(generator.templateWords[ACTION], CoreMatchers.is(notNullValue()))
    }

    @Test
    void


}
