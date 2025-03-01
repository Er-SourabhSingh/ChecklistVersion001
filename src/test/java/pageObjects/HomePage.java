package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(xpath = "//a[@class='projects']")
    WebElement lnkProject;
    @FindBy(xpath = "//h2[normalize-space()='Home']")
    WebElement homeTitle;
    @FindBy(xpath = "//a[@class='administration']")
    WebElement lnkAdministration;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnProjects() {
        this.lnkProject.click();
    }

    public void clickONAdministrator() {
        this.lnkAdministration.click();
    }

    public String getHomeTitle() {
        return homeTitle.getText();
    }


}
