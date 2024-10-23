package Tests;


import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utilities.Utility.*;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_LandingTest {
    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "Password");
    private Set<Cookie> cookies;

    @BeforeClass
    private void login() throws IOException {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogsUtils.info("Browser was opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info(" browser is redirected to the url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME).enterPassword(PASSWORD).clickOnLoginButton();
        cookies = getAllCookies(getDriver());
        quitDriver();

    }

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogsUtils.info("Browser was opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "HOME_URL"));
        LogsUtils.info(" browser is redirected to the url");
        restoreSession(getDriver(), cookies);
        getDriver().navigate().refresh();
    }

    @Test
    public void checkingNumberOfSelectedProductsTC() {
        new P02_LandingPage(getDriver()).addAllProductsToCart();
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());

    }

    @Test
    public void addingRandomProductsToCartTC() {
        new P02_LandingPage(getDriver()).addRandomProducts(3, 6);
        Assert
                .assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());

    }

    @Test
    public void clickOnCartIcon() throws IOException {
        new P02_LandingPage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(verifyURl(getDriver(), DataUtils.getPropertyValue("environment", "CART_URL")));
    }


    @AfterMethod
    public void quit() throws IOException {
        quitDriver();
    }

    @AfterClass
    public void deleteSession() {
        cookies.clear();
    }


}
