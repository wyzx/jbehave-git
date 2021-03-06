package org.jbehave.examples.trader.stories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import org.jbehave.core.JUnitStory;
import org.jbehave.core.MostUsefulStoryConfiguration;
import org.jbehave.core.StoryConfiguration;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.parser.UnderscoredCamelCaseResolver;
import org.jbehave.core.reporters.PrintStreamOutput;
import org.jbehave.core.steps.StepsFactory;

public class PriorityMatching extends JUnitStory {

    public PriorityMatching() {
        StoryConfiguration storyConfiguration = new MostUsefulStoryConfiguration();
        storyConfiguration.useStoryPathResolver(new UnderscoredCamelCaseResolver(".story"));        
        storyConfiguration.useStoryReporter(new PrintStreamOutput());
        useConfiguration(storyConfiguration);

        addSteps(new StepsFactory().createCandidateSteps(new PriorityMatchingSteps()));

    }
    
    public static class PriorityMatchingSteps {

        private String param;

        @Given("a step that has $param")
        public void has(String param){
            this.param = param;
        }
        
        @Given(value="a step that has exactly one $param", priority=1)
        public void hasExactlyOne(String param){
            this.param = param;
        }

        @Then("the parameter value is \"$param\"")
        public void theParamValue(String param){
           ensureThat(this.param, equalTo(param));
        }

    }

}
