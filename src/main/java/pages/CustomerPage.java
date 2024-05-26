package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CustomerPage {
    private WebDriver driver;

    public CustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //region Elements
    @FindBy(xpath = "//select[@id= 'userSelect']")
    private WebElement yourNameDropDown;

    @FindBy(xpath = "//option[@value]")
    private List<WebElement> yourNameValues;

    @FindBy(xpath = "//button[text() = 'Login']")
    private WebElement loginButton;
    //endregion

    //region Actions
    public void selectUser(String userName) {
        yourNameDropDown.click();
        yourNameValues.stream()
                .filter(x -> x.getText().equals(userName))
                .findFirst().get().click();
        loginButton.click();
    }
    //endregion
}
