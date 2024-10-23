package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Pages.P04_CheckoutPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utilities.Utility.getAllCookies;
import static Utilities.Utility.restoreSession;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC04_CheckoutTest {
    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "Password");
    private final String FIRSTNAME = DataUtils.getJsonData("information", "fName") + "-" + Utility.getTimeStamp();
    private final String LASTNAME = DataUtils.getJsonData("information", "lName") + "-" + Utility.getTimeStamp();
    private final String ZIPCODE = new Faker().number().digits(5);
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
    public void checkoutStepOneTC() throws IOException {
        //TODO:Login Steps
        new P01_LoginPage(getDriver()).
                enterUsername(USERNAME).enterPassword(PASSWORD).clickOnLoginButton();
        LogsUtils.info("login Successfully");

        //TODO:Adding Products Steps
        new P02_LandingPage(getDriver()).addRandomProducts(2, 6).clickOnCartIcon();
        LogsUtils.info("products was added");

        //TODO:Go To Checkout Page Steps
        new P03_CartPage(getDriver()).clickOnCheckoutButton();

        //TODO:Filling Information Steps
        new P04_CheckoutPage(getDriver()).fillingInformationForm(FIRSTNAME, LASTNAME, ZIPCODE).clickOnContinueButton();
        LogsUtils.info("the information was filled");

        Assert.assertTrue(Utility.verifyURl(getDriver(), DataUtils.getPropertyValue("environment", "CHECKOUT_URL")));

    }


    @AfterMethod
    public void quit() throws IOException {
        quitDriver();
    }
}
