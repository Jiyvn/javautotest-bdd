package auto;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Client {

    public WebDriver driver;
    public DesiredCapabilities desiredCaps = new DesiredCapabilities();

    public Client() {
    }

    public Client(DesiredCapabilities desCaps) {
        this.desiredCaps = desCaps;
    }

    public WebElement getElement(By LocatedBy, long timeout) {
        return new WebDriverWait(this.driver, timeout, 500).until(
                ExpectedConditions.presenceOfElementLocated(LocatedBy));
    }

    public Boolean findElement(By LocatedBy, long timeout) {
        boolean found = false;
        try {
            new WebDriverWait(this.driver, timeout, 500).until(
                    ExpectedConditions.presenceOfElementLocated(LocatedBy));
            found = true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return found;
    }

    public List<WebElement> getElements(By LocatedBy, long timeout) {
        return new WebDriverWait(this.driver, timeout, 500).until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(LocatedBy));
    }

    public Boolean findElements(By LocatedBy, long timeout) {
        boolean found = false;
        try {
            new WebDriverWait(this.driver, timeout, 500).until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(LocatedBy));
            found = true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return found;
    }

    public boolean alert() {
        try {
            new WebDriverWait(this.driver, 3).until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public WebElement get(By locator, String ec, long timeout, long frequency) //throws
//            NoSuchMethodException, InvocationTargetException,
//            IllegalAccessException, InstantiationException {
    {
        Class<?> cls = ExpectedConditions.class;
//        Class<?> cls = Class.forName("org.openqa.selenium.support.ui.ExpectedConditions");
//        Method func = cls.getMethod(ec, By.class);
        Method func = null;
        try {
            func = cls.getDeclaredMethod(ec, By.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
//        func.getGenericReturnType();
//        Method[] methods = cls.getMethods();
//        Method[] methods = cls.getDeclaredFields();
        WebElement element = null;

        try {
            element = (WebElement) new WebDriverWait(
                    this.driver, timeout, frequency
            ).until((ExpectedCondition<Object>) func.invoke(null, locator));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return element;
    }
    public WebElement get(By locator,long timeout){ //throws
//            NoSuchMethodException, InvocationTargetException,
//            IllegalAccessException, InstantiationException {
        return this.get(locator, "visibilityOfElementLocated",  timeout, 500);
    }

//    public WebElement get(By locator) throws
//            NoSuchMethodException, InvocationTargetException,
//            IllegalAccessException, InstantiationException {
    public WebElement get(By locator){
        return this.get(locator, 3);
    }

    public boolean find(By locator, String ec, long timeout, long frequency){ //throws
//            NoSuchMethodException, InvocationTargetException,
//            IllegalAccessException {
        boolean found = false;
        try {
            Class<?> cls = ExpectedConditions.class;
            Method func = cls.getMethod(ec, By.class);
            new WebDriverWait(
                    this.driver, timeout, frequency
            ).until((ExpectedCondition<Object>) func.invoke(null, locator));
            found = true;
        }catch (TimeoutException e){
            found = false;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return found;
    }
}