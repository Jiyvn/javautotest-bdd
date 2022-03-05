package steps.web;

import auto.client.Browser;
//import io.cucumber.java.*;
import auto.exceptions.JavAutoException;
import helper.cucumberHelper;
import helper.uiAutoHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.qameta.allure.Attachment;
import model.ui;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.concurrent.TimeUnit;



public class Search extends ui {
    static Logger log = LoggerFactory.getLogger(Search.class);

    private String word;

    @Given("搜索词:{word}")
    public void testWord(String searchWord) {
        System.out.println("Given -- 搜索词:" + searchWord);
        word = searchWord;
    }

    // ineffective
    @Attachment(value = "Allure Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    @When("打开百度，输入搜索词搜索")
    public void searchByBaidu() throws Exception {
        System.out.println(cucumberHelper.getScenario().getName() + "  When -- 打开百度，输入搜索词搜索");

        if (driver == null) {
            log.info("driver is null");
            driver = page.setBrowser(defaultBrowser).setOptions().Remote();
            uiAutoHelper.setDriver(driver);
        }
        driver.get("https://www.baidu.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.findElement(By.id("kw")).sendKeys(word);
        driver.findElement(By.id("kw")).sendKeys(word);
        driver.findElement(By.id("su")).click();
//        scenario.attach(
//                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png", "Page screenshot"
//        );
        uiAutoHelper.attachImage("after search:"+word);
    }

    @And("浏览器:{word}")
    public void getBrowser(String browser) throws Exception {
        System.out.println("And -- 浏览器:" + browser);
        if (!browser.toLowerCase().equals(defaultBrowser)) {
            DefaultBrowser = false;
//            switch (browser.toLowerCase()) {
//                case "chrome":
//                    //                System.setProperty("webdriver.chrome.driver", "/Users/zi/webdrivers/chromedriver/chromedriver");
////                    caps = DesiredCapabilities.chrome();
//                    caps = new DesiredCapabilities(new ChromeOptions());
////                    ChromeOptions chromeOptions = new ChromeOptions();
//////                    ImmutableCapabilities capabilities = new ImmutableCapabilities("browserName", "chrome");                    ChromeOptions chromeOptions = new ChromeOptions();
////                    chromeOptions.setCapability("browserVersion", "xx");
////                    chromeOptions.setCapability("platformName", "MacOS");
////                    WebDriver driver = new RemoteWebDriver(new URL("http://www.example.com--command-executor"), chromeOptions);
//                    break;
//                case "firefox":
//                    //                System.setProperty("webdriver.gecko.driver", "/Users/zi/webdrivers/geckodriver/geckodriver");
////                    caps = DesiredCapabilities.firefox();
//                    FirefoxOptions options = new FirefoxOptions();
//                    options.setCapability("acceptInsecureCerts", true);
//                    caps = new DesiredCapabilities(options);
//
//                    break;
//                case "safari":
//                    //                System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver/sss");
////                    caps = DesiredCapabilities.safari();
//                    caps = new DesiredCapabilities(new SafariOptions());
//                    break;
//                default:
//                    caps = new DesiredCapabilities();
//            }
            caps = new DesiredCapabilities();
            page = new Browser(caps);
            driver = page.setBrowser(browser.toLowerCase()).setOptions().Remote();
            uiAutoHelper.setDriver(driver);
        }
    }

    @And("打开www")
    public void openwww() {
//        assert 1 == 0;
        saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        throw new JavAutoException();
    }

    @And("点击xxx")
    public void clickxxx() {
        //
    }

}
