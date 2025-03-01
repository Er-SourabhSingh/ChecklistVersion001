package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@id='username']")
    WebElement txtUserName;
    @FindBy(xpath = "//input[@id='password']")
    WebElement txtUserPassword;
    @FindBy(xpath = "//input[@id='login-submit']")
    WebElement btnLogin;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickBtnLogin() {
        btnLogin.click();
    }


    public void setTxtUserName(String name) {
        txtUserName.sendKeys(name);
    }

    public void setTxtUserPassword(String pwd) {
        txtUserPassword.sendKeys(pwd);
    }


}
