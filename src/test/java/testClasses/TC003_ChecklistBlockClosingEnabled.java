package testClasses;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import testBase.BaseClass;

public class TC003_ChecklistBlockClosingEnabled extends BaseClass {

    String issueId;
    String parentChecklist1;

    @Test(priority = 1, groups = {"Sanity", "Regression", "Master"})
    public void configureChecklistSettings_EnableBlockIssueClosing() {
        logger.info("===== TC003: Verify Issue cannot close if any checklist item is unchecked when Block Issue is enabled - Start Execution =====");

        // Initialize Page Objects
        HomePage homePage = new HomePage(driver);
        AdministrationPage administrationPage = new AdministrationPage(driver);
        PluginsPage pluginsPage = new PluginsPage(driver);
        ChecklistConfigurationPage checklistConfigurationPage = new ChecklistConfigurationPage(driver);
        try {
            // Step 1: Login with admin
            logger.info("Step 1: Login as admin");
            login(properties.getProperty("user1"), properties.getProperty("user1_pwd"));

            logger.info("Step 2: Navigate to Checklist plugin → Configuration");
            homePage.clickONAdministrator();
            administrationPage.clickOnPlugins();
            pluginsPage.scrollToChecklistPlugin();
            pluginsPage.openChecklistConfiguration();


            logger.info("Step 3: Enable Block Issue Closing and Click on apply");
            checklistConfigurationPage.enabledBlockedIssueClose();
            checklistConfigurationPage.clickOnApply();
            Assert.assertTrue(checklistConfigurationPage.isSelectedBlockedIssueClosing());
            Assert.assertEquals(checklistConfigurationPage.isSuccessMessageDisplayed(), "Successful update.");
        } catch (Exception e) {
            logger.error("Enable 'Block Issue Closing' failed: " + e.getMessage());
            Assert.fail();
        } finally {
            logger.info("=== Configure Checklist Plugin - Enabled 'Block Issue Closing' - Method Executed ===");
        }
    }

    @Test(priority = 2, groups = {"Regression", "Master"}, dependsOnMethods = "configureChecklistSettings_EnableBlockIssueClosing")
    public void verifyIssueCanCloseIfNoChecklistCreated() {
        logger.info("=== Verify issue should close if no checklist is Created ===");

        // Initialize Objects
        ChecklistConfigurationPage checklistConfigurationPage = new ChecklistConfigurationPage(driver);
        ProjectListPage projectsPage = new ProjectListPage(driver);
        ProjectOverviewPage projectOverviewPage = new ProjectOverviewPage(driver);
        IssueListPage issueListPage = new IssueListPage(driver);
        CreateIssueFromPage createIssueFromPage = new CreateIssueFromPage(driver);
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        EditIssueFormPage editIssueFormPage = new EditIssueFormPage(driver);
        try {
            // Step 1: Navigate to issue list
            logger.info("Step 4: Navigate to the issue list of a project");
            checklistConfigurationPage.clickOnProjectsLink();
            projectsPage.clickToOpenProject(properties.getProperty("project_name"));
            projectOverviewPage.clickIssuesLnk();


            // Step 3: Enter issue subject and create the issue
            logger.info("Step 6: Enter Subject in issue form");
            String id = createIssue("Test - Block Issue Closing without checklist");


            // Step 4: Attempt to close the issue
            logger.info("Step 4: Attempt to close the issue");
            Assert.assertTrue(closeIssue(id));
            logger.info("Test Passed: Issue can be closed if NO checklist is created.");
        } catch (Exception e) {
            logger.error("Issue closing if no checklist is created test failed: " + e.getMessage());
            Assert.fail();
        } finally {
            logger.info("=== Verify issue should close if any checklist is created - Execution completed ===");
        }
    }

    @Test(priority = 3, groups = {"Regression", "Master"}, dependsOnMethods = "verifyIssueCanCloseIfNoChecklistCreated")
    public void verifyIssueCannotCloseIfChecklistUnchecked() {
        logger.info("=== Verify issue should not close if any checklist item is unchecked ===");

        IssueListPage issueListPage = new IssueListPage(driver);
        CreateIssueFromPage createIssueFromPage = new CreateIssueFromPage(driver);
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        EditIssueFormPage editIssueFormPage = new EditIssueFormPage(driver);
        try {

            logger.info("Step 5: Navigate to the issue list page");
            issueDetailPage.clickLinkIssues();

            logger.info("Step 6: Create issue");
            issueId = createIssue("Test - Block Issue Closing when no checklist closed");

            parentChecklist1 = randomChecklist(10);
            logger.info("Step 7: Add checklist item: " + parentChecklist1);
            addParentChecklist(parentChecklist1);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist1));
            logger.info("Expected:parent checklist created");

            logger.info("Step 8: Verify that checklist is not checked");
            Assert.assertFalse(issueDetailPage.isParentChecklistCheckboxSelected(parentChecklist1), "Checklist item should not be checked");
            logger.info("Expected: Parent Checklist is not completed");

            logger.info("Step 9: Attempt to close the issue");
            Assert.assertFalse(closeIssue(issueId));
            logger.info("Test Passed: Issue cannot be closed if any checklist item is unchecked.");
        } catch (Exception e) {
            logger.error("Issue closing test failed: " + e.getMessage());
            Assert.fail();
        } finally {
            logger.info("=== Verify issue should not close if any checklist item is unchecked - Execution completed ===");
        }
    }

    @Test(priority = 4, groups = {"Master", "Regression"}, dependsOnMethods = "verifyIssueCannotCloseIfChecklistUnchecked")
    public void verifyIssueCanCloseIfChecklistChecked() {

        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        EditIssueFormPage editIssueFormPage = new EditIssueFormPage(driver);
        logger.info("=== Verify issue should close if checklists is checked - Execution Started");
        try {
            logger.info("Step 10: navigate back on issue detail page");
            driver.navigate().back();

            logger.info("Step 11: check parentchecklist checklist");
            issueDetailPage.checkCheckBoxParentChecklist(parentChecklist1);
            Assert.assertTrue(issueDetailPage.isParentChecklistCheckboxSelected(parentChecklist1));

            logger.info("Step 12: Close the issue after completing checklist");
            Assert.assertTrue(closeIssue(issueId));

            logger.info("the issue is successfully closed");


            logger.info("✅ Test Passed: Issue is automatically closed after checking all checklist items.");
        } catch (Exception e) {
            logger.error("the issue closes after completing the checklist - Test fail: " + e.getMessage());
            Assert.fail();
        } finally {
            logger.info("=== Verify issue should close if checklists is checked - Execution Started");
        }
    }


}
