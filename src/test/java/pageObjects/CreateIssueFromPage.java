package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CreateIssueFromPage extends BasePage {
    @FindBy(xpath = "//select[@id='issue_tracker_id']")
    WebElement dropdownTracker;
    @FindBy(xpath = "//input[@id='issue_subject']")
    WebElement txtSubject;
    @FindBy(xpath = "//input[@name='commit']")
    WebElement btnCreate;

    public CreateIssueFromPage(WebDriver driver) {
        super(driver);
    }

    public void selectTracker(String trackerName){
        Select select = new Select(dropdownTracker);
        select.selectByVisibleText(trackerName);
    }

    public void setTxtSubject(String subject) {
        this.txtSubject.sendKeys(subject);
    }

    public void clickCreate() {
        this.btnCreate.click();
    }


}
