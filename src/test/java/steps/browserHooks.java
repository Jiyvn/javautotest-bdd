package steps;

import helper.cucumberHelper;
import helper.uiAutoHelper;
import io.cucumber.java.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.capturer;
import utils.configLoader;

public class browserHooks {

    protected String defaultBrowser = System.getProperty("browser", "firefox");
    protected Scenario scenario;
    public static Logger log = LoggerFactory.getLogger(browserHooks.class);

    @Before(order = 1)
    public void beforeScenario(){
//        log.info("class: " + browserHooks.class.getName());
        configLoader.setProperties();
        uiAutoHelper.setDesiredCaps(new DesiredCapabilities());
        uiAutoHelper.setBrowser(defaultBrowser.toLowerCase());
        this.scenario = cucumberHelper.getScenario();
//        log.info("before --> scenario: " + this.scenario.getName());

    }

    @After(order = 1)
    public void afterScenario(){
//        log.info("class: " + browserHooks.class.getName());
//        log.info("after --> scenario: " + this.scenario.getName());
        log.info("Stopped on Step: " + cucumberHelper.getStepName());
        cucumberHelper.takeScreenshotIfFailed(
                uiAutoHelper.getDriver(),
                this.scenario.getName()+ "_" + cucumberHelper.getStepName()
        );
        uiAutoHelper.quit();
        uiAutoHelper.reset();
    }

//    @AfterStep
//    public void afterStep(){
////        log.info("afterStep -- stepName -->  " + cucumberHelper.getStepName());
//        if (cucumberHelper.getFirstly()) {
//            cucumberHelper.setFirstly(false);
//        }
//    }
//    @BeforeStep
//    public void beforeStep(){
//        if (!cucumberHelper.getFirstly()) {
////            log.info("beforeStep -- stepName -->  " + cucumberHelper.getStepName());
//        }else{
////            log.info("beforeStep - first running");
//        }
//
//    }

}
