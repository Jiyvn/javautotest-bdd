package steps.web;

import helper.cucumberHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CucumberHooks {
    static Logger log = LoggerFactory.getLogger(CucumberHooks.class);

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
//        log.info("class: " + CucumberHooks.class.getName());
        cucumberHelper.setScenario(scenario);
        log.info("Feature in progress: \"" + scenario.getUri() + "\""); // feature file
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        cucumberHelper.resetScenario();
    }

}


