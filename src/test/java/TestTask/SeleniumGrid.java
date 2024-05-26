package TestTask;

import drivers.PageNavigator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.AccountPage;
import pages.CustomerPage;
import pages.LoginPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SeleniumGrid {

    private WebDriver driver;
    private PageNavigator navigator;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(new URL("http://localhost:5555/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        navigator = new PageNavigator(driver);
    }

    @Test
    public void testTask() {
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        LoginPage loginPage = navigator.loadLoginPage();
        loginPage.clickCustomerLogin();

        CustomerPage customerPage = navigator.loadCustomerPage();
        customerPage.selectUser("Harry Potter");

        AccountPage accountPage = navigator.loadAccountPage();
        accountPage.accountRefill();
        accountPage.accountWithdrawl();
        accountPage.getTransactionInfo();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
