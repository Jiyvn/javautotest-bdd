package helper;

import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.PickleStepTestStep;

public class cucumberHelper {

    /*
     * cucumber
     */
    // get scenario
    public static final InheritableThreadLocal<Scenario> scenario = new InheritableThreadLocal<Scenario>() {
        @Override
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
    public static void resetScenario(){
        scenario.remove();
    }

    // get step
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

    public static void resetStep(){
        step.remove();
    }

    // get exception
    public static InheritableThreadLocal<Throwable> error = new InheritableThreadLocal<Throwable>() {
        @Override
        protected synchronized Throwable initialValue() {
            return null;
        }
    };
    public static Throwable getException(){
        return error.get();
    }
    public static void setException(Throwable throwable){
        error.set(throwable);
    }
    public static void resetException(){
        error.remove();
    }

    // attach file
    public static void attach(byte[] data, String mediaType, String fileName){
        getScenario().attach(data, mediaType, fileName);
    }

    // log message
    public static void log(String message){
        getScenario().log(message);
    }

}
