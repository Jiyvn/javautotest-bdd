package auto.actions;

import io.appium.java_client.AppiumBy;
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static io.appium.java_client.touch.offset.PointOption.point;


//https://www.selenium.dev/selenium/docs/api/java/deprecated-list.html
// https://www.selenium.dev/documentation/webdriver/actions_api/mouse/
// selenium:
//      - import org.openqa.selenium.interactions.touch.TouchActions;
//      - import org.openqa.selenium.interactions.Actions;
// appium:
// http://appium.io/docs/en/commands/interactions/touch/touch-perform/
// http://appium.io/docs/en/commands/interactions/actions/index.html
// https://github.com/appium/appium/tree/master/docs/en/commands/interactions/touch
public class ElementAction{

    public static void switchToFrame(WebDriver driver, By frame){
//        driver.switchTo().frame((WebElement) driver.findElement(frame));
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public static void switchToFrame(WebDriver driver, WebElement frame){
//        driver.switchTo().frame((WebElement) driver.findElement(frame));
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public static void swipe(WebDriver driver, int startX, int startY, int endX, int endY) throws InterruptedException {
        new TouchAction<>((PerformsTouchActions) driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();
//        new Actions(driver).moveByOffset(startX, startY).clickAndHold().moveByOffset(endX, endY);
    }

    public static void tap(WebDriver driver, int x, int y){
        new TouchAction<>((PerformsTouchActions) driver)
                .tap(point(x, y))
                .perform();
    }

    public static void tap(WebDriver driver, WebElement webElement){
//        new TouchAction<>((PerformsTouchActions) driver)
//                .tap(TapOptions.tapOptions().withElement(ElementOption.element(webElement)))
//                .perform();
//        new TouchActions(driver).singleTap(webElement).perform();
        new Actions(driver).moveToElement(webElement).click();

    }


    public static void press(WebDriver driver, int x, int y){
        new TouchAction<>((PerformsTouchActions) driver)
                .press(PointOption.point(x, y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                .release()
                .perform();
    }

    public static void press(WebDriver driver, WebElement webElement){
        Rectangle rect = webElement.getRect();
        int midX = rect.getX() + rect.getWidth() / 2;
        int midY = rect.getY() + rect.getHeight() / 2;
        press(driver, midX, midY);


    }

}
