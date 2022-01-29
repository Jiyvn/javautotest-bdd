package helper;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class uiAutoHelper {

    /*
     * driver
     */
    public static InheritableThreadLocal<WebDriver> driver = new InheritableThreadLocal<WebDriver>() {
        protected synchronized WebDriver initialValue() {
            return null;
        }
    };

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static InheritableThreadLocal<String> browser = new InheritableThreadLocal<String>() {
        protected synchronized String initialValue() {
            return null;
        }
    };

    public static String getBrowser() {
        return browser.get();
    }

    public static void setBrowser(String br) {
        browser.set(br);
    }

    /*
     * desiredCaps
     */
    public static InheritableThreadLocal<DesiredCapabilities> desiredCaps = new InheritableThreadLocal<DesiredCapabilities>() {
        protected synchronized DesiredCapabilities initialValue() {
            return null;
        }
    };

    public static DesiredCapabilities getDesiredCaps() {
        return desiredCaps.get();
    }

    public static void setDesiredCaps(DesiredCapabilities caps) {
        desiredCaps.set(caps);
    }


    public static void reset(){
        browser.remove();
        desiredCaps.remove();
        driver.remove();
    }

    public static void quit(){
        getDriver().quit();
    }
}
