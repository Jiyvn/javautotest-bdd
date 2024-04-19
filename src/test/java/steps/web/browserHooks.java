package steps.web;

import auto.actions.Capturer;
import auto.exceptions.JavAutoException;
import helper.cucumberHelper;
import helper.uiAutoHelper;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import auto.propertyLoader;
import utils.image.imgIO;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class browserHooks {
    public static Logger log = LoggerFactory.getLogger(browserHooks.class);

    protected String defaultBrowser = System.getProperty("browser", "firefox");
    protected Scenario scenario;
    protected String pageSource;

    @Before(order = 1, value = "@web")
    public void beforeScenario(){
//        log.info("class: " + browserHooks.class.getName());
        propertyLoader.setDriverExecutor();
        uiAutoHelper.setDesiredCaps(new DesiredCapabilities());
        uiAutoHelper.setBrowser(defaultBrowser.toLowerCase());
        this.scenario = cucumberHelper.getScenario();
        this.pageSource = "";
//        log.info("before --> scenario: " + this.scenario.getName());

    }

    @After(order = 1, value = "@web")
    public void afterScenario(){
//        log.info("class: " + browserHooks.class.getName());
//        log.info("after --> scenario: " + this.scenario.getName());
        log.info("Stopped on Step: " + cucumberHelper.getStepName());
        uiAutoHelper.quit();
        uiAutoHelper.reset();
    }

    @After(order = 10001, value = "@web")
    public void takeScreenshot(){
        Status state = cucumberHelper.getScenario().getStatus();
        try {
//            if (state.equals(Status.FAILED) || state.equals(Status.UNDEFINED)) {
            if (state.equals(Status.FAILED)) {
                uiAutoHelper.attachImage(cucumberHelper.getScenario().getName() + "__" + cucumberHelper.getStep().getStep().getText());

//                // for reportportal https://github.com/reportportal/logger-java-logback
                  // two model
                  // RP_MESSAGE#FILE#FILENAME#MESSAGE_TEST
                  // RP_MESSAGE#BASE64#BASE_64_REPRESENTATION#MESSAGE_TEST
//                String scrFile = scenario.getName() + "_" + scenario.getStatus()+ "_"+ Calendar.getInstance().getTime().getTime()/1000+ ".png";
//                imgIO.bytesToFileViaStream(new Capturer().captureFullScreen(uiAutoHelper.getDriver()), new File(scrFile));
//                log.info("RP_MESSAGE#FILE#{}#{}", scrFile, "after_takeScreenshot_upload_with_file");
//
//                log.info(
//                        "RP_MESSAGE#FILE#{}#{}",
//                        imgIO.createTempPng(new Capturer().captureFullScreen(uiAutoHelper.getDriver()), "after_takeScreenshot_upload_with_file"),
//                        "after_takeScreenshot_upload_with_file");
//                log.info("RP_MESSAGE#BASE64#{}#{}", new Capturer().captureFullScreen(uiAutoHelper.getDriver()), "after_takeScreenshot_upload_with_base64");
//
            }
        } catch (WebDriverException e) {
            e.printStackTrace(System.err);
        }
    }

    @After(order = 10000, value = "@web")
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

    @After(order = 2, value = "@web")
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
