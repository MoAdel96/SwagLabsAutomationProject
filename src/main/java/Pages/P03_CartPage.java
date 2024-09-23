package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {
    private final WebDriver driver;
    private final By pricesOfSelectedProductsLocator = By.xpath("//div[contains(@class, 'inventory_item')]//button[text()='REMOVE']/ancestor::div[contains(@class, 'inventory_item')]//div[@class='inventory_item_price']\n");
    private float totalPrice = 0;


    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrice() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);

            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By elements = By.xpath("//div[contains(@class, 'inventory_item')]//button[text()='REMOVE']/ancestor::div[contains(@class, 'inventory_item')]//div[@class='inventory_item_price']\n[" + i + "]");
                String fullText = Utility.getText(driver, elements);
                LogsUtils.info("Total Price " + totalPrice);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));


            }
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparingPrices(String price) {
        return getTotalPrice().equals(price);
    }
}
