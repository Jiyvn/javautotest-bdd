package auto.element;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CuzExpectedConditions {

    public static ExpectedCondition<Boolean> allDisplayed(final List<WebElement> elements) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return elements.stream().allMatch(WebElement::isDisplayed);
            }
        };
    }

}
