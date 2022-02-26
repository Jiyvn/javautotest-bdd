package auto.actions;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.appium.java_client.touch.offset.PointOption.point;

public class ElementAction{

    public static void switchToFrame(WebDriver driver, By frame){
//        driver.switchTo().frame((WebElement) driver.findElement(frame));
        new WebDriverWait(driver, 20).until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public static void switchToFrame(WebDriver driver, WebElement frame){
//        driver.switchTo().frame((WebElement) driver.findElement(frame));
        new WebDriverWait(driver, 20).until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public static void swipe(WebDriver driver, int startX, int startY, int endX, int endY) {
        new TouchAction<>((PerformsTouchActions) driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();
    }

    public static void tap(WebDriver driver, int x, int y){
        new TouchAction<>((PerformsTouchActions) driver)
                .tap(point(x, y))
                .perform();
    }

    public static void tap(WebDriver driver, WebElement webElement){
        new TouchAction<>((PerformsTouchActions) driver)
                .tap(TapOptions.tapOptions().withElement(ElementOption.element(webElement)))
                .perform();
    }


    public static void press(WebDriver driver, int x, int y){
        new TouchAction<>((PerformsTouchActions) driver)
                .press(PointOption.point(x, y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                .perform();
    }

    public static void press(WebDriver driver, WebElement webElement){
        Rectangle rect = webElement.getRect();
        int midX = rect.getX() + rect.getWidth() / 2;
        int midY = rect.getY() + rect.getHeight() / 2;
        press(driver, midX, midY);
    }

}
