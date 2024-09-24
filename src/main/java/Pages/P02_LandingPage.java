package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

public class P02_LandingPage {
    static float totalPrice = 0.0f;
    private static List<WebElement> allProducts;
    private static List<WebElement> SelectedProducts;
    private final By addToCartButtonForAllProducts = By.xpath("//button[@class]");
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final WebDriver driver;
    private final By numberOfSelectedProducts = By.xpath("//button[text()='REMOVE']");
    private final By pricesOfSelectedProductsLocator = By.xpath("//button[.=\"REMOVE\"]//preceding-sibling::div[@class='inventory_item_price']");


    private final By cartIcon = By.id("shopping_cart_container");

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

    public P02_LandingPage addRandomProducts(int numberOfProductsNeeded, int totalNumberOfProducts) {
        Set<Integer> randomNumbers = Utility.generateUniqueNumber(numberOfProductsNeeded, totalNumberOfProducts);

        for (int random : randomNumbers) {
            LogsUtils.info("randomNumber " + random);
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + random + "]");
            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);

        }
        return this;
    }


    public boolean comparingNumberOfSelectedProductsWithCart() {
        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());
    }

    public P03_CartPage clickOnCartIcon() {
        Utility.clickingOnElement(driver, cartIcon);
        return new P03_CartPage(driver);
    }

    public Boolean verifyCartPageURl(String expectedURl) {
        try {
            Utility.generalWait(driver).until(ExpectedConditions.urlToBe(expectedURl));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public String getTotalPriceOfSelectedProducts() {
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
}

