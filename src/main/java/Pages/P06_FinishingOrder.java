package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.findWebElement;

public class P06_FinishingOrder {
    private final WebDriver driver;
    private final By thanksMessage = By.className("complete-header");


    public P06_FinishingOrder(WebDriver driver) {
        this.driver = driver;
    }

    public boolean checkVisibilityOfThanksMessage() {
        return findWebElement(driver, thanksMessage).isDisplayed();
    }
}
