package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


// for class retry --> whole feature
class RetryAnalyzer implements IRetryAnalyzer {
    public static Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);
    private int runTimes = 0;
    private int maxRetries = Integer.parseInt(System.getProperty("maxRetries", "0"));

    @Override
    public boolean retry(ITestResult result) {
        log.trace("runTimes: "+ (runTimes+1));
        if (runTimes < maxRetries){
            runTimes++;
            log.trace("Retry: "+ runTimes);
            return true;
        }
        return false;
    }
}