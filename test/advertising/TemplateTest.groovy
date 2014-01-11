package advertising

import org.hamcrest.CoreMatchers
import org.hamcrest.collection.IsIterableContainingInOrder
import org.junit.Test

import static advertising.KeywordType.ACTION
import static advertising.KeywordType.AREA
import static advertising.Template.aTemplate
import static org.junit.Assert.assertThat

/**
 * Created by user on 11.01.14.
 */
class TemplateTest {

    @Test
    void buildATemplate() {
        assertThat(aTemplate().build(), CoreMatchers.is(Template.class))
    }

    @Test
    void addTypeSquence() {
        def template = aTemplate().withType(AREA).and().withType(ACTION)
                .build()
        assertThat(template.getSequence(), IsIterableContainingInOrder.contains(AREA, ACTION))
    }
}
