package helper;

import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.plugin.event.PickleStepTestStep;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import utils.capturer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class cucumberHelper {

    /*
     * cucumber
     */
    public static final InheritableThreadLocal<Scenario> scenario = new InheritableThreadLocal<Scenario>() {
        protected synchronized Scenario initialValue() {
            return null;
        }
    };

    public static Scenario getScenario() {
        return scenario.get();
    }

    public static void setScenario(Scenario sc) {
        scenario.set(sc);
    }


    public static InheritableThreadLocal<PickleStepTestStep> step = new InheritableThreadLocal<PickleStepTestStep>() {
        protected synchronized PickleStepTestStep initialValue() {
            return null;
        }
    };

    public static PickleStepTestStep getStep() {
        return step.get();
    }

    public static void setStep(PickleStepTestStep sc) {
        step.set(sc);
    }

    public static String getKeyword() {
        return step.get().getStep().getKeyword();
    }

    public static String getStepName() {
        return step.get().getStep().getText();
    }

    public static String getWholeStep() {
//        return getKeyword() + " " + getStepName();
        return getKeyword() + getStepName();
    }

    public static void resetScenario(){
        scenario.remove();
    }

    public static void resetStep(){
        step.remove();
    }
    public static void attach(byte[] imageBytes, String mediaType, String fileName){
        getScenario().attach(imageBytes, mediaType, fileName);
    }

    // full screen capture
    public static void attachImage(WebDriver driver){
        attachImage(driver, getScenario().getName() + "__" + getStep().getStep().getText());
    }

    public static void attachImage(WebDriver driver, String Name){
        attachImage(driver, "image/png", Name);
    }

    public static void attachImage(WebDriver driver, String mediaType, String Name){
        attach(new capturer().captureFullScreen(driver), mediaType, Name);
    }


    // WebElement capture
    public static void attachImage(WebElement element){
        attachImage(element, getScenario().getName() + "__" + getStep().getStep().getText());
    }

    public static void attachImage(WebElement element, String Name){
        attachImage(element, "image/png", Name);
    }

    public static void attachImage(WebElement element, String mediaType, String Name){
        attach(new capturer().takeScreenShot(element), mediaType, Name);
    }


    public static void takeScreenshotIfFailed(WebDriver driver){
        takeScreenshotIfFailed(driver,
                getScenario().getName().replace(":", "-") + "_fail_" + new SimpleDateFormat("yyyyMMdd-HHmm-ss.SSS").format(new Date())
        );
    }

    public static void takeScreenshotIfFailed(WebDriver driver, String Name){
        Status state = getScenario().getStatus();
        try {
//            if (state.equals(Status.FAILED) || state.equals(Status.UNDEFINED)) {
            if (state.equals(Status.FAILED)) {
                attachImage(driver, Name);
            }
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }
}
