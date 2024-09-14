package Tests;

import Pages.P01_LoginPage;
import Utilities.DataUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;

public class TC01_LoginTest {
    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "Password");


    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        getDriver().get(DataUtils.getPropertyValue("environment", "BASE_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void validLoginTC() throws IOException {
        new P01_LoginPage(getDriver()).enterUsername(USERNAME).enterPassword(PASSWORD).clickOnLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTC(DataUtils
                .getPropertyValue("environment", "HOME_URL")));

    }


    @AfterMethod
    public void quit() {

    }
}
