package auto.report;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestStatus {
    AtomicInteger passCases = new AtomicInteger(0);
    AtomicInteger failedCases = new AtomicInteger(0);
    AtomicInteger passFeatures = new AtomicInteger(0);
    AtomicInteger failedFeatures = new AtomicInteger(0);

    AtomicInteger totalPass = new AtomicInteger(0);
    AtomicInteger totalFailed = new AtomicInteger(0);

    List<String> defects = new ArrayList<>();

}
