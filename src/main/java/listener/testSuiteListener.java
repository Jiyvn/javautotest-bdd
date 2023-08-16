package listener;

import helper.testResultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class testSuiteListener  implements ISuiteListener {
    private static final Logger log = LoggerFactory.getLogger(testSuiteListener.class);


    @Override
    public void onStart(ISuite suite) {
        // Not used
    }

    @Override
    public void onFinish(ISuite suite) {
        testResultHelper.threadingResults.forEach((k, v)->{
            log.info("feature/api: "+k);
            v.forEach((innerKey, value)->{
                log.info(innerKey+": "+value.get());
            });
        });
        testResultHelper.overallResult.forEach((k, v)->{
            log.info(k+" total: "+v.get());
        });


//        ConcurrentHashMap<String, Map<String,Object>> mergedData = new ConcurrentHashMap<>();
//        testResults.forEach((threadName, data) -> mergedData.merge(threadName, data, (oldValue, newValue) -> oldValue + ", " + newValue));
//
//        // 打印合并的统计数据
//        mergedData.forEach((threadName, data) -> System.out.println("Thread " + threadName + " - Merged Data: " + data));
    }
}
