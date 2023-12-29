package listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AlterListener implements IAlterSuiteListener {
    private static final Logger log = LoggerFactory.getLogger(AlterListener.class);

    @Override
    public void alter(List<XmlSuite> suites) {
        for (XmlSuite suite : suites) {
            // 获取所有的监听器
            List<String> listeners = suite.getListeners();
            log.warn("listeners: "+listeners);
            log.warn("disable allure: "+System.getProperty("allure.disable", "false"));
            if (!Boolean.parseBoolean(System.getProperty("allure.disable", "false"))) {
                log.warn("Going to disable allure report");
                Set<String> RPListeners = listeners.stream().filter(l -> l.contains("io.qameta.allure.cucumber")).collect(Collectors.toSet());
                log.warn("allure listeners: "+RPListeners);
                RPListeners.forEach(listeners::remove);
                log.warn("listeners after disabled allure report: "+listeners);
                suite.setListeners(listeners);

            }
        }
    }
}