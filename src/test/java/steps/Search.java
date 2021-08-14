package steps;

import auto.Browser;
//import io.cucumber.java.*;
import helper.cucumberHelper;
import helper.uiAutoHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import model.uiModel;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;



public class Search extends uiModel {
    static Logger log = LoggerFactory.getLogger(Search.class);

    private String word;

    @Given("搜索词:{word}")
    public void testWord(String searchWord) {
        System.out.println("Given -- 搜索词:" + searchWord);
        word = searchWord;
    }

//    @Attachment(value = "Page screenshot", type = "image/png")
//    public byte[] Attach(byte[] screenShot){
//        return screenShot;
//    }

    @When("打开百度，输入搜索词搜索")
    public void searchByBaidu() throws Exception {
        System.out.println(cucumberHelper.getScenario().getName() + "  When -- 打开百度，输入搜索词搜索");

        if (driver == null) {
            log.info("driver is null");
            driver = page.setBrowser(defaultBrowser).setOptions().Start();
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
        cucumberHelper.attach(driver);
    }

    @And("浏览器:{word}")
    public void getBrowser(String browser) throws Exception {
        System.out.println("And -- 浏览器:" + browser);
        if (!browser.toLowerCase().equals(defaultBrowser)) {
            DefaultBrowser = false;
            switch (browser.toLowerCase()) {
                case "chrome":
                    //                System.setProperty("webdriver.chrome.driver", "/Users/zi/webdrivers/chromedriver/chromedriver");
                    caps = DesiredCapabilities.chrome();
                    break;
                case "firefox":
                    //                System.setProperty("webdriver.gecko.driver", "/Users/zi/webdrivers/geckodriver/geckodriver");
                    caps = DesiredCapabilities.firefox();
                    break;
                case "safari":
                    //                System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver/sss");
                    caps = DesiredCapabilities.safari();
                    break;
                default:
                    caps = new DesiredCapabilities();
            }
            page = new Browser(caps);
            driver = page.setBrowser(browser.toLowerCase()).setOptions().Start();
            uiAutoHelper.setDriver(driver);
        }
    }

    @And("打开")
    public void open() {
        assert 1 == 0;
    }

}
