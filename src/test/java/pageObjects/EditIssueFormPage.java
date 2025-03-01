package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditIssueFormPage extends BasePage {
    @FindBy(xpath = "//select[@id='issue_status_id']")
    WebElement statusDropdown;
    @FindBy(xpath = "//input[@value='Submit']")
    WebElement btnSubmit;
    @FindBy(xpath = "//li[contains(text(),'Issue cannot be closed as there are incomplete che')]")
    WebElement errMessage;
    private WebDriverWait wait;

    public EditIssueFormPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectStatus(String status) {
        Select selectStatus = new Select(statusDropdown);
        new Actions(driver).scrollToElement(statusDropdown).perform();

        if (status.equals("Closed")) {
            selectStatus.selectByVisibleText("Closed");
        }

    }

    public void clickOnSubmitBtn() {
        this.btnSubmit.click();
    }

    public String errMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errMessage));
            return errMessage.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
