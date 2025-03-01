package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProjectListPage extends BasePage {
    @FindBy(xpath = "//h2[normalize-space()='Projects']")
    private WebElement pageTitle;
    @FindBy(xpath = "//div[@id='projects-index']//a")
    private List<WebElement> lnkOfProjects;

    public ProjectListPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return this.pageTitle.getText();
    }

    public void clickToOpenProject(String projectName) {
        for (WebElement el : this.lnkOfProjects) {
            if (el.getText().equalsIgnoreCase(projectName)) {
                el.click();
                break;
            }
        }
    }
}
