package testBase;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pageObjects.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class BaseClass {
    public static WebDriver driver;
    public Properties properties;
    public Logger logger;

    @BeforeClass(groups = {"Sanity", "Master", "Regression"})
    @Parameters({"os", "browser"})
    public void setUp(String os, String br) throws IOException {
        logger = LogManager.getLogger(this.getClass());
        //loading config.properties file
        FileReader fileReader = new FileReader("./src//test//resources//config.properties");
        properties = new Properties();
        properties.load(fileReader);

        logger = LogManager.getLogger(this.getClass());

        if (properties.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            if (os.equalsIgnoreCase("window")) {
                capabilities.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            } else {
                System.out.println("os is invalid");
                return;
            }

            switch (br.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;
                case "edge":
                    capabilities.setBrowserName("edge");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("invalid browser");
                    return;
            }
            //set url next time
            driver = new RemoteWebDriver(new URL("http://localhost:4444"), capabilities);
        }
        if (properties.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (br.toLowerCase()) {
                case "chrome":
                   /* ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless"); // Enable headless mode
                    chromeOptions.addArguments("--window-size=1920,1080"); // Set screen size
                    chromeOptions.addArguments("--disable-gpu"); // Disable GPU acceleration (sometimes needed for headless)
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    chromeOptions.setExperimentalOption("useAutomationExtension", false);
                    driver = new ChromeDriver(chromeOptions);*/
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    /*EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--headless"); // Run in headless mode
                    edgeOptions.addArguments("--disable-gpu"); // Disable GPU for better performance
                    edgeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    edgeOptions.setExperimentalOption("useAutomationExtension", false);
                    WebDriver driver = new EdgeDriver(edgeOptions);*/
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    /*FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--headless");// Run in headless mode
                    firefoxOptions.addArguments("--disable-gpu"); // Disable GPU for better performance
                    firefoxOptions.addPreference("dom.webdriver.enabled", false);
                    firefoxOptions.addPreference("useAutomationExtension", false);
                    driver = new FirefoxDriver(firefoxOptions);*/
                    driver = new FirefoxDriver();
                    break;
                default:
                    System.out.println("invalid browser");
                    return;
            }

        } else {
            System.out.println("invalid execution environment");
            return;
        }

        //driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(properties.getProperty("url1"));
        driver.manage().window().maximize();
    }

    @AfterClass(groups = {"Sanity", "Master", "Regression"})
    public void tearDown() {
        driver.quit();
    }


    public String captureScreen(String tName) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE); //temporary file

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tName + "_" + timeStamp + ".png";
        File targeFile = new File(targetFilePath);
        sourceFile.renameTo(targeFile);

        return targetFilePath;
    }

    public String randomChecklist(int length) {
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(alphabets.charAt(random.nextInt(alphabets.length())));
        }

        return sb.toString();

    }

    public void login(String username, String password) {
        logger.info("===== Login - Start Execution =====");
        try {
            // Initialize Page Object
            LoginPage loginPage = new LoginPage(driver);

            // Step 1: Login with provided username and password
            logger.info("Step 1: Login with provided credentials");
            loginPage.setTxtUserName(username);
            loginPage.setTxtUserPassword(password);
            loginPage.clickBtnLogin();

            logger.info("Login successful for user: " + username);
        } catch (Exception e) {
            logger.error("Login Failed for user: " + username + " - " + e.getMessage());
            Assert.fail("Login failed: " + e.getMessage());
        } finally {
            logger.info("=== Login Test Completed ===");
        }
    }

    /**
     * ================================== Helper methods ============================
     **/


    protected String createIssue(String subject) throws Exception{
        IssueListPage issueListPage = new IssueListPage(driver);
        CreateIssueFromPage createIssueFromPage = new CreateIssueFromPage(driver);
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        logger.info("Click on Add new issue btn");
        issueListPage.clickNewIssueBtn();
        logger.info("Enter subject for issue");
        createIssueFromPage.setTxtSubject(subject);
        logger.info("Click on create btn to create issue");
        createIssueFromPage.clickCreate();
        return issueDetailPage.getIssueID();
    }

    protected boolean closeIssue(String issueId) throws Exception {
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        EditIssueFormPage editIssueFormPage = new EditIssueFormPage(driver);
        logger.info("Click on edit button on issue detail form of " + issueId);
        issueDetailPage.clickEditIssueBtn();
        logger.info("select closed status");
        editIssueFormPage.selectStatus("Closed");
        logger.info("Click on Submit button");
        editIssueFormPage.clickOnSubmitBtn();

        if (issueDetailPage.getIssueStatus().equalsIgnoreCase("Closed"))
            return true;
        else if (!editIssueFormPage.errMessage().isEmpty())
            return false;
        else
            return false;

    }

    protected void addParentChecklist(String parentChecklist) throws Exception {
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        logger.info("Click on three dot or action icon of Checklist Section on issue detail page");
        issueDetailPage.clickOnActionChecklist();
        logger.info("Click add button of Checklist Dropdown");
        issueDetailPage.clickAddNewChecklist();
        logger.info("Enter checklist name in Checklist field");
        issueDetailPage.setTxtChecklist(parentChecklist);
        logger.info("Click Enter to create checklist");
        issueDetailPage.clickOnEnter();

    }

    protected void addSubChecklist(String parentChecklist, String subChecklist) throws Exception {
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        logger.info("Click on three dot of parentchecklist");
        issueDetailPage.clickThreeDotOfParentChecklist(parentChecklist);
        logger.info("Click on add subchecklist btn");
        issueDetailPage.clickAddSubChecklist();
        logger.info("Enter subchecklist name");
        issueDetailPage.setSubChecklistInParentChecklist(parentChecklist, subChecklist);
        logger.info("Click on Enter button");
        issueDetailPage.clickOnEnter();

    }

    protected void updateParentChecklist(String parentChecklist, String updatedChecklist) throws Exception {
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        logger.info("Click on three dot of parentchecklist");
        issueDetailPage.clickThreeDotOfParentChecklist(parentChecklist);
        logger.info("Click edit button");
        issueDetailPage.clickOnEditIconOfParent();
        logger.info("Update checklist name");
        issueDetailPage.setTxtUpdateFieldParent(updatedChecklist);
        logger.info("Click on Enter");
        issueDetailPage.clickOnEnter();

    }

    protected void updateSubChecklist(String parentChecklist, String subChecklist, String updatedSubChecklist) throws Exception {
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        issueDetailPage.clickOnThreeDotSubChecklist(parentChecklist, subChecklist);
        issueDetailPage.clickOnSubChecklistEdit();
        issueDetailPage.setTxtUpdateFieldSubChecklist(updatedSubChecklist);
        issueDetailPage.clickOnEnter();


    }

    protected void deleteParentChecklist(String parentChecklist) throws Exception{
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        logger.info("Click on Three Dot of Parent Checklist");
        issueDetailPage.clickThreeDotOfParentChecklist(parentChecklist);
        logger.info("Click on Delete button");
        issueDetailPage.clickOnDeleteButtonOfParent();
        logger.info("Click on Confirm button In Delete Checklist popup");
        issueDetailPage.clickOnConfirmDeleteBTn();

    }

    protected void deleteSubChecklist(String parentChecklist, String subChecklist) throws Exception{
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        logger.info("Click on Three Dot of Sub Checklist");
        issueDetailPage.clickOnThreeDotSubChecklist(parentChecklist, subChecklist);
        logger.info("Click on Delete button");
        issueDetailPage.clickSubChecklistDeleteBtn();
        logger.info("Click on Confirm button In Delete Cheklist popup");
        issueDetailPage.clickOnConfirmDeleteBTn();

    }






}
