package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P02_LandingPage {
    private static List<WebElement> allProducts;
    private static List<WebElement> SelectedProducts;
    private final By addToCartButtonForAllProducts = By.xpath("//button[@class]");
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final WebDriver driver;
    private final By numberOfSelectedProducts = By.xpath("//button[text()='REMOVE']");

    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    public By getNumberOfSelectedProductsOnCartIcon() {
        return numberOfProductsOnCartIcon;
    }

    public P02_LandingPage addAllProductsToCart() {
        allProducts = driver.findElements(addToCartButtonForAllProducts);
        LogsUtils.info("number of all products" + allProducts.size());
        for (int i = 1; i <= allProducts.size(); i++) {
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + i + "]");
            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);

        }
        return this;
    }

    public String getNumberOfProductsOnCartIcon() {

        try {

            LogsUtils.info("number of products on cart " + Utility.getText(driver, numberOfProductsOnCartIcon));
            return Utility.getText(driver, numberOfProductsOnCartIcon);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
        }

        return "0";
    }


    public String getNumberOfSelectedProducts() {

        try {
            SelectedProducts = driver.findElements(numberOfSelectedProducts);
            LogsUtils.info("selected products " + SelectedProducts.size());

            return String.valueOf(SelectedProducts.size());
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }


    public boolean comparingNumberOfSelectedProductsWithCart() {
        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());
    }


}
