package drivers;

import org.openqa.selenium.WebDriver;
import pages.AccountPage;
import pages.CustomerPage;
import pages.LoginPage;

public class PageNavigator {
    private WebDriver driver;

    public PageNavigator(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage loadLoginPage() {
        return new LoginPage(driver);
    }

    public CustomerPage loadCustomerPage() {
        return new CustomerPage(driver);
    }

    public AccountPage loadAccountPage() {
        return new AccountPage(driver);
    }
}
