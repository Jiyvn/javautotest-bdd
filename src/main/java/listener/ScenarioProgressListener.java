package listener;

import io.cucumber.java.Scenario;
import io.cucumber.java.StepDefinitionAnnotation;

import io.cucumber.plugin.event.PickleStepTestStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;


public class ScenarioProgressListener implements IInvokedMethodListener {

    private static final Logger log = LoggerFactory.getLogger(TestProgressListener.class);

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        String scenarioName = method.getTestMethod().getMethodName();
//        method.getClass().getField("requestHelper");
        log.debug("Starting scenario: " + scenarioName);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        String scenarioName = method.getTestMethod().getMethodName();
        log.debug("Finished scenario: " + scenarioName);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        // Not used
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        // Not used
    }

//    private Object getStepInstance(Scenario scenario) {
//        List<PickleStepTestStep> testSteps = ((PickleStepTestStep) scenario.getStepContext().getCurrentStep()).getMatchingStepDefinitions();
//        PickleStepDefinitionMatch match = (PickleStepDefinitionMatch) testSteps.get(0).getStepDefinitionMatch();
//        return match.getInstance();
//    }
}