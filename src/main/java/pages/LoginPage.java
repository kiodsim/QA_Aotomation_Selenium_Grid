package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //region Elements
    @FindBy(xpath = "//button[text() = 'Customer Login']")
    private WebElement customerLoginButton;

    @FindBy(xpath = "//button[text() = 'Bank Manager Login']")
    private WebElement bankManagerLoginButton;
    //endregion

    //region Actions
    public void clickCustomerLogin() {
        customerLoginButton.click();
    }
    //endregion
}
