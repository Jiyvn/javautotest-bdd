package steps.app;

import auto.exceptions.JavAutoException;
import helper.cucumberHelper;
import helper.uiAutoHelper;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.configLoader;

public class appHooks {
    public static Logger log = LoggerFactory.getLogger(appHooks.class);

    protected Scenario scenario;
    protected String pageSource;

    @Before(order = 1)
    public void beforeScenario(){
//        log.info("class: " + appHooks.class.getName());
//        configLoader.setProperties();
        uiAutoHelper.setDesiredCaps(new DesiredCapabilities());
        this.scenario = cucumberHelper.getScenario();
        this.pageSource = "";
//        log.info("before --> scenario: " + this.scenario.getName());

    }

    @After(order = 1)
    public void afterScenario(){
//        log.info("class: " + appHooks.class.getName());
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
        if(uiAutoHelper.getDriver() instanceof IOSDriver) {
            this.pageSource = uiAutoHelper.getDriver().getPageSource().replace("<", "*");
        }else {
            this.pageSource = uiAutoHelper.getDriver().getPageSource();
        }
    }

    @After(order = 2)
    public void assertPageSource(){
        if(cucumberHelper.getScenario().isFailed()
                && !(cucumberHelper.getException() instanceof JavAutoException)){
            // page source analysis
            // throw corresponding exception if something found
        }
    }
}
