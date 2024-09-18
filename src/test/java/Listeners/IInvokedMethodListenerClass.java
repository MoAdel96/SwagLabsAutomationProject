package Listeners;

import Pages.P02_LandingPage;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokedMethodListenerClass implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogsUtils.info(" Test Case " + testResult.getName() + " Failed ");

            Utility.takeScreenShot(getDriver(), testResult.getName()); //TC-2024-12-05-8-17pm
            Utility.takeFullScreenShot(getDriver(), new P02_LandingPage(getDriver()).getNumberOfSelectedProductsOnCartIcon());
        }
    }
}
