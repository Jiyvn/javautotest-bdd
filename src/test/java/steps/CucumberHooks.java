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

import java.text.SimpleDateFormat;
import java.util.Date;

public class CucumberHooks {

    public Scenario scenario;
    public Browser page;
    public DesiredCapabilities caps;

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {
        String home = System.getenv("HOME") == null ? System.getenv("HOMEPATH") : System.getenv("HOME");
        System.setProperty("webdriver.chrome.driver", home + "/webdrivers/chromedriver");
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox Developer Edition.app/Contents/MacOS/firefox");
        System.setProperty("webdriver.gecko.driver", home + "/webdrivers/geckodriver");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
        this.caps = DesiredCapabilities.firefox();
        this.page = new Browser(caps);
        this.page.setBrowser("firefox").setOptions();
        this.scenario = scenario;
        this.page.Start();


    }

    @After
    public void afterScenario(Scenario scenario) {
        Status state = this.scenario.getStatus();
        try{
//            if (state.equals(Status.FAILED) || state.equals(Status.UNDEFINED)) {
            if (state.equals(Status.FAILED)) {
                byte[] screenshot = ((TakesScreenshot) this.page.driver).getScreenshotAs(OutputType.BYTES);
                this.scenario.attach(screenshot, "image/png",
                        this.scenario.getName()+"_fail_" + new SimpleDateFormat("yyyyMMdd-HHmm-ss.SSS").format(new Date()));
            }
        }catch (WebDriverException e){
            e.printStackTrace();
        }
        this.page.driver.quit();
    }
}
