package steps;

import common.Browser;
import common.Directory;
import common.TestListener;
import io.cucumber.java.*;
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
import org.openqa.selenium.remote.DesiredCapabilities;

//import io.cucumber.java.Scenario;
import org.testng.annotations.Listeners;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

//@Listeners(TestListener.class)
//@Feature("search by baidu")
public class Search {
    public static WebDriver driver;
    private String word;
    private Boolean DefaultBrowser = true;
    private Browser page;
    private DesiredCapabilities caps;
    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) throws Exception{
        caps = DesiredCapabilities.firefox();
        page = new Browser(caps);
        page.setBrowser("firefox").setOptions();
        this.scenario = scenario;

    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] Attach(byte[] screenShot){
        return screenShot;
    }

    @After
    public void tearDown(){
        Status state = this.scenario.getStatus();
        if (state.equals(Status.FAILED)||state.equals(Status.UNDEFINED)){
            if (state.equals(Status.UNDEFINED)){
            }
            System.out.println("scenario failed");
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            this.scenario.attach(screenshot, "image/png", "fail_screenshot");
//            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            try{
//                FileUtils.copyFile(screenshot, new File(Directory.Path("capture/screenshot.png")));
//            }catch (IOException e) {
//                e.printStackTrace();
//            }
//            Path content = Paths.get(Directory.Path("capture/screenshot.png"));
//            try (InputStream is = Files.newInputStream(content)) {
//                Allure.addAttachment("My attachment", is);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            Attach(screenshot);
        }
        driver.quit();

    }

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
        driver.findElement(new By.ById("kw")).sendKeys(word);
        driver.findElement(new By.ById("su")).click();
    }
    @And("浏览器:{word}")
    public void setBrowser(String browser) throws Exception {
        System.out.println("And -- 浏览器:"+browser);
        DefaultBrowser = false;
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
