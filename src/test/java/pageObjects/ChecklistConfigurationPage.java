package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChecklistConfigurationPage extends BasePage {
    @FindBy(xpath = "//input[@id='settings_block_issue_closing']")
    WebElement checkboxBlockIssueClosing;
    @FindBy(xpath = "//a[@id='tab-checklist_configuration']")
    WebElement tabGeneral;
    @FindBy(xpath = "//a[@id='tab-checklist_template']")
    WebElement tabTemplate;
    @FindBy(xpath = "//a[@id='create-checklist-template-link']")
    WebElement btnAddNewTemplate;
    @FindBy(xpath = "//input[@name='commit']")
    WebElement btnApply;
    @FindBy(xpath = "//div[@id='flash_notice']")
    WebElement successMessage;
    @FindBy(xpath = "//a[@class='projects']")
    WebElement lnkProjects;

    public ChecklistConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public void enabledBlockedIssueClose() {
        if (!checkboxBlockIssueClosing.isSelected()) {
            checkboxBlockIssueClosing.click();
        }
    }

    public void disableBlockedIssueClose() {
        if (checkboxBlockIssueClosing.isSelected()) {
            checkboxBlockIssueClosing.click();
        }
    }

    public boolean isSelectedBlockedIssueClosing() {
        return checkboxBlockIssueClosing.isSelected();
    }

    public void clickOnApply() {
        btnApply.click();
    }

    public void openGeneralTab() {
        this.tabGeneral.click();
    }

    public void openTemplateTab() {
        this.tabTemplate.click();
    }

    public String isSuccessMessageDisplayed() {
        return successMessage.getText();
    }

    public void clickOnProjectsLink() {
        this.lnkProjects.click();
    }

    public void clickAddNewChecklistBtn(){
        this.btnAddNewTemplate.click();
    }



}
