package auto;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;

public class propertyLoader {

    static String home = System.getenv("HOME") == null ? System.getenv("HOMEPATH") : System.getenv("HOME");

    public static void setDriverExecutor(){
        System.setProperty("webdriver.chrome.driver", home + "/webdrivers/chromedriver");
        // effective
//        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
//        // ineffective
//        System.setProperty(ChromeDriverService.CHROME_DRIVER_VERBOSE_LOG_PROPERTY, "false");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_APPEND_LOG_PROPERTY, "false"); // append to the log file or not
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "log/chrome.log");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
        // firefox dev edition application
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox Developer Edition.app/Contents/MacOS/firefox");
        System.setProperty("webdriver.gecko.driver", home + "/webdrivers/geckodriver");
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"log/firefox.log");
//        org.testng.log4testng.Logger
    }
}
