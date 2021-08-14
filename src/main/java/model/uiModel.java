package model;

import auto.Browser;
import helper.cucumberHelper;
import helper.uiAutoHelper;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class uiModel {
    protected WebDriver driver = uiAutoHelper.getDriver();
    protected String defaultBrowser = uiAutoHelper.getBrowser();
    protected Boolean DefaultBrowser = true;
    protected DesiredCapabilities caps = uiAutoHelper.getDesiredCaps();
    protected Browser page = new Browser(caps);
    protected Scenario scenario = cucumberHelper.getScenario();
}
