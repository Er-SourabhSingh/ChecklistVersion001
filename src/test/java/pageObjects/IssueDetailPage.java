package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;


import java.time.Duration;
import java.util.List;


public class IssueDetailPage extends BasePage {
    private final WebDriverWait wait;
    @FindBy(xpath = "//span[contains(@class,'badge badge-status')]")
    WebElement issueStatus;
    @FindBy(xpath = "//div[@id='flash_notice']")
    WebElement successMessage;
    @FindBy(xpath = "//div[@id='errorExplanation']")
    WebElement errorMessage;
    @FindBy(xpath = "//a[@accesskey='e']")
    WebElement btnEdit;
    @FindBy(xpath = "//div[@class='status attribute']//div[@class='value']")
    WebElement labelStatus;
    @FindBy(xpath = "//h2[@class='inline-flex']")
    WebElement issueTracker_Id;
    @FindBy(xpath = "//span[@class='icon-only icon-actions add-dropdown icon_for_padding' or @onclick='return false;']")
    WebElement actionChecklist;
    @FindBy(xpath = "//a[@id='add-checklist-item-btn']")
    WebElement addNewChecklist;
    @FindBy(xpath = "//a[@id='add-from-template-btn']")
    WebElement addFromTemplate;
    @FindBy(xpath = "//input[@id='checklist-item-subject']")
    WebElement txtChecklist;
    @FindBy(xpath = "//strong[normalize-space()='Checklist']")
    WebElement checklistTitle;
    @FindBy(xpath = "//a[@class='issues selected']")
    WebElement lnkIssues;
    @FindBy(xpath = "//div[contains(@class, 'checklist_option_dropdown') and not(contains(@style, 'display: none'))]//a[1]")
    WebElement btnAddSubChecklist;
    @FindBy(xpath = "//div[contains(@class, 'checklist_option_dropdown') and not(contains(@style, 'display: none'))]//a[2]")
    WebElement btnEditParentChecklist;
    @FindBy(xpath = "//div[contains(@class, 'checklist_option_dropdown') and not(contains(@style, 'display: none'))]//a[3]")
    WebElement btnDeleteParentChecklist;
    @FindBy(xpath = "//span[@class='checklist-item-text']/preceding-sibling::input[@type='text']")
    WebElement txtUpdateFieldParent;

    //parent checklist option
    @FindBy(xpath = "//div[@style='display: block;']//a[text()='Edit']")
    WebElement btnSubEdit;
    @FindBy(xpath = "//div[@style='display: block;']//a[text()='Delete']")
    WebElement btnSubDelete;
    @FindBy(xpath = "//span[@class='sub-checklist-item-text']/preceding-sibling::input[@type='text']")
    WebElement txtUpdateFieldSubChecklist;
    @FindBy(xpath = "//button[@id='confirmchecklistBtn']")
    WebElement confirmDeleteBtn;
    @FindBy(xpath = "//*[@id='fakeDynamicForm']/div/div[5]")
    private WebElement checklistDiv;

    public IssueDetailPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private List<WebElement> subchecklistsOfParentChecklist(String parentChecklist) {
        return driver.findElements(By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li[contains(@class, 'checklist-li')]//span[@class='sub-checklist-item-text']"));
    }

    private List<WebElement> checkboxesOfSubChecklistOfParentChecklist(String parentChecklist) {
        return driver.findElements(By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li[contains(@class, 'checklist-li')]//span[@class='sub-checklist-item-text']/preceding-sibling::input"));
    }

    //three dot subChecklist path
    private By threeDotOfSubChecklist(String parentChecklist, String subChecklist) {
        return By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li[contains(@class, 'checklist-li')]//span[text()='" + subChecklist + "']/ancestor::li[@class='sub-checklist-li']//span[contains(@class,'icon-only icon-actions')]");
    }

    private By chekboxOfParentChecklist(String parentChecklist) {
        return By.xpath("//span[text()='" + parentChecklist + "']/preceding-sibling::input[@type='checkbox']");
    }

    //check box of subchecklist path
    private By checkBoxOfSubChecklist(String parentChecklist, String subChecklist) {
        return By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li[contains(@class, 'checklist-li')]//span[text()='" + subChecklist + "']/preceding-sibling::input[@type='checkbox']");
    }

    private By dropdownOfSubChecklist(String parentChecklist, String subChecklist) {
        return By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li[contains(@class, 'checklist-li')]//span[text()='" + subChecklist + "']/ancestor::div[@class='sub-checklist-div']//select");

    }

    private By dateOfSubChecklist(String parentChecklist, String subChecklist) {
        return By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li[contains(@class, 'checklist-li')]//span[text()='" + subChecklist + "']/ancestor::li[@class='sub-checklist-li']//span[contains(@id,'done-date')]");
    }

    //XPath for expand collapsed button on checklist
    private By expandCollapseBtn(String parentChecklist) {
        return By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li//div[@class='icon-buttons']//button[@class='icon-test']");
    }

    public void clickOnExpand(String parentChecklist) {
        WebElement expandBtn = wait.until(ExpectedConditions.elementToBeClickable(expandCollapseBtn(parentChecklist)));
        expandBtn.click();
    }

    // XPath for the three-dot menu inside checklist item (dynamic) //parent checklist
    private By threeDotMenuParent(String parentChecklist) {
        return By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li//div[@class='icon-buttons']//span[contains(@class,'icon-only icon-actions')]");
    }

    // XPath for the three-dot menu inside checklist item (dynamic) //parent checklist
    private By progressbarParent(String parentChecklist) {
        return By.xpath("//span[text()='"+parentChecklist+"']/ancestor::li//div[@role='progressbar']");
    }

    private By progressPercentageParent(String parentChecklist) {
        return By.xpath("//span[text()='"+parentChecklist+"']/ancestor::li//div[@role='progressbar']//span");
    }

    private By txtFieldSubChecklist(String parentChecklist) {
        return By.xpath("//li[.//span[@class='checklist-item-text' and text()='" + parentChecklist + "']]//input[contains(@id,'sub-checklist-item-subject')]");
    }

    private By checkBoxParentChecklist(String parentChecklist) {
        return By.xpath("//span[text()='" + parentChecklist + "']/preceding-sibling::input[@class='checklist-completed-checkbox']");
    }


    public String getIssueStatus() {
        try {
            wait.until(ExpectedConditions.visibilityOf(issueStatus));
            return issueStatus.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void clickLinkIssues() {
        this.lnkIssues.click();
    }

    public String getSuccessMessage() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", successMessage);

            wait.until(ExpectedConditions.visibilityOf(successMessage));
            System.out.println(successMessage.getText());
            return successMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getErrorMessage() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorMessage);
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getIssueID() {
        return this.issueTracker_Id.getText().split("#")[1].trim();
    }

    public String getIssueTracker() {
        return this.issueTracker_Id.getText().split("#")[0].trim();
    }

    public boolean isActionChecklistEnabled() {
        return this.actionChecklist.isEnabled();
    }

    public void clickOnActionChecklist() {
        int retries = 3; // Number of retries
        for (int i = 0; i < retries; i++) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", actionChecklist);
                WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(actionChecklist));
                new Actions(driver).moveToElement(webElement).pause(500).click().perform(); // Added pause for stability
                break;
            }catch (TimeoutException e) {
                System.err.println("Timeout: Element not clickable - Attempt " + (i + 1) + " of " + retries);
            } catch (StaleElementReferenceException e) {
                System.err.println("Stale element: Retrying... (" + (i + 1) + " of " + retries + ")");
            } catch (ElementClickInterceptedException e) {
                System.err.println("Element click intercepted: Retrying... (" + (i + 1) + " of " + retries + ")");
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void clickOnAddFromTemplate(){
        this.addFromTemplate.click();
    }

    public void selectTemplateName(String nameTemplate){
        WebElement webElement = driver.findElement(By.xpath("//li[normalize-space()='"+nameTemplate+"']"));
        webElement.click();
    }

    public boolean isThreeDotChecklistBTnClickable() {
        if (actionChecklist.isEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    public String getTooltipThreeDotChecklist() {
        return actionChecklist.getAttribute("title");
    }

    public void clickAddNewChecklist() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewChecklist)).click();
    }

    public void setTxtChecklist(String checklistName) {
        wait.until(ExpectedConditions.visibilityOf(txtChecklist)).sendKeys(checklistName);
    }


    public void checkCheckBoxParentChecklist(String parentChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(chekboxOfParentChecklist(parentChecklist)));
        WebElement checkboxParent = wait.until(ExpectedConditions.elementToBeClickable(chekboxOfParentChecklist(parentChecklist)));
        if (!checkboxParent.isSelected()) {
            checkboxParent.click();
        }
    }

    public void uncheckCheckBoxParentChecklist(String parentChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(chekboxOfParentChecklist(parentChecklist)));
        WebElement checkboxParent = wait.until(ExpectedConditions.elementToBeClickable(chekboxOfParentChecklist(parentChecklist)));
        if (checkboxParent.isSelected()) {
            checkboxParent.click();
        }
    }

    public String getTooltipCheckBoxParentChecklist(String parentChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(chekboxOfParentChecklist(parentChecklist)));
        WebElement checkboxParent = wait.until(ExpectedConditions.visibilityOfElementLocated(chekboxOfParentChecklist(parentChecklist)));
        return checkboxParent.getAttribute("title");
    }

    public boolean isParentChecklistCheckboxEnabled(String parentChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(chekboxOfParentChecklist(parentChecklist)));
        WebElement checkboxParent = wait.until(ExpectedConditions.visibilityOfElementLocated(chekboxOfParentChecklist(parentChecklist)));
        return checkboxParent.isEnabled();
    }

    public boolean isParentChecklistCheckboxSelected(String parentChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(chekboxOfParentChecklist(parentChecklist)));
        WebElement checkboxParent = wait.until(ExpectedConditions.visibilityOfElementLocated(chekboxOfParentChecklist(parentChecklist)));
        return checkboxParent.isSelected();
    }
    public void clickThreeDotOfParentChecklist(String parentChecklist) {
        int retries = 3; // Number of retries
        for (int i = 0; i < retries; i++) {
            try {
                // Wait for the element to be visible and clickable
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(threeDotMenuParent(parentChecklist)));
                WebElement threeDot = wait.until(ExpectedConditions.elementToBeClickable(threeDotMenuParent(parentChecklist)));

                // Scroll the element into view

                // Use Actions class to move to the element and click
                new Actions(driver).moveToElement(threeDot).pause(500).click().perform(); // Added pause for stability
                break; // Exit loop if click is successful
            } catch (TimeoutException e) {
                System.err.println("Timeout while waiting for parent checklist three-dot menu: " + parentChecklist + " - Attempt " + (i + 1) + " of " + retries);
            } catch (StaleElementReferenceException e) {
                System.err.println("Stale element reference for parent checklist: " + parentChecklist + " - Retrying... (" + (i + 1) + " of " + retries + ")");
            } catch (ElementClickInterceptedException e) {
                System.err.println("Element click intercepted for parent checklist: " + parentChecklist + " - Retrying... (" + (i + 1) + " of " + retries + ")");
            } catch (Exception e) {
                System.err.println("Unexpected error while clicking parent checklist three-dot menu: " + parentChecklist + " - Attempt " + (i + 1) + " of " + retries);
                e.printStackTrace(); // Log the full exception for debugging
            }
        }
    }


    public String getTooltipOfParentChecklistThreeDot(String parentChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(threeDotMenuParent(parentChecklist)));
        WebElement threeDot = wait.until(ExpectedConditions.visibilityOfElementLocated(threeDotMenuParent(parentChecklist)));
        return threeDot.getAttribute("title");
    }

    public void clickAddSubChecklist() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddSubChecklist)).click();
    }

    public void setSubChecklistInParentChecklist(String parentChecklist, String subChecklist) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(txtFieldSubChecklist(parentChecklist)));
            WebElement txt = wait.until(ExpectedConditions.visibilityOfElementLocated(txtFieldSubChecklist(parentChecklist)));
            txt.sendKeys(subChecklist);

        } catch (Exception e) {
            System.err.println("Sub-checklist text field not found for parent: " + parentChecklist);
        }
    }

    public void clickOnEditIconOfParent() {
        btnEditParentChecklist.click();
    }

    public void clickOnDeleteButtonOfParent() {
        btnDeleteParentChecklist.click();
    }

    public void setTxtUpdateFieldParent(String updatedParentChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", this.txtUpdateFieldParent);
        this.txtUpdateFieldParent.clear();
        this.txtUpdateFieldParent.sendKeys(updatedParentChecklist);
    }

    public void clickOnThreeDotSubChecklist(String parentChecklist, String subChecklist) {
        int retries = 3; // Number of retries
        for (int i = 0; i < retries; i++) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(threeDotOfSubChecklist(parentChecklist, subChecklist)));
                WebElement threeDotSubChecklist = wait.until(ExpectedConditions.visibilityOfElementLocated(threeDotOfSubChecklist(parentChecklist, subChecklist)));
                // Use Actions class to move to the element and click
                new Actions(driver).moveToElement(threeDotSubChecklist).pause(500).click().perform(); // Added pause for stability
                break; // Exit loop if click is successful

            }catch (TimeoutException e) {
                System.err.println("Timeout while waiting for sub-checklist three-dot menu: " + subChecklist);
            } catch (Exception e) {
                System.err.println("Sub-checklist three-dot menu click failed for: " + subChecklist + " - Attempt " + (i + 1) + " of " + retries);
                e.printStackTrace(); // Print stack trace for better debugging
            }
        }
    }

    public String getTootipThreeDotSubChecklist(String parentChecklist, String subChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(threeDotOfSubChecklist(parentChecklist, subChecklist)));
        WebElement threeDotSubChecklist = wait.until(ExpectedConditions.visibilityOfElementLocated(threeDotOfSubChecklist(parentChecklist, subChecklist)));
        return threeDotSubChecklist.getAttribute("title");
    }

    public void checkCheckboxOfSubChecklist(String parentChecklist, String subChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }


    public void uncheckCheckboxOfSubChecklist(String parentChecklist, String subChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        if (checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public String getPercentageOfParent(String parent) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(progressPercentageParent(parent)));
        return driver.findElement(progressPercentageParent(parent)).getText();
    }

    public String getProgressBarWidth(String parent) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(progressbarParent(parent)));
        return driver.findElement(progressbarParent(parent)).getAttribute("Style").split(":")[1].trim().replace(";", "");
    }

    public boolean isProgressBarCompleted(String parent) {
        String width = getProgressBarWidth(parent);
        return width.contains("100%");
    }

    public boolean isSubChecklistCheckboxSelected(String parentChecklist, String subChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        return checkbox.isSelected();
    }

    public boolean isSubChecklistCheckboxEnabled(String parentChecklist, String subChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        return checkbox.isEnabled();
    }

    public String getTooltipOfSubChecklistCheckkbox(String parentChecklist, String subChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(checkBoxOfSubChecklist(parentChecklist, subChecklist)));
        return checkbox.getAttribute("title");
    }

    public void selectSubChecklistDropdown(String parentChecklist, String subChecklist, String option) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(dropdownOfSubChecklist(parentChecklist, subChecklist)));

        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownOfSubChecklist(parentChecklist, subChecklist)));
        Select select = new Select(dropdown);
        select.selectByVisibleText(option);
    }

    public String getSelectedSubChecklistOption(String parentChecklist, String subChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(dropdownOfSubChecklist(parentChecklist, subChecklist)));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownOfSubChecklist(parentChecklist, subChecklist)));
        Select select = new Select(dropdown);

        // Get the selected option
        WebElement selectedOption = select.getFirstSelectedOption();
        return selectedOption.getText();
    }

    public boolean isSubChecklistDropdownEnabled(String parentChecklist, String subChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(dropdownOfSubChecklist(parentChecklist, subChecklist)));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownOfSubChecklist(parentChecklist, subChecklist)));
        return dropdown.isEnabled();
    }

    public void clickOnSubChecklistEdit() {
        btnSubEdit.click();
    }

    public void setTxtUpdateFieldSubChecklist(String updatedSubChecklist) {
        txtUpdateFieldSubChecklist.clear();
        txtUpdateFieldSubChecklist.sendKeys(updatedSubChecklist);
    }

    public void clickSubChecklistDeleteBtn() {
        btnSubDelete.click();
    }

    public void clickOnConfirmDeleteBTn() {
        confirmDeleteBtn.click();
    }


    /**
     * ✅ Simulate pressing Enter
     */
    public void clickOnEnter() {
        new Actions(driver).sendKeys(Keys.ENTER).perform();
    }

    public void clickEditIssueBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", this.btnEdit);
        this.btnEdit.click();
    }

    public boolean isIssueClosed() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", labelStatus);
        return labelStatus.getText().split("\\n")[0].trim().equals("Closed");
    }
//span[@class='checklist-item-text' and contains(text(),'Ef')]

    public boolean isParentChecklistCreated(String parentChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//span[@class='checklist-item-text' and contains(text(),'" + parentChecklist + "')]")));
        return driver.findElement(By.xpath("//span[@class='checklist-item-text' and contains(text(),'" + parentChecklist + "')]")).isDisplayed();
    }

    public boolean isSubChecklistCreated(String parentChecklist, String subChecklist) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li[contains(@class, 'checklist-li')]//span[text()='" + subChecklist + "']")));
        return driver.findElement(By.xpath("//span[text()='" + parentChecklist + "']/ancestor::li[contains(@class, 'checklist-li')]//span[text()='" + subChecklist + "']")).isDisplayed();
    }
}

