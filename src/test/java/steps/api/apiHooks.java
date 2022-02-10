package steps.api;

import helper.cucumberHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class apiHooks {
    protected Scenario scenario;
    public static Logger log = LoggerFactory.getLogger(apiHooks.class);

    @Before(order = 1)
    public void beforeScenario(){
//        log.info("class: " + apiHooks.class.getName());
        this.scenario = cucumberHelper.getScenario();
//        log.info("before --> scenario: " + this.scenario.getName());

    }

    @After(order = 1)
    public void afterScenario(){
//        log.info("class: " + apiHooks.class.getName());
//        log.info("after --> scenario: " + this.scenario.getName());
        log.info("Stopped on Step: " + cucumberHelper.getStepName());
    }
}
