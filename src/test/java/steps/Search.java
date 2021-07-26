package steps;

import common.Browser;
//import io.cucumber.java.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.testng.annotations.*;
import web.BrowserParam;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Search extends BrowserParam {
//    public WebDriver driver;
    private String word;
    private Boolean DefaultBrowser = true;
//    private Browser page = new xxxPage(driver);
    private Browser page = Br;


    @Given("搜索词:{word}")
    public void testWord(String searchWord){
        System.out.println("Given -- 搜索词:"+searchWord);
        word = searchWord;


    }
    @When("打开百度，输入搜索词搜索")
    public void searchByBaidu() throws Exception{
        System.out.println("When -- 打开百度，输入搜索词搜索");
        if (DefaultBrowser.equals(true)){
            driver = page.Start();
        }
        driver.get("https://www.baidu.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.findElement(By.id("kw")).sendKeys(word);
        driver.findElement(By.id("kw")).sendKeys(word);
        driver.findElement(By.id("su")).click();
    }

    @And("浏览器:{word}")
    public void getBrowser(String browser) throws Exception {
        System.out.println("And -- 浏览器:"+browser);
        if (!browser.toLowerCase().equals(CucumberHooks.defaultBrowser)) {
            DefaultBrowser = false;
        }
        switch (browser.toLowerCase()){
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

    }

    @And("打开")
    public void open(){
        assert 1==0;
    }

}
