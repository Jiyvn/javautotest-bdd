package auto.client;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;


public class Browser extends RemoteDevice {
    public static Logger log = LoggerFactory.getLogger(Browser.class);

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
            //Variadic fuction
            Method func = this.optionCls.getDeclaredMethod("addArguments", String[].class);
            func.setAccessible(true);
            func.invoke(this.options, new Object[]{new String[]{"--log-level=3"}});
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

}
