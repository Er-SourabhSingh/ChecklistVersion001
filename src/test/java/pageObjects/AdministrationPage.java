package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdministrationPage extends BasePage {
    @FindBy(xpath = "//a[@class='icon icon-plugins plugins']")
    WebElement lnkPlugins;

    public AdministrationPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnPlugins() {
        this.lnkPlugins.click();
    }
}
