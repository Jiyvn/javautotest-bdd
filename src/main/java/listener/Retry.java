package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


// for class retry --> whole feature
class Retry implements IRetryAnalyzer {
    public static Logger log = LoggerFactory.getLogger(Retry.class);
    private int retries = 0;
    private int maxRetry = Integer.parseInt(System.getProperty("retry", "0"));

    @Override
    public boolean retry(ITestResult result) {
        log.info("Fail: "+ retries);
        if (retries<=maxRetry){
            retries++;
            log.info("Retry: "+ retries);
            return true;
        }
        return false;
    }
}