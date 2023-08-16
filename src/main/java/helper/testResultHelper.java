package helper;

import auto.report.TestStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class testResultHelper {
    public static final ConcurrentHashMap<String, Map<String, AtomicInteger>> threadingResults = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, AtomicInteger> overallResult = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<String, TestStatus> testResult = new ConcurrentHashMap<>();

    public static void tPut(String outerKey, String innerKey, AtomicInteger innerValue){
//        threadingResults.computeIfAbsent(outerKey, key -> new ConcurrentHashMap<>());
        threadingResults.computeIfPresent(outerKey, (key, innerMap)->{
//            if (innerMap == null){
//                innerMap = new ConcurrentHashMap<>();
//            }
            innerMap.put(innerKey, innerValue);
            return innerMap;
        });

    }

    public static void tIncrease(String outerKey, String innerKey){
        threadingResults.computeIfAbsent(outerKey, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(innerKey, k -> new AtomicInteger(0));
        threadingResults.compute(outerKey, (key, innerMap)->{
            AtomicInteger value = innerMap.get(innerKey);
            value.incrementAndGet();
            return innerMap;
        });
        overallResult.computeIfAbsent(innerKey, k -> new AtomicInteger(0));
        overallResult.compute(innerKey, (k, value)->{
            value.incrementAndGet();
            return value;
        });
    }
}
