package auto.client;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
}
