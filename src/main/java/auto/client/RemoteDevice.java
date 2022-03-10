package auto.client;

import com.google.common.collect.Lists;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;

public class RemoteDevice {

    public WebDriver driver;
    public DesiredCapabilities desiredCaps = new DesiredCapabilities();

    public RemoteDevice() {

    }

    public RemoteDevice(DesiredCapabilities desCaps) {
        this.desiredCaps = desCaps;
    }

    public WebElement getElement(By LocatedBy, long timeout) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(timeout), Duration.ofMillis(500)).until(
                ExpectedConditions.presenceOfElementLocated(LocatedBy));
    }

    public Boolean findElement(By LocatedBy, long timeout) {
        try {
            new WebDriverWait(this.driver, Duration.ofSeconds(timeout), Duration.ofMillis(500)).until(
                    ExpectedConditions.presenceOfElementLocated(LocatedBy));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public List<WebElement> getElements(By LocatedBy, long timeout) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(timeout), Duration.ofMillis(500)).until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(LocatedBy));
    }

    public Boolean findElements(By LocatedBy, long timeout) {
        try {
            new WebDriverWait(this.driver, Duration.ofSeconds(timeout), Duration.ofMillis(500)).until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(LocatedBy));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean alert() {
        try {
            new WebDriverWait(this.driver, Duration.ofSeconds(3)).until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public WebElement get(By locator, String ec, long timeout, long frequency) throws ReflectiveOperationException {
        Class<?> cls = ExpectedConditions.class;
//        Class<?> cls = Class.forName("org.openqa.selenium.support.ui.ExpectedConditions");
//        Method func = cls.getMethod(ec, By.class);
        Method func = cls.getDeclaredMethod(ec, By.class);

//        func.getGenericReturnType();
//        Method[] methods = cls.getMethods();
//        Method[] methods = cls.getDeclaredFields();
        WebElement element = (WebElement) new WebDriverWait(
                this.driver,
                Duration.ofSeconds(timeout),
                Duration.ofMillis(frequency)
        ).until((ExpectedCondition<?>) func.invoke(null, locator));

        return element;
    }

    public WebElement get(By locator,long timeout) throws ReflectiveOperationException {
        return this.get(locator, "visibilityOfElementLocated",  timeout, 500);
    }

    public WebElement get(By locator) throws ReflectiveOperationException {
        return this.get(locator, 3);
    }

    public boolean find(By locator, String ec, long timeout, long frequency) throws ReflectiveOperationException {
        try {
            Class<?> cls = ExpectedConditions.class;
            Method func = cls.getMethod(ec, By.class);
            new WebDriverWait(
                    this.driver, Duration.ofSeconds(timeout),
                    Duration.ofMillis(frequency)
            ).until((ExpectedCondition<?>) func.invoke(null, locator));
        }catch (TimeoutException e){
            return false;
        }
        return true;
    }
    public void waitForElementPresent(WebElement element, int timeout){
        // By format = "[foundFrom] -> locator: term"
        // see RemoteWebElement toString() implementation
        String[] data = element.toString().split(" -> ")[1].replace("]", "").split(": ");
        String locatorType = data[0];
        String identity = data[1];
        By locator = getLocator(locatorType, identity);
        new WebDriverWait(this.driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public By getLocator(String locatorType, String identity){
        switch (locatorType) {
            case "xpath":
                return By.xpath(identity);
            case "css selector":
                return By.cssSelector(identity);
            case "id":
                return By.id(identity);
            case "tag name":
                return By.tagName(identity);
            case "name":
                return By.name(identity);
            case "link text":
                return By.linkText(identity);
            case "class name":
                return By.className(identity);
            default:
                throw new RuntimeException("unrecognized locator: "+locatorType+"="+identity);
        }
    }

    public List<By> getLocators(String elemName) {

        List<By> byList = Lists.newArrayList();
        try {
            Class cls = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            for(Field field : cls.getDeclaredFields()){
                String name = field.getName();
                if(name.equals(elemName)) {
                    if(driver instanceof AndroidDriver) {
                        AndroidFindBy[] androidFindBys = field.getAnnotationsByType(AndroidFindBy.class);
                        for (AndroidFindBy findBy: androidFindBys) {
                            By by = getAndroidFindBy(findBy);
                            if (by != null){
                                byList.add(by);
                            }
                        }
                    }else if(driver instanceof IOSDriver) {
                        iOSXCUITFindBy[] iOSFindBys = field.getAnnotationsByType(iOSXCUITFindBy.class);
                        for (iOSXCUITFindBy findBy: iOSFindBys) {
                            By by = getIOSFindBy(findBy);
                            if (by != null){
                                byList.add(by);
                            }
                        }
                    }
                    // for webview
                    if (byList.size()<=0){
                        FindBy[] findBys = field.getAnnotationsByType(FindBy.class);
                        for (FindBy findBy: findBys) {
                            By by = getFindBy(findBy);
                            if (by != null){
                                byList.add(by);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byList;
    }

    public By getFindBy(final FindBy findBy){
        if (findBy != null) {
            if (findBy.id()!=null && !findBy.id().isEmpty()) {
                return By.id(findBy.id());
            } else if (findBy.name()!=null && !findBy.name().isEmpty()) {
                return By.name(findBy.name());
            } else if (findBy.className()!=null && !findBy.className().isEmpty()) {
                return By.className(findBy.className());
            } else if (findBy.css()!=null && !findBy.css().isEmpty()) {
                return By.cssSelector(findBy.css());
            } else if (findBy.tagName()!=null && !findBy.tagName().isEmpty()) {
                return By.tagName(findBy.tagName());
            } else if (findBy.linkText()!=null && !findBy.linkText().isEmpty()) {
                return By.linkText(findBy.linkText());
            } else if (findBy.partialLinkText()!=null && !findBy.partialLinkText().isEmpty()) {
                return By.partialLinkText(findBy.partialLinkText());
            } else if (findBy.xpath()!=null && !findBy.xpath().isEmpty()) {
                return By.xpath(findBy.xpath());
            } else {
                return null;
            }
        }
        return null;
    }

    public By getAndroidFindBy(final AndroidFindBy findBy){
        if (findBy != null) {
            if (findBy.id()!=null && !findBy.id().isEmpty()) {
                return MobileBy.id(findBy.id());
            } else if (findBy.uiAutomator()!=null && !findBy.uiAutomator().isEmpty()) {
                return MobileBy.AndroidUIAutomator(findBy.uiAutomator());
            } else if (findBy.accessibility()!=null && !findBy.accessibility().isEmpty()) {
                return MobileBy.AccessibilityId(findBy.accessibility());
            } else if (findBy.xpath()!=null && !findBy.xpath().isEmpty()) {
                return MobileBy.xpath(findBy.xpath());
            } else if (findBy.tagName()!=null && !findBy.tagName().isEmpty()) {
                return MobileBy.tagName(findBy.tagName());
            } else if (findBy.className()!=null && !findBy.className().isEmpty()) {
                return MobileBy.className(findBy.className());
            } else {
                return null;
            }
        }
        return null;
    }

    public By getIOSFindBy(final iOSXCUITFindBy findBy){
        if (findBy != null) {
            if (findBy.id()!=null && !findBy.id().isEmpty()) {
                return MobileBy.id(findBy.id());
            } else if (findBy.xpath()!=null && !findBy.xpath().isEmpty()) {
                return MobileBy.xpath(findBy.xpath());
            } else if (findBy.iOSNsPredicate()!=null && !findBy.iOSNsPredicate().isEmpty()) {
                return MobileBy.iOSNsPredicateString(findBy.iOSNsPredicate());
            } else if (findBy.iOSClassChain()!=null && !findBy.iOSClassChain().isEmpty()) {
                return MobileBy.iOSClassChain(findBy.iOSClassChain());
            } else if (findBy.accessibility()!=null && !findBy.accessibility().isEmpty()) {
                return MobileBy.AccessibilityId(findBy.accessibility());
            } else if (findBy.tagName()!=null && !findBy.tagName().isEmpty()) {
                return MobileBy.tagName(findBy.tagName());
            } else if(findBy.className()!=null && !findBy.className().isEmpty()){
                return MobileBy.className(findBy.className());
            }else{
                return null;
            }
        }
        return null;
    }

}
