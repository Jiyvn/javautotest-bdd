package steps.web;

import auto.exceptions.JavAutoException;
import helper.cucumberHelper;
import helper.uiAutoHelper;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import auto.propertyLoader;

public class browserHooks {
    public static Logger log = LoggerFactory.getLogger(browserHooks.class);

    protected String defaultBrowser = System.getProperty("browser", "firefox");
    protected Scenario scenario;
    protected String pageSource;

    @Before(order = 1)
    public void beforeScenario(){
//        log.info("class: " + browserHooks.class.getName());
        propertyLoader.setProperties();
        uiAutoHelper.setDesiredCaps(new DesiredCapabilities());
        uiAutoHelper.setBrowser(defaultBrowser.toLowerCase());
        this.scenario = cucumberHelper.getScenario();
        this.pageSource = "";
//        log.info("before --> scenario: " + this.scenario.getName());

    }

    @After(order = 1)
    public void afterScenario(){
//        log.info("class: " + browserHooks.class.getName());
//        log.info("after --> scenario: " + this.scenario.getName());
        log.info("Stopped on Step: " + cucumberHelper.getStepName());
        uiAutoHelper.quit();
        uiAutoHelper.reset();
    }

    @After(order = 10001)
    public void takeScreenshot(){
        Status state = cucumberHelper.getScenario().getStatus();
        try {
//            if (state.equals(Status.FAILED) || state.equals(Status.UNDEFINED)) {
            if (state.equals(Status.FAILED)) {
                uiAutoHelper.attachImage(cucumberHelper.getScenario().getName() + "__" + cucumberHelper.getStep().getStep().getText());
            }
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }

    @After(order = 10000)
    public void getPageSource(){
        this.pageSource = uiAutoHelper.getDriver().getPageSource();
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

    @After(order = 2)
    public void assertPageSource(){
        if(cucumberHelper.getScenario().isFailed()
                && !(cucumberHelper.getException() instanceof JavAutoException)){
            // page source analysis
            // throw corresponding exception if something found
            cucumberHelper.log("cucumber log: instanceof JavAutoException");

        }else if(cucumberHelper.getScenario().isFailed()){
            cucumberHelper.log("case failed");
        }
    }

}
