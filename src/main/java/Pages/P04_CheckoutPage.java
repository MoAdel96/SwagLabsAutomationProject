package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P04_CheckoutPage {
    private final WebDriver driver;
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By zipCode = By.id("postal-code");
    private final By continueButton = By.xpath("//input[@type='submit']");

    public P04_CheckoutPage(WebDriver driver) {
        this.driver = driver;


    }

    public P04_CheckoutPage fillingInformationForm(String fName, String lName, String Zip) {
        Utility.sendData(driver, firstName, fName);
        Utility.sendData(driver, lastName, lName);
        Utility.sendData(driver, zipCode, Zip);
        return this;
    }

    public P05_OverviewPage clickOnContinueButton() {
        Utility.clickingOnElement(driver, continueButton);
        return new P05_OverviewPage(driver);
    }

    public Boolean verifyURl(String expectedURl) {
        try {
            Utility.generalWait(driver).until(ExpectedConditions.urlToBe(expectedURl));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
