package steps;

import common.Browser;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.Assertion;
import utils.TestListener;
import web.BrowserParam;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Login163{
//    private Browser page = new xxxPage(driver);
    private Browser page;
    private String mail;
    private String password;
    private Scenario scenario;
    private String defaultBrowser;
    private WebDriver driver;
    private DesiredCapabilities caps;

    @Before("@163")
    public void beforeScenario(Scenario scenario) throws Exception {
        String home = System.getenv("HOME") == null ? System.getenv("HOMEPATH") : System.getenv("HOME");
        System.setProperty("webdriver.chrome.driver", home + "/webdrivers/chromedriver");
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox Developer Edition.app/Contents/MacOS/firefox");
        System.setProperty("webdriver.gecko.driver", home + "/webdrivers/geckodriver");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
//        web.BrowserParam.caps = DesiredCapabilities.firefox();
//        web.BrowserParam.Br = new Browser(web.BrowserParam.caps);
//        web.BrowserParam.Br.setBrowser(web.BrowserParam.defaultBrowser).setOptions();
        defaultBrowser = System.getProperty("browser", "firefox");
        caps = new DesiredCapabilities();
        page = new Browser(caps);
        page.setBrowser(defaultBrowser).setOptions();
        this.scenario = scenario;
//        page.Start();

    }

    @After("@163")
    public void afterScenario(Scenario scenario) {
        Status state = this.scenario.getStatus();
        try{
//            if (state.equals(Status.FAILED) || state.equals(Status.UNDEFINED)) {
            if (state.equals(Status.FAILED)) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                this.scenario.attach(screenshot, "image/png",
                        this.scenario.getName()+"_fail_" + new SimpleDateFormat("yyyyMMdd-HHmm-ss.SSS").format(new Date()));
            }
        }catch (WebDriverException e){
            e.printStackTrace();
        }
        driver.quit();
    }

    @Given("用户：{word}，密码：{word}")
    public void setLoginInfo(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    @And("打开:{word}")
    public void NavigateTo163(String url) throws
            IllegalAccessException, InvocationTargetException,
            InstantiationException, MalformedURLException, NoSuchFieldException,
            NoSuchMethodException, ClassNotFoundException {
        if (driver==null) {
            driver = page.Start();
        }
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//        WebDriverWait wait = new WebDriverWait(driver, 10L, 500L);
//        wait.until(ExpectedConditions.titleIs("163网易免费邮--中文邮箱第一品牌"));
    }

    @When("输入用户名和密码")
    public void InputLoginInfo() {
//        ((JavascriptExecutor) driver).executeScript("");
//        driver.switchTo().frame(1);
        new WebDriverWait(driver, 20).until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("(//iframe)[1]")));
//        driver.findElement(By.className("j-inputtext dlemail j-nameforslide")).click(); // className无效
        driver.findElement(By.xpath("//input[@class=\"j-inputtext dlemail j-nameforslide\"]")).click();
        driver.findElement(By.xpath("//input[@class=\"j-inputtext dlemail j-nameforslide\"]")
        ).sendKeys(this.mail.substring(0, this.mail.length() - 8));
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).sendKeys(this.password);

    }

    @And("点击登陆按钮")
    public void ClickLoginButton() {
        driver.findElement(By.id("dologin")).click();

    }

    @Then("登陆失败，出现提示{string}")  // the quotes in {string} will be ignored
    public void FailureTextVerification(String errorMessage) {
        assert 1==0;
        WebElement error;
        try {
            error = new WebDriverWait(driver, 5L, 500L).until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("ferrorhead")));
        } catch (NoSuchElementException | TimeoutException e) {
            throw new AssertionError("error message not found");
        }
        assert error.getText().equals(errorMessage);

    }
}
