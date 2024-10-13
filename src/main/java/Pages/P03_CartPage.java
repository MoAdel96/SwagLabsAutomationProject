package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {
    private final WebDriver driver;
    private final By pricesOfSelectedProductsLocator = By
            .xpath("//button[.=\"REMOVE\"]//preceding-sibling::div[@class='inventory_item_price']");
    private final By checkOutButton = By.xpath("//a[contains(@class,'checkout_button')]");
    private float totalPrice = 0;


    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrice() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);

            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By elements = By
                        .xpath("(//button[.=\"REMOVE\"]//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String fullText = Utility.getText(driver, elements);

                totalPrice += Float.parseFloat(fullText.replace("$", ""));


            }
            LogsUtils.info("Total Price on cart " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparingPrices(String price) {
        return getTotalPrice().equals(price);
    }

    public P04_CheckoutPage clickOnCheckoutButton(WebDriver driver) {
        Utility.clickingOnElement(driver, checkOutButton);
        return new P04_CheckoutPage(driver);
    }
}
