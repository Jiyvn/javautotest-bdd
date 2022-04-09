package helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import auto.actions.Capturer;

import java.util.Base64;

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

    // full screen capture
    public static void attachImage(String Name){
        cucumberHelper.attach(new Capturer().captureFullScreen(getDriver()), "image/png", Name);
    }

    // WebElement capture
    public static void attachImage(WebElement element, String Name){
        cucumberHelper.attach(new Capturer().takeScreenShot(element), "image/png", Name);
    }

    public static void attachVideo(byte[] data, String Name){
        cucumberHelper.attach(Base64.getMimeDecoder().decode(data), "video/mp4", Name);
    }
}
