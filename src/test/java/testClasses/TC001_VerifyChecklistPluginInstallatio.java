package testClasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.AdministrationPage;
import pageObjects.HomePage;
import pageObjects.PluginsPage;
import testBase.BaseClass;

import java.time.Duration;
import java.util.Set;

public class TC001_ChecklistPluginInstallation extends BaseClass {
    private HomePage homePage;
    private AdministrationPage administrationPage;
    private PluginsPage pluginsPage;


    @Test(priority = 1, groups = {"Sanity", "Regression", "Master"})
    public void verifyChecklistInstalled() {
        homePage = new HomePage(driver);
        administrationPage = new AdministrationPage(driver);
        pluginsPage = new PluginsPage(driver);
        logger.info("===== TC001: Verify Checklist Plugin Installation - Start =====");
        try {
            login(properties.getProperty("user1"), properties.getProperty("user1_pwd"));
            navigateToPluginsPage();

            logger.info("Step 3: Scroll and Verify Plugin");
            pluginsPage.scrollToChecklistPlugin();

            Assert.assertEquals(pluginsPage.getChecklistPluginName(), "Redmineflux Checklist Plugin", " Plugin NOT Installed!");

            logger.info("✔ Checklist Plugin is installed successfully.");
        } catch (Exception e) {
            logger.error("Plugin Verification Failed: " + e.getMessage());
            Assert.fail("Plugin installation verification failed.");
        } finally {
            logger.info("=== Checklist Plugin Verification Completed ===");
        }
    }

    @Test(priority = 2, groups = {"Master", "Regression"}, dependsOnMethods = "verifyChecklistInstalled")
    public void verifyChecklistPluginDescription() {
        logger.info("===== Verify Checklist Plugin Description =====");
        pluginsPage = new PluginsPage(driver);
        try {
            String expectedDescription = "Enhance Redmine effortlessly manage task lists within issues, track progress, ensure completion, and boost productivity.";
            Assert.assertEquals(pluginsPage.getChecklistPluginDescription(), expectedDescription, " Description Mismatch!");

            logger.info("Checklist Plugin description is correct.");
        } catch (Exception e) {
            logger.error("Description Verification Failed: " + e.getMessage());
            Assert.fail("Plugin description verification failed.");
        } finally {
            logger.info("=== Checklist Plugin Description Verification Completed ===");
        }
    }

    @Test(priority = 3, groups = {"Master", "Regression"}, dependsOnMethods = "verifyChecklistInstalled")
    public void verifyKnowledgeBaseLinkNavigation() {
        logger.info("===== Verify Knowledge Base Link Navigation =====");
        pluginsPage = new PluginsPage(driver);
        try {
            verifyNewTabURL(() -> pluginsPage.clickKnowledgeBaseLink(),
                    "https://www.redmineflux.com/knowledge-base/plugins/checklist-plugin/",
                    "Knowledge Base page URL mismatch!");

            logger.info("✔ Successfully navigated to the Knowledge Base page.");
        } catch (Exception e) {
            logger.error("Knowledge Base Link Navigation Failed: " + e.getMessage());
            Assert.fail("Knowledge Base navigation test failed.");
        } finally {
            logger.info("=== Knowledge Base Link Navigation Test Completed ===");
        }
    }

    @Test(priority = 4, groups = {"Master", "Regression"}, dependsOnMethods = "verifyChecklistInstalled")
    public void verifyAuthorWebsite() {
        logger.info("===== Verify Checklist Author Website Navigation =====");
        pluginsPage = new PluginsPage(driver);
        try {
            verifyNewTabURL(() -> pluginsPage.clickAuthorLink(),
                    "https://www.redmineflux.com/",
                    "Checklist Author's page URL mismatch!");

            logger.info("✔ Successfully navigated to the Checklist Author's page.");
        } catch (Exception e) {
            logger.error("Checklist Author Website Link Navigation Failed: " + e.getMessage());
            Assert.fail("Author website navigation test failed.");
        } finally {
            logger.info("=== Checklist Author Website Link Navigation Test Completed ===");
        }
    }

    /**
     * ===================== Helper Methods =====================
     **/

    private void navigateToPluginsPage() {
        logger.info("Navigating to Administration → Plugins");
        homePage.clickONAdministrator();
        administrationPage.clickOnPlugins();
    }

    private void verifyNewTabURL(Runnable clickAction, String expectedURL, String errorMessage) throws Exception {
        String originalTab = driver.getWindowHandle();
        clickAction.run(); // Clicks the given link
        switchToNewTab(originalTab);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("about:blank")));
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL, errorMessage);
        Thread.sleep(2000);
        driver.close();
        driver.switchTo().window(originalTab);
    }

    private void switchToNewTab(String originalTab) {

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
                return;
            }
        }
    }
}

