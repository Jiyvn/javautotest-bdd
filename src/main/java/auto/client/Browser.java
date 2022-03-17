package auto.client;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class Browser extends RemoteDevice {
//    public static Logger log = LoggerFactory.getLogger(Browser.class);

    public String serverUrl = null;
    private Object options;
    private Class<?> optionCls;
    private Class<?> driverCls;
    protected String browser;

    public Browser(){
        super();
    }

    public Browser(WebDriver driver){
        this.driver = driver;
    }

    public Browser(DesiredCapabilities desiredCap){
        super(desiredCap);
    }

    public Browser(WebDriver driver, DesiredCapabilities desiredCaps){
        this(driver);
        this.desiredCaps = desiredCaps;
        this.browser = (String) this.desiredCaps.getCapability("browser");

    }

    public Browser setCaps(DesiredCapabilities desiredCaps){
        this.desiredCaps = desiredCaps;
        this.browser = (String) this.desiredCaps.getCapability("browser");
        return this;
    }

    public Browser setServerAddress(String serverAddress){
        this.serverUrl = serverAddress;
        return this;
    }

    public Browser setBrowser(String browser){
        this.browser = browser;
        return this;
    }

    public Browser headless() throws ReflectiveOperationException {
        Method func = this.optionCls.getDeclaredMethod("setHeadless", boolean.class);
//        Method func = this.options.getClass().getDeclaredMethod("setHeadless", boolean.class);
        func.setAccessible(true);
        func.invoke(this.options, true);
        return this;
    }


    public Browser setOptions() throws ReflectiveOperationException {
        String obj = String.format("org.openqa.selenium.%s.%sOptions",
                browser.toLowerCase(),
                browser.equals("ie")
                        ? "InternetExplorer"
                        : browser.substring(0, 1).toUpperCase() +
                        browser.substring(1).toLowerCase()
        );
        log.debug("setOptions: "+obj);
        this.optionCls = Class.forName(obj);
        this.options = this.optionCls.getDeclaredConstructor().newInstance();
        try{
//            Method func = this.optionCls.getDeclaredMethod("setLogLevel", ChromeDriverLogLevel.class);
//            func.invoke(this.options, ChromeDriverLogLevel.OFF);
//            Method[] funcs = this.optionCls.getDeclaredMethods();
//            log.info("DeclaredMethods: "+Arrays.toString(funcs));
            //Variadic function, getMethod allow getting method from parent class
            Method func = this.optionCls.getMethod("addArguments", String[].class);
            func.setAccessible(true);
            func.invoke(this.options, new Object[]{new String[]{"--log-level=3"}});
//            func.invoke(this.options, new Object[]{new String[]{"--log-level=3", "--silent"}});
//            new org.openqa.selenium.chrome.ChromeOptions().addArguments();
//            new org.openqa.selenium.firefox.FirefoxOptions().addArguments();
//            new org.openqa.selenium.edge.EdgeOptions().addArguments();
//            // no such method
//            new org.openqa.selenium.safari.SafariOptions().addArguments();
        }catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return this;
    }

    public WebDriver Remote() throws ReflectiveOperationException, MalformedURLException {
        String obj = String.format("org.openqa.selenium.%s.%sDriver",
                browser.toLowerCase(),
                browser.equals("ie")
                        ? "InternetExplorer"
                        : browser.substring(0, 1).toUpperCase() +
                        browser.substring(1).toLowerCase()
        );
        log.debug("Remote: "+obj);
        if(serverUrl == null){
            this.driverCls = Class.forName(obj);

//            Annotation[] func = this.optionCls.getDeclaredConstructor().getDeclaredAnnotations();
//            Field func = this.optionCls.getDeclaredField("merge");
//            func.setAccessible(true);
//            func.set(this.options, this.desiredCaps);
//            System.out.println(Arrays.toString(this.options.getClass().getDeclaredClasses()));
//            System.out.println(Arrays.toString(this.options.getClass().getMethods()));
//            System.out.println(Arrays.toString(this.options.getClass().getConstructors()));
//            Method func = this.options.getClass().getDeclaredMethod("merge", DesiredCapabilities.class);
            Method func = Capabilities.class.getDeclaredMethod("merge", Capabilities.class);
            func.setAccessible(true);
            func.invoke(this.options, this.desiredCaps);
            this.driver = (WebDriver) driverCls.getDeclaredConstructor(new Class[]{this.optionCls})
//                    .newInstance(this.options);
                    .newInstance(new Object[]{this.options});

        }else {
            this.driver = new RemoteWebDriver(new URL(this.serverUrl), this.desiredCaps);
        }
        return this.driver;
    }

    public Browser Open(String url) {
        this.driver.get(url);
//        Method func = this.driverCls.getDeclaredMethod("get", String.class);
//        func.invoke(this.options, url);
        return this;

    }

    public Browser dragInViewPort(WebElement element){
        ((Locatable) element).getCoordinates().inViewPort();
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].scrollIntoView();", element);
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].scrollIntoView(true);", element);
//        String javaScript = "var evObj = document.createEvent('MouseEvents');" +
//                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
//                "arguments[0].dispatchEvent(evObj);";
//        ((JavascriptExecutor)driver).executeScript(javaScript, element);
        return this;
    }

    public static boolean isVisibleInViewport(WebElement element) {
        WebDriver driver = ((RemoteWebElement)element).getWrappedDriver();
        String js = "var elem = arguments[0],                 " +
                "  box = elem.getBoundingClientRect(),    " +
                "  cx = box.left + box.width / 2,         " +
                "  cy = box.top + box.height / 2,         " +
                "  e = document.elementFromPoint(cx, cy); " +
                "for (; e; e = e.parentElement) {         " +
                "  if (e === elem)                        " +
                "    return true;                         " +
                "}                                        " +
                "return false;                            ";
        return (boolean) ((JavascriptExecutor)driver).executeScript(js, element);
    }

}
