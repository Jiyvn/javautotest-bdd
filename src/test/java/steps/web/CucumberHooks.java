package steps.web;

import helper.cucumberHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.invoke.MethodHandles.lookup;


public class CucumberHooks {
    static Logger log = LoggerFactory.getLogger(lookup().lookupClass());

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
//        log.info("class: " + CucumberHooks.class.getName());
        cucumberHelper.setScenario(scenario);
        log.info("feature: \"" + scenario.getUri() + "\""); // feature file
        log.info("getName: \"" + scenario.getName() + "\""); // scenario
        log.info("getId: \"" + scenario.getId() + "\"");
        log.info("getLine: \"" + scenario.getLine() + "\""); // line number in feature file
        log.info("getSourceTagNames: \"" + scenario.getSourceTagNames() + "\""); // tags
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        cucumberHelper.resetScenario();
    }

}


