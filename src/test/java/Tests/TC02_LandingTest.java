package Tests;


import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_LandingTest {

    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "Password");


    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogsUtils.info("Browser was opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info(" browser is redirected to the url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void checkingNumberOfSelectedProductsTC() {
        new P01_LoginPage(getDriver()).enterUsername(USERNAME).enterPassword(PASSWORD).clickOnLoginButton().addAllProductsToCart();
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());

    }

    @Test
    public void addingRandomProductsToCartTC() {
        new P01_LoginPage(getDriver()).enterUsername(USERNAME)
                .enterPassword(PASSWORD).clickOnLoginButton().addRandomProducts(3, 6);
        Assert
                .assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());

    }

    @Test
    public void clickOnCartIcon() throws IOException {
        new P01_LoginPage(getDriver()).enterUsername(USERNAME).enterPassword(PASSWORD)
                .clickOnLoginButton().clickOnCartIcon();
        Assert.assertTrue(new P02_LandingPage(getDriver()).verifyCartPageURl(DataUtils.getPropertyValue("environment", "CART_URL")));
    }


    @AfterMethod
    public void quit() throws IOException {
        quitDriver();
    }

}
