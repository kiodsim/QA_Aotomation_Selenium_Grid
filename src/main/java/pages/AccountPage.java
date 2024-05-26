package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class AccountPage {
    private WebDriver driver;
    private WebDriverWait wait;


    public AccountPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //region Elements
    @FindBy(xpath = "//div[@class = 'center']/button[contains(text(), 'Transactions')]")
    private WebElement transactionsCenterButton;

    @FindBy(xpath = "//div[@class = 'center']/button[contains(text(), 'Deposit')]")
    private WebElement depositCenterButton;

    @FindBy(xpath = "//div[@class = 'center']/button[contains(text(), 'Withdrawl')]")
    private WebElement withdrawlCenterButton;

    @FindBy(xpath = "//input[@type = 'number']")
    private WebElement amountInput;

    @FindBy(xpath = "//button[@type = 'submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//span[@ng-show = 'message']")
    private WebElement message;

    @FindBy(xpath = "//strong[2]")
    private WebElement balance;

    @FindBy(xpath = "//button[text() = 'Back']")
    private WebElement backButton;

    @FindBy(xpath = "//tr[@class = 'ng-scope']")
    private List<WebElement> tableRows;
    //endregion

    //region Actions
    public void accountRefill() {
        long refillAmount = evaluateFibonacci();
        depositCenterButton.click();
        amountInput.sendKeys(String.valueOf(refillAmount));
        submitButton.click();
        Assert.assertEquals("Внос депозита не выполнен\nОжадалось: Deposit Successful\nПолучено: " + message.getText(), "Deposit Successful", message.getText());
    }

    public void accountWithdrawl() {
        long chargeAmount = evaluateFibonacci();
        withdrawlCenterButton.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//label[text() = 'Amount to be Withdrawn :']"))));


        amountInput.sendKeys(String.valueOf(chargeAmount));
        submitButton.click();
        Assert.assertEquals("Транзакция не выполнена\nОжадалось: Transaction Successful\nПолучено: " + message.getText(), "Transaction successful", message.getText());
        Assert.assertEquals("Ожидалось: баланс = 0\nПолучено: баланс = " + balance.getText(), "0", balance.getText());

        transactionsCenterButton.click();
        try {
            sleep(1000);
        } catch (Exception e) {
            Assert.fail();
        }
        backButton.click();
        transactionsCenterButton.click();
        if (tableRows.isEmpty()) {
            Assert.fail("В таблице не появилось ни одного значения");
        }
    }

    public void getTransactionInfo() {
        List<String> listRows = tableRows.stream()
                .map(x -> {
                    String dateTime = x.findElement(By.xpath("./td[1]")).getText().replace(" AM", "").replace(" PM", "");

                    String amount = x.findElement(By.xpath("./td[2]")).getText();

                    String transactionType = x.findElement(By.xpath("./td[3]")).getText();
                    return dateTime + " " + amount + " " + transactionType;
                })
                .collect(Collectors.toList());
        generateCsvFile("report.csv", listRows);
        attachCsvFileToAllure("report.csv");
    }

    public long evaluateFibonacci() {
        int N = LocalDate.now().getDayOfMonth() + 1;
        return fibonacci(N);
    }

    public long fibonacci(int N) {
        long a = 0, b = 1;
        for (int i = 2; i <= N; i++) {
            long temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }

    @Step("Генерация CSV файла")
    private void generateCsvFile(String csvFile, List<String> listRows) {
        try (FileWriter writer = new FileWriter(csvFile)) {
            for (int i = 0; i < listRows.size(); i++) {
                writer.append(listRows.get(i)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Прикрепление CSV файла к отчету Allure")
    public void attachCsvFileToAllure(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Allure.addAttachment("CSV Report", "text/csv", fis, ".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
