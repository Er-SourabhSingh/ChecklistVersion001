package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class ChecklistTemplateForm extends BasePage{
    private final WebDriverWait wait;
    @FindBy (xpath = "//select[@id='tracker_id']")
    WebElement trackerDropdown;
    @FindBy (xpath = "//input[@name='checklist_template[name]']")
    WebElement txtTemplateName;
    @FindBy(xpath = "//a[@id='add-parent-checklist']")
    WebElement btnAddParentChecklist;
    @FindBy(xpath = "//input[@name='commit']")
    WebElement btnCreateTemplate;





    private By parentChecklistTxtFieldXPath(int i){
        return By.xpath("//input[@name='checklist_template[checklist_template_items_attributes]["+i+"][checklist_title]']");
    }

    private By btnAddSubChecklistXpath(int i){
        return By.xpath("//div[@id='parent-checklists']//div["+i+"]//p[2]//a[1]");
    }

    private By btnDeleteParentChecklistXpath(int i){
        return By.xpath("//body/div[@id='wrapper']/div[@id='main']/div[@id='content']/form/div[@class='box tabular']/div[@id='parent-checklists']/div["+i+"]/p[1]/a[1]");
    }

    private By btnDeleteSubChecklistXpath(int i,int j){
        return By.xpath("//div[@id='parent-checklists']//div["+i+"]//div[1]//div["+j+"]//p[1]//a[1]");
    }


    private By subChecklistTxtFieldXpath(int i,int j){
        return By.xpath("//input[@name='checklist_template[checklist_template_items_attributes]["+i+"][sub_items_attributes]["+j+"][checklist_title]']");
    }


    public ChecklistTemplateForm(WebDriver driver){
        super(driver);
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
    }

    public void selectTracker(String tracker){
        try{
            Select dropdown = new Select(this.trackerDropdown);
            dropdown.selectByVisibleText(tracker);
        }catch (Exception e){
            System.err.println("invalid tracker");
        }
    }

    public void setTxtTemplateName(String templateName){
        this.txtTemplateName.sendKeys(templateName);
    }

    public void clickAddBtnOfParentChecklist(){
        this.btnAddParentChecklist.click();
    }


    public void clickOnCreateTemplateBtn(){
        this.btnCreateTemplate.click();
    }

    public void setParentChecklistTxt(int index,String parentChecklist){
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(parentChecklistTxtFieldXPath(index)));
        webElement.sendKeys(parentChecklist);
    }

    public void clickAddSubChecklistBtn(int index){
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(btnAddSubChecklistXpath(index)));
        webElement.click();
    }


    public void setSubChecklistTxt(int indexParent,int indexSub, String subChecklist){
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(subChecklistTxtFieldXpath(indexParent,indexSub)));
        webElement.sendKeys(subChecklist);
    }

    public void clickOnDeleteBtnOfParent(int index){
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(btnDeleteParentChecklistXpath(index)));
        webElement.click();
    }

    public void clickOnDeleteBtnOfParent(int indexP,int indexS){
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(btnDeleteSubChecklistXpath(indexP,indexS)));
        webElement.click();
    }



}
