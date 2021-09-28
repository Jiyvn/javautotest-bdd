package utils;

import org.openqa.selenium.*;


public class capturer {

    protected WebDriver driver;

    public capturer setDriver(WebDriver driver){
        this.driver = driver;
        return this;
    }

    public byte[] captureFullScreen(){
        return ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);
    }

    public byte[] captureFullScreen(WebDriver driver){
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public byte[] takeScreenShot(WebElement element){
        return element.getScreenshotAs(OutputType.BYTES);
    }

}
