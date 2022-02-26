package auto.actions;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
        new TouchAction<>((PerformsTouchActions) driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();
    }

}
