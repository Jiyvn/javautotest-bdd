package steps;

import common.Browser;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import web.BrowserParam;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CucumberHooks extends BrowserParam {
//    public static WebDriver driver;
    public Scenario scenario;
//    public static Browser Br;
//    public static DesiredCapabilities caps;
//    public static String defaultBrowser = "firefox";

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {
        String home = System.getenv("HOME") == null ? System.getenv("HOMEPATH") : System.getenv("HOME");
        System.setProperty("webdriver.chrome.driver", home + "/webdrivers/chromedriver");
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox Developer Edition.app/Contents/MacOS/firefox");
        System.setProperty("webdriver.gecko.driver", home + "/webdrivers/geckodriver");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
//        web.BrowserParam.caps = DesiredCapabilities.firefox();
//        web.BrowserParam.Br = new Browser(web.BrowserParam.caps);
//        web.BrowserParam.Br.setBrowser(web.BrowserParam.defaultBrowser).setOptions();
        defaultBrowser = System.getProperty("browser", "firefox");
        caps = new DesiredCapabilities();
        Br = new Browser(caps);
        Br.setBrowser(defaultBrowser).setOptions();
        this.scenario = scenario;
//        page.Start();

    }

    @After
    public void afterScenario(Scenario scenario) {
        Status state = this.scenario.getStatus();
        if (driver!=null) {
            try {
//            if (state.equals(Status.FAILED) || state.equals(Status.UNDEFINED)) {
                if (state.equals(Status.FAILED)) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    this.scenario.attach(screenshot, "image/png",
                            this.scenario.getName() + "_fail_" + new SimpleDateFormat("yyyyMMdd-HHmm-ss.SSS").format(new Date()));
                }
            } catch (WebDriverException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}


