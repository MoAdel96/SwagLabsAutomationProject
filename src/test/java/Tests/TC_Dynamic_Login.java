package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
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
public class TC_Dynamic_Login {


    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogsUtils.info("Browser was opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info(" browser is redirected to the url");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }


    @Test
    public void validLoginTC() throws IOException {
        // Use the correct JSON file name and field path
        String username = DataUtils.getData("dynamicLogin", "validData.username");
        String password = DataUtils.getData("dynamicLogin", "validData.password");

        new P01_LoginPage(getDriver())
                .enterUsername(username)
                .enterPassword(password)
                .clickOnLoginButton();

        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTC(
                DataUtils.getPropertyValue("environment", "HOME_URL")
        ));
        LogsUtils.info("after assertion");
    }


    @Test
    public void invalidLoginTC() throws IOException {

        // Use the correct JSON file name and field path
        String username = DataUtils.getData("dynamicLogin", "invalidData.username");
        String password = DataUtils.getData("dynamicLogin", "invalidData.password");

        LogsUtils.info("username is " + username + "password is " + password);

        new P01_LoginPage(getDriver()).enterUsername(username).enterPassword(password).clickOnLoginButton();
        LogsUtils.info("cannot log in");

        Assert.assertFalse(new P01_LoginPage(getDriver()).assertLoginTC(DataUtils
                .getPropertyValue("environment", "HOME_URL")));

        LogsUtils.info("after assertion.");

    }


    @AfterMethod
    public void quit() throws IOException {
        quitDriver();
    }
}



