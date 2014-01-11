package advertising

import org.jbehave.core.InjectableEmbedder
import org.jbehave.core.annotations.Configure
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.UsingEmbedder
import org.jbehave.core.annotations.UsingSteps
import org.jbehave.core.annotations.When
import org.jbehave.core.annotations.groovy.UsingGroovy
import org.jbehave.core.configuration.groovy.GroovyResourceFinder
import org.jbehave.core.io.CodeLocations
import org.jbehave.core.io.StoryFinder
import org.jbehave.core.junit.groovy.GroovyAnnotatedEmbedderRunner
import org.jbehave.core.model.ExamplesTable
import org.jbehave.core.reporters.StoryReporterBuilder
import org.junit.Assert
import org.junit.Test
import org.junit.matchers.JUnitMatchers
import org.junit.runner.RunWith

import static advertising.KeywordType.NAME
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.*

/**
 * Created by user on 07.01.14.
 */
@RunWith(GroovyAnnotatedEmbedderRunner.class)
@Configure(storyReporterBuilder = MyReportBuilder.class)
@UsingEmbedder()
@UsingSteps(instances = KeywordGeneratorBehaver.class)
@UsingGroovy(classLoader = GroovyClassLoader.class, resourceFinder = GroovyResourceFinder.class)
public class KeywordGeneratorBehaver extends InjectableEmbedder {

    @Test
    public void run() {
        injectedEmbedder().runStoriesAsPaths(storyPaths());
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder()
                .findPaths(CodeLocations.codeLocationFromClass(this.getClass()).getFile(), Arrays.asList("**/key_generator.story"), null);
    }

    public static class MyReportBuilder extends StoryReporterBuilder {
        public MyReportBuilder() {
            this.withFormats(CONSOLE, TXT, HTML, XML).withDefaultFormats();
        }
    }


    KeywordGenerator generator = new KeywordGenerator()
    def modifications = []

    @Given("keywords: \$keywordTable")
    public  void keywords (ExamplesTable keywordTable) {
        def rows = keywordTable.getRows()
        rows.each { row ->
            generator.addKeyword(KeywordType.valueOf(row.get("withType")), row.get("keyword"))
        }
    }

    @When("generate keyword modification")
    public  void generateKeywordModification () {
        modifications = generator.modifyNamedKeywords(generator.getKeywordsByType(NAME))
    }

    @Then("modifications returned are: \$modificationTable")
    public  void modificationsReturnedAre(ExamplesTable modificationTable) {
        Assert.assertThat(modifications, JUnitMatchers.hasItems(getColumnAsList(modificationTable, "modifications")))
    }

    private def getColumnAsList(ExamplesTable examplesTable, String columnName) {
        def list = [];
        def rows = examplesTable.getRows()
        rows.each { row ->
            list.add(row.get(columnName))
        }
        return list.toArray()
    }
}