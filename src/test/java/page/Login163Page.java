package page;

import auto.client.Browser;
import auto.actions.ElementAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login163Page extends Browser{

//    WebDriver driver;

    @FindBy(xpath = "//input[@class=\"j-inputtext dlemail j-nameforslide\"]")
    protected WebElement emailInputBox;

    @FindBy(name = "password")
    protected WebElement pwdInputBox;

    @FindBy(id = "dologin")
    protected WebElement loginButton;

    protected By loginFrame = By.xpath("(//iframe)[1]");

    public Login163Page(){
        PageFactory.initElements(new DefaultElementLocatorFactory(this.driver), this);
//        PageFactory.initElements(new AjaxElementLocatorFactory(this.driver, 20), this);
    }

    public Login163Page(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new DefaultElementLocatorFactory(this.driver), this);
//        PageFactory.initElements(new AjaxElementLocatorFactory(this.driver, 20), this);
    }

    public void switchToLoginFrame(){
        ElementAction.switchToFrame(driver, loginFrame);
//        new WebDriverWait(driver, Duration.ofSeconds(20)).until(
//                ExpectedConditions.frameToBeAvailableAndSwitchToIt(loginFrame));
    }

    public void inputEmail(String email){
        emailInputBox.click();
        emailInputBox.clear();
        emailInputBox.sendKeys(email);
    }

    public void inputPassword(String password){
        pwdInputBox.click();
        pwdInputBox.clear();
        pwdInputBox.sendKeys(password);
    }

    public void clickLoginButton(){
        loginButton.click();
    }

    public void login(String email, String password){
        inputEmail(email);
        inputPassword(password);
        clickLoginButton();
    }

}
