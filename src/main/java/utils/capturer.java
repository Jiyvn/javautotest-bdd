package utils;

import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class capturer {

    protected WebDriver driver;
    protected Scenario scenario;

    public capturer setDriver(WebDriver driver){
        this.driver = driver;
        return this;
    }

    public capturer setScenario(Scenario scenario){
        this.scenario = scenario;
        return this;
    }

    public void takeScreenshotIfFailed(){
        takeScreenshotIfFailed(
                this.scenario.getName().replace(":", "-") + "_fail_" + new SimpleDateFormat("yyyyMMdd-HHmm-ss.SSS").format(new Date())
        );
    }

    public void takeScreenshotIfFailed(String Name){
        Status state = this.scenario.getStatus();
        try {
//            if (state.equals(Status.FAILED) || state.equals(Status.UNDEFINED)) {
            if (state.equals(Status.FAILED)) {
                takeScreenshot(Name);
            }
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }


    public void takeScreenshot(String name){
        takeScreenshot(this.driver, name);
    }

    public void takeScreenshot(WebDriver driver, String name){
//        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//        this.scenario.attach(screenshot, "image/png", name);
        takeScreenshot(this.scenario, driver, name);
    }

    public static void takeScreenshot(Scenario scenario, WebDriver driver, String name){
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", name);
    }

    public static void takeScreenshotIfFailed(WebDriver driver, Scenario scenario, String Name){
        new capturer().setDriver(driver).setScenario(scenario).takeScreenshotIfFailed(Name);
    }

}
