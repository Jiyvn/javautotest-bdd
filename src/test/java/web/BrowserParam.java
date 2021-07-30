package web;

import common.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserParam{
//    public static InheritableThreadLocal<WebDriver> driver = new InheritableThreadLocal<WebDriver>(){
//        protected synchronized WebDriver initialValue(){
//            return null;
//        }
//    };
    public static WebDriver driver;
    public static Browser Br;
    public static DesiredCapabilities caps;
    public static String defaultBrowser;

//    public static void remove(){
//        driver.remove();
//    }

}
