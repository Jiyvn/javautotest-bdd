package listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestProgressListener implements ITestListener {
    private static final Logger log = LoggerFactory.getLogger(TestProgressListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        // Set unique threadId for each test method
        MDC.put("threadId", String.valueOf(Thread.currentThread().getId()));

        String scenarioName = result.getMethod().getMethodName();
        System.out.println("Starting scenario: " + scenarioName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Clear MDC context
        MDC.clear();

        String scenarioName = result.getMethod().getMethodName();
        System.out.println("Finished scenario: " + scenarioName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Clear MDC context
        MDC.clear();
        // Not used
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Not used
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used
    }

    @Override
    public void onStart(ITestContext context) {
        // Not used
    }

    @Override
    public void onFinish(ITestContext context) {
        // Not used
    }
}