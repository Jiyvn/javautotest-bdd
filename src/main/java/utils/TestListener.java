package utils;

import common.AppiumService;
import common.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.annotations.AfterSuite;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class TestListener implements ITestListener {
    public static Logger log = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
        log.info("context.getStartDate: "+context.getStartDate());
        File allureResultDir = new File(System.getProperty("user.dir")+"/allure-results");
//        if (allureResultDir.exists()){
////            log.info("result existed");
//            File[] files = allureResultDir.listFiles();
//            if (files != null && files.length > 0) {
//                for (File temp : files) {
//                    temp.delete();
//                }
//            }
//        }
//        Map<String, String> params = context.getCurrentXmlTest().getAllParameters();

//        log.info("getOutputDirectory: "+context.getOutputDirectory());
//        log.info("getName: "+context.getName());
//        log.info("getCurrentXmlTest: "+context.getCurrentXmlTest());
//        log.info("SuiteName: "+context.getCurrentXmlTest().getSuite().getName());
//        log.info("params: " + params);
//        log.info("getParallel: "+context.getCurrentXmlTest().getParallel());

    }

    @Override
    public void onTestFailure(ITestResult result) {
//        Object driver = result.getTestContext().getAttribute("driver");

    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        try {
//            log.info("runnerName: " + context.getAllTestMethods()[0].getInstance().getClass().getName());
            log.info("SuiteName: "+context.getCurrentXmlTest().getSuite().getName());
//            log.info("xmlTest: " + context.getCurrentXmlTest());
//            log.info("context.getAttributeNames: " + context.getAttributeNames());
//            log.info("context.getSuite: " + context.getSuite());
//            log.info("context.getHost: " + context.getHost());
//            log.info("context.getFailedTests: " + context.getFailedTests());
//            log.info("context.getPassedTests: " + context.getPassedTests());
//            Report.setReport(context.getCurrentXmlTest().getSuite().getName());
            Report.Clean();
            Report.dumpReport();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class TestAdapter extends TestListenerAdapter{

    public void testContexts(){
        super.getTestContexts();
    }

}

class TestNGListener implements ITestNGListener {

}