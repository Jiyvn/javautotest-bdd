package listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.*;

public class methodInterceptor implements IMethodInterceptor {
    private static final Logger log = LoggerFactory.getLogger(methodInterceptor.class);

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        List<IMethodInstance> result = new ArrayList<>();
        log.warn("methods: "+methods);
        for (IMethodInstance m : methods) {
            Test test = m.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
            log.error("method: " + m.getMethod().getMethodName());
            Set<String> groups = new HashSet<String>(Arrays.asList(test.groups()));
            log.warn("groups set: " + Arrays.toString(groups.toArray()));
            log.warn("--Dtags: " + System.getProperty("tags"));
            log.warn("-Dgroups: " + System.getProperty("groups"));
            String groupsTags = System.getProperty("tags");
            List<String> gtList = groupsTags == null || groupsTags.isBlank() ? new ArrayList<>() : List.of(groupsTags.split(","));
            for (String gts : gtList) {
                if (gts.contains("&&")) {
                    Set<String> andGroups = new HashSet<>();
                    log.warn("and groups (&&): " + gts);
                    Arrays.stream(gts.split("&&")).forEach(t -> {
                        if (!t.isBlank()) {andGroups.add(t.trim());}
                    });
                    if (groups.containsAll(andGroups)) {
                        log.warn("groups " + groups + " contains expected groups (&&): " + andGroups);
                        result.add(m);
                        break;
                    }
                } else {
                    log.warn("or groups (,): " + gts);
                    if (groups.contains(gts.trim())) {
                        result.add(m);
                        break;
                    }
                }
            }
        }
        log.error("result: " + result);
        return result;
    }

}
