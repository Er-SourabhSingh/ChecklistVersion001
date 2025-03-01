package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectOverviewPage extends BasePage {
    @FindBy(xpath = "//span[@class='current-project']")
    WebElement projectTitle;
    @FindBy(xpath = "//a[@class='issues']")
    WebElement lnkIssues;

    public ProjectOverviewPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectTitle() {
        return this.projectTitle.getText();
    }

    public void clickIssuesLnk() {
        this.lnkIssues.click();
    }

}
