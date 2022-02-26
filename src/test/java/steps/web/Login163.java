package steps.web;

import auto.client.Browser;
import helper.uiAutoHelper;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.ui;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.Login163Page;

import java.util.concurrent.TimeUnit;


public class Login163 extends ui {

    private String mail;
    private String password;

    Login163Page page;

    @Before("@163")
    public void beforeScenario(Scenario scenario) throws Exception {
        String home = System.getenv("HOME") == null ? System.getenv("HOMEPATH") : System.getenv("HOME");
        System.setProperty("webdriver.chrome.driver", home + "/webdrivers/chromedriver");
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox Developer Edition.app/Contents/MacOS/firefox");
        System.setProperty("webdriver.gecko.driver", home + "/webdrivers/geckodriver");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
        defaultBrowser = System.getProperty("browser", "firefox");
        caps = new DesiredCapabilities();
        Browser browser = new Browser(caps);
        browser.setCaps(caps);
        browser.setBrowser(defaultBrowser).setOptions();
        driver = browser.Remote();
        page = new Login163Page(driver);

        this.scenario = scenario;

        uiAutoHelper.setBrowser(defaultBrowser);
        uiAutoHelper.setDesiredCaps(caps);
        uiAutoHelper.setDriver(driver);

    }

    @Given("用户：{word}，密码：{word}")
    public void setLoginInfo(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    @And("打开:{word}")
    public void NavigateTo163(String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//        WebDriverWait wait = new WebDriverWait(driver, 10L, 500L);
//        wait.until(ExpectedConditions.titleIs("163网易免费邮--中文邮箱第一品牌"));
    }

    @When("输入用户名和密码")
    public void InputLoginInfo() {
//        ((JavascriptExecutor) driver).executeScript("");
//        driver.switchTo().frame(1);
//        new WebDriverWait(driver, 20).until(
//                ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("(//iframe)[1]")));
////        driver.findElement(By.className("j-inputtext dlemail j-nameforslide")).click(); // className无效
//        driver.findElement(By.xpath("//input[@class=\"j-inputtext dlemail j-nameforslide\"]")).click();
//        driver.findElement(By.xpath("//input[@class=\"j-inputtext dlemail j-nameforslide\"]")
//        ).sendKeys(this.mail.substring(0, this.mail.length() - 8));
//        driver.findElement(By.name("password")).click();
//        driver.findElement(By.name("password")).sendKeys(this.password);
        page.switchToLoginFrame();
        page.inputEmail(this.mail.substring(0, this.mail.length() - 8));
        page.inputPassword(this.password);


    }

    @And("点击登陆按钮")
    public void ClickLoginButton() {
//        driver.findElement(By.id("dologin")).click();
        page.clickLoginButton();

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
