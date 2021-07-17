package steps;

import common.Browser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class Search {
    private WebDriver driver;
    private String word;


    @BeforeClass()
    public void Webdriver() throws Exception{
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        Browser page = new Browser(caps);
        driver = page.setBrowser("firefox").setOptions().Start();

    }
    @AfterClass()
    public void Close(){
        driver.quit();

    }

    @Given("搜索词:{word}")
    public void testWord(String searchWord){
        System.out.println("Given -- 搜索词:"+searchWord);
        word = searchWord;


    }
    @When("打开百度，输入搜索词搜索")
    public void searchByBaidu(){
        System.out.println("When -- 打开百度，输入搜索词搜索");
        driver.get("http://www.baidu.com");
        driver.findElement(new By.ById("kw")).sendKeys(word);
        driver.findElement(new By.ById("su")).click();
    }
    @And("浏览器:{word}")
    public void setBrowser(String browser) throws Exception {
        System.out.println("And -- 浏览器:"+browser);
        DesiredCapabilities caps;
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
        Browser page = new Browser(caps);
        driver = page.setBrowser(browser.toLowerCase()).setOptions().Start();
    }
}
