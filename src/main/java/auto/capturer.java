package auto;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;

import java.time.Duration;


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

    public void startRecording(){
        if (driver instanceof IOSDriver) {
            ((CanRecordScreen) this.driver).startRecordingScreen(
                    new IOSStartScreenRecordingOptions()
                            .withTimeLimit(Duration.ofHours(1))
                            .withVideoQuality(IOSStartScreenRecordingOptions.VideoQuality.LOW)
                            .withFps(5)
                            .withVideoType("h264")
                            .withVideoScale("trunc(iw/2)*2:trunc(ih/2)*2")
                            .enableForcedRestart()
            );
        }else if(driver instanceof AndroidDriver){
            ((CanRecordScreen) this.driver).startRecordingScreen(
                    new AndroidStartScreenRecordingOptions()
                            .withTimeLimit(Duration.ofHours(1))
                            .withBitRate(500000) // 500k/s
                            .withVideoSize("720x1280")
//                            .withVideoSize("360x640")
//                            .enableBugReport() // since Android P
                            .enableForcedRestart()
            );
        }


    }

    public String stopRecording(){
        return ((CanRecordScreen) this.driver).stopRecordingScreen();
    }

}
