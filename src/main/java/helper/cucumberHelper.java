package helper;

import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.PickleStepTestStep;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

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
        firstly.remove();
    }
    public static void resetStep(){
        step.remove();
    }

    public static InheritableThreadLocal<Boolean> firstly = new InheritableThreadLocal<Boolean>() {
        protected synchronized Boolean initialValue() {
            return true;
        }
    };
    public static Boolean getFirstly() {
        return firstly.get();
    }

    public static void setFirstly(Boolean first) {
        firstly.set(first);
    }


    public static void attach(WebDriver driver){
        getScenario().attach(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png",
                getScenario().getName() + "___" + getStep().getStep().getText()
        );
    }

}
