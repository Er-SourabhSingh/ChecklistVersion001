package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class PluginsPage extends BasePage {
    @FindBy(xpath = "//span[normalize-space()='Redmineflux Checklist Plugin']")
    WebElement checklistPluginTitle;
    @FindBy(xpath = "//a[@href='/settings/plugin/redmineflux_checklist']")
    WebElement lnkChecklistConfiguration;
    @FindBy(xpath = "//span[contains(text(),'Enhance Redmine effortlessly manage task lists wit')]")
    WebElement checklistDescription;
    @FindBy(xpath = "//a[@href='https://www.redmineflux.com/knowledge-base/plugins/checklist-plugin/']")
    WebElement lnkChecklistKnowledgeBase;
    @FindBy(xpath = "//tr[@id='plugin-redmineflux_checklist']//a[@target='_blank'][normalize-space()='Redmineflux - Powered by Zehntech Technologies Inc']")
    WebElement lnkAuthor;

    public PluginsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isChecklistInstalled() {
        return checklistPluginTitle.isDisplayed();
    }


    public void scrollToChecklistPlugin() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'start', inline: 'nearest'});", checklistPluginTitle);
        } catch (Exception e) {
            Assert.fail("Scrolling failed: " + e.getMessage());
        }
    }

    public String getChecklistPluginName() {
        return this.checklistPluginTitle.getText();
    }

    public void openChecklistConfiguration() {
        this.lnkChecklistConfiguration.click();
    }

    public String getChecklistPluginDescription() {

        return checklistDescription.getText();
    }

    public void clickKnowledgeBaseLink() {
        this.lnkChecklistKnowledgeBase.click();
    }

    public void clickAuthorLink() {
        this.lnkAuthor.click();
    }

}
