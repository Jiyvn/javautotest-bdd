package auto.client;

import com.google.common.collect.Lists;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
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
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void waitUntilElementPresent(WebElement element, int timeout){
        // format: this.foundBy = String.format("[%s] -> %s: %s", foundFrom, locator, term);
        // see RemoteWebElement setFoundBy()
        String[] data =element.toString().split(" -> ")[1].replace("]", "").split(": ");
        String locatorType = data[0];
        String identity = data[1];
        By locator = getLocator(locatorType, identity);
        new WebDriverWait(this.driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /*
    * web, need improvement for mobile
    * */
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
//            Class<?> cls = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            List<Field> fields = Arrays.stream(Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getDeclaredFields())
                    .filter(f -> elemName.equals(f.getName())).collect(Collectors.toList());
            Annotation[] annotations = fields.get(0).getAnnotations();
            for(Annotation ann: annotations){
                if (driver instanceof AppiumDriver) {
                    if (ann instanceof AndroidFindBy
                            && driver instanceof AndroidDriver) {
                        byList.add(getFindBy((AndroidFindBy) ann));
                    } else if (ann instanceof iOSXCUITFindBy && driver instanceof IOSDriver) {
                        byList.add(getFindBy((iOSXCUITFindBy) ann));
                    }
                }
                // web or webview
                if(ann instanceof FindBy){
                    byList.add(getFindBy((FindBy) ann));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byList;
    }

    public By getFindBy(final FindBy findBy){
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

    public By getFindBy(final AndroidFindBy findBy){
        if (findBy.id()!=null && !findBy.id().isEmpty()) {
            return new By.ById(findBy.id());
        } else if (findBy.uiAutomator()!=null && !findBy.uiAutomator().isEmpty()) {
            return AppiumBy.androidUIAutomator(findBy.uiAutomator());
        } else if (findBy.accessibility()!=null && !findBy.accessibility().isEmpty()) {
            return AppiumBy.accessibilityId(findBy.accessibility());
        } else if (findBy.xpath()!=null && !findBy.xpath().isEmpty()) {
            return AppiumBy.xpath(findBy.xpath());
        } else if (findBy.tagName()!=null && !findBy.tagName().isEmpty()) {
            return AppiumBy.tagName(findBy.tagName());
        } else if (findBy.className()!=null && !findBy.className().isEmpty()) {
            return AppiumBy.className(findBy.className());
        } else {
            return null;
        }
    }

    public By getFindBy(final iOSXCUITFindBy findBy){
        if (findBy.id()!=null && !findBy.id().isEmpty()) {
            return new By.ById(findBy.id());
        } else if (findBy.xpath()!=null && !findBy.xpath().isEmpty()) {
            return AppiumBy.xpath(findBy.xpath());
        } else if (findBy.iOSNsPredicate()!=null && !findBy.iOSNsPredicate().isEmpty()) {
            return AppiumBy.iOSNsPredicateString(findBy.iOSNsPredicate());
        } else if (findBy.iOSClassChain()!=null && !findBy.iOSClassChain().isEmpty()) {
            return AppiumBy.iOSClassChain(findBy.iOSClassChain());
        } else if (findBy.accessibility()!=null && !findBy.accessibility().isEmpty()) {
            return AppiumBy.accessibilityId(findBy.accessibility());
        } else if (findBy.tagName()!=null && !findBy.tagName().isEmpty()) {
            return AppiumBy.tagName(findBy.tagName());
        } else if(findBy.className()!=null && !findBy.className().isEmpty()){
            return AppiumBy.className(findBy.className());
        }else{
            return null;
        }
    }

}
