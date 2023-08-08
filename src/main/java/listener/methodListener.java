package listener;

import io.cucumber.java.Scenario;
import io.cucumber.java.StepDefinitionAnnotation;

import io.cucumber.plugin.event.PickleStepTestStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;


public class methodListener implements IInvokedMethodListener {

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
        // Check if the test method uses a DataProvider
        Method testMethod = testResult.getMethod().getConstructorOrMethod().getMethod();
        DataProvider dataProviderAnnotation = testMethod.getAnnotation(DataProvider.class);
        if (dataProviderAnnotation != null) {
            // Set unique threadId for each thread created by the DataProvider
            MDC.put("threadId", String.valueOf(Thread.currentThread().getId()));
        }

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        // Check if the test method uses a DataProvider
        Method testMethod = testResult.getMethod().getConstructorOrMethod().getMethod();
        DataProvider dataProviderAnnotation = testMethod.getAnnotation(DataProvider.class);
        if (dataProviderAnnotation != null) {
            // Clear MDC context for each thread created by the DataProvider
            MDC.clear();
        }

    }


//    private Object getStepInstance(Scenario scenario) {
//        List<PickleStepTestStep> testSteps = ((PickleStepTestStep) scenario.getStepContext().getCurrentStep()).getMatchingStepDefinitions();
//        PickleStepDefinitionMatch match = (PickleStepDefinitionMatch) testSteps.get(0).getStepDefinitionMatch();
//        return match.getInstance();
//    }
}