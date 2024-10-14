package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {
    private final WebDriver driver;

    private final By subTotal = By.className("summary_subtotal_label");
    private final By Tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.cssSelector("a[href=\"./checkout-complete.html\"]");

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public Float getSubtotal() {
        return Float.parseFloat(Utility.getText(driver, subTotal).replace("$", ""));
    }

    public Float getTax() {
        return Float.parseFloat(Utility.getText(driver, Tax).replace("$", ""));
    }

    public Float getTotal() {
        return Float.parseFloat(Utility.getText(driver, total).replace("$", ""));
    }

    public String calculateTotalPrice() {
        return String.valueOf(getSubtotal() + getTax());
    }

    public Boolean comparingPrices() {
        return calculateTotalPrice().equals(String.valueOf(getTotal()));
    }

    public P06_FinishingOrder clickOnFinishButton() {
        Utility.clickingOnElement(driver, finishButton);
        return new P06_FinishingOrder(driver);
    }


}
