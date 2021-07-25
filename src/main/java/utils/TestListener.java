package utils;

import common.AppiumService;
import common.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.annotations.AfterSuite;

import java.io.IOException;

public class TestListener implements ITestListener {
    public static Logger log = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
        log.info("context: "+context);

    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object driver = result.getTestContext().getAttribute("driver");
        log.info("driver: "+ driver);

    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        try {
            Report.dumpReport();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class TLAdapter extends TestListenerAdapter{

}

class TestNGListener implements ITestNGListener {

}