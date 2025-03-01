package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.util.List;

public class IssueListPage extends BasePage {


    @FindBy(xpath = "//legend[@class='icon icon-expanded']")
    WebElement expandFilter;
    @FindBy(xpath = "//select[@id='add_filter_select']")
    WebElement dropdownAddFiler;
    @FindBy(xpath = "//a[normalize-space()='Apply']")
    WebElement btnApply;
    @FindBy(xpath = "//tbody//td[2]//a")
    List<WebElement> lnkIds;
    @FindBy(xpath = "//p[@class='nodata']")
    WebElement noData;
    @FindBy(xpath = "//a[@class='icon icon-add new-issue']")
    WebElement btnAddNewIssue;

    public IssueListPage(WebDriver driver) {
        super(driver);
    }

    public void clickNewIssueBtn() {
        this.btnAddNewIssue.click();
    }

    public void addAuthorFilter() {
        this.dropdownAddFiler.click();
        Select select = new Select(dropdownAddFiler);
        select.selectByValue("author_id");
    }

    public void selectStatus(String status) {
        //Select the Status (open or closed)
        WebElement statusDropdown = driver.findElement(By.id("operators_status_id"));

        Select operatorSelect = new Select(statusDropdown);
        if (status.equalsIgnoreCase("open")) {
            operatorSelect.selectByValue("o"); // "o"
        } else if (status.equalsIgnoreCase("closed")) {
            operatorSelect.selectByValue("c"); // "c"
        } else {
            System.out.println("Invalid operator provided.");
            return;
        }

    }

    public void selectOperatorAndAuthor(String operator, String authorName) {
        // Select the operator (either "is" or "is not")
        WebElement operatorDropdown = driver.findElement(By.id("operators_author_id"));
        Select operatorSelect = new Select(operatorDropdown);

        // Determine the operator value and select it
        if (operator.equalsIgnoreCase("is")) {
            operatorSelect.selectByValue("="); // "is"
        } else if (operator.equalsIgnoreCase("is not")) {
            operatorSelect.selectByValue("!"); // "is not"
        } else {
            System.out.println("Invalid operator provided.");
            return;
        }

        // Select the author name from the value dropdown
        WebElement authorDropdown = driver.findElement(By.id("values_author_id_1"));
        Select authorSelect = new Select(authorDropdown);
        if (authorName.equalsIgnoreCase("me")) {
            authorSelect.selectByValue("me"); // Select << me >> option
        } else {
            // Select other author names from the list
            authorSelect.selectByVisibleText(authorName); // Select the given author name
        }
    }

    public void clickApply() {
        this.btnApply.click();
    }

    public void openFirstIssue() {
        if (!lnkIds.isEmpty())
            lnkIds.get(0).click();
    }


}
