package testClasses;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import testBase.BaseClass;

public class TC002_VerifyChecklistBlockClosingWhenDisabled extends BaseClass {

    @Test(priority = 1, groups = {"Sanity", "Regression", "Master"})
    public void configureChecklistSettings_DisableBlockIssueClosing() {
        HomePage homePage = new HomePage(driver);
        AdministrationPage administrationPage = new AdministrationPage(driver);
        PluginsPage pluginsPage = new PluginsPage(driver);
        ChecklistConfigurationPage checklistConfigurationPage = new ChecklistConfigurationPage(driver);
        logger.info("=== TC002: Verify Issue will close with or without checklist when BlockIssue is disable - Start Execution ===");
        try {
            logger.info("Step 1: Login as admin");
            login(properties.getProperty("user1"), properties.getProperty("user1_pwd"));

            logger.info("Step 2: Navigate to Checklist plugin Configuration");
            homePage.clickONAdministrator();
            administrationPage.clickOnPlugins();
            pluginsPage.scrollToChecklistPlugin();
            pluginsPage.openChecklistConfiguration();

            logger.info("Step 3: Disable Block Issue Closing and Click on apply");
            checklistConfigurationPage.disableBlockedIssueClose();
            checklistConfigurationPage.clickOnApply();
            Assert.assertFalse(checklistConfigurationPage.isSelectedBlockedIssueClosing());
            Assert.assertEquals(checklistConfigurationPage.isSuccessMessageDisplayed(), "Successful update.");
            logger.info("Block issue closing box is unchecked");
        } catch (Exception e) {
            logger.error("Disable 'Block issue failed': " + e.getMessage());
            Assert.fail();
        } finally {
            logger.info("=== Configure Checklist Plugin - Disabled ' block issue closing'- Method Executed ===");
        }
    }

    @Test(priority = 2, groups = {"Regression", "Master"}, dependsOnMethods = "configureChecklistSettings_DisableBlockIssueClosing")
    public void createAndCloseIssueWithoutChecklist() {
        ChecklistConfigurationPage checklistConfigurationPage = new ChecklistConfigurationPage(driver);
        ProjectListPage projectsPage = new ProjectListPage(driver);
        ProjectOverviewPage projectOverviewPage = new ProjectOverviewPage(driver);

        logger.info("Verify Issue should be closed without Any checklist");
        try {
            logger.info("Step 4: navigate to issue list page of a project");
            checklistConfigurationPage.clickOnProjectsLink();
            projectsPage.clickToOpenProject(properties.getProperty("project_name"));
            projectOverviewPage.clickIssuesLnk();

            logger.info("Step 5: Create new issue");
            String id = createIssue("Created issue no checklist added");

            logger.info("Step 6: Select Closed status from dropdown and submit form");
            Assert.assertTrue(closeIssue(id));
            logger.info("Test pass - Issue is successfully closed!");

        } catch (Exception e) {
            logger.error("Issue closing without any checklist test fail: " + e.getMessage());
            Assert.fail();
        } finally {
            logger.info("=== Verify Issue should be closed without Any checklist - execution is completed ===");
        }
    }

    @Test(priority = 3, groups = {"Regression", "Master"}, dependsOnMethods = "configureChecklistSettings_DisableBlockIssueClosing")
    public void createIssueWithChecklistAndClose_UncheckedChecklist() {
        logger.info("=== Verify without checking checklists or sub Checklist issue should be closed ===");
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);

        try {

            logger.info("Step 7: Go to issue list from issue detail page");
            issueDetailPage.clickLinkIssues();

            logger.info("Step 8: Create new issue");
            String id = createIssue("Issue with Checklist");

            logger.info("Step 9: Create Parent Checklist");
            String parentChecklist = randomChecklist(10);
            addParentChecklist(parentChecklist);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist));
            logger.info(parentChecklist + ": is created");

            logger.info("Step 10: Enter subchecklist name");
            addSubChecklist(parentChecklist,"Test");
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist,"Test"));

            logger.info("Step 11: Select Closed status from dropdown and submit form");
            Assert.assertTrue(closeIssue(id));
            logger.info("Test pass Issue is successfully closed!");
        } catch (Exception e) {
            logger.error("Issue closing without checking checklist any checklist test fail: " + e.getMessage());
            Assert.fail();
        } finally {
            logger.info("=== Verify without checking checklists or sub Checklist issue should be closed - Execution is completed ===");
        }
    }

    @Test(priority = 4, groups = {"Regression", "Master"}, dependsOnMethods = "configureChecklistSettings_DisableBlockIssueClosing")
    public void verifyIssueClosesAfterCompletingChecklist() {
        logger.info("=== Verify that the issue closes after completing the checklist when block issue closing disabled ===");
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        try {
            logger.info("Step 11: Navigate to the issue list from the issue detail page");
            issueDetailPage.clickLinkIssues();

            logger.info("Step 12: Create a issue");
            String id = createIssue("Test - Create checklist and check checboex of checklsit and closed this issue");

            logger.info("Step 13: Create parent Checklists");
            String parentChecklist1 = randomChecklist(10);
            addParentChecklist(parentChecklist1);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist1));
            logger.info("Parent Checklist '" + parentChecklist1 + "' is created");

            String parentChecklist2 = randomChecklist(14);
            addParentChecklist(parentChecklist2);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist2));
            logger.info("Parent Checklist '" + parentChecklist2 + "' is created");

            Thread.sleep(3000);

            logger.info("Step 14: add subchecklists in 1st parentchecklist");
            String subchecklist1 = randomChecklist(5);
            addSubChecklist(parentChecklist1, subchecklist1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subchecklist1));
            logger.info("SubChecklist1 '" + subchecklist1 + "' is created");
            String subchecklist2 = randomChecklist(5);
            addSubChecklist(parentChecklist1, subchecklist2);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subchecklist2));
            logger.info("SubChecklist2 '" + subchecklist2 + "' is created");
            String subchecklist3 = randomChecklist(5);
            addSubChecklist(parentChecklist2, subchecklist3);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist2,subchecklist3));
            logger.info("SubChecklist3 '" + subchecklist3 + "' is created");

            Thread.sleep(3000);

            logger.info("check the checkboxes of parent checklists");
            issueDetailPage.checkCheckBoxParentChecklist(parentChecklist1);
            issueDetailPage.checkCheckBoxParentChecklist(parentChecklist2);
            logger.info("Check parent and its sub checlist checkboxes are checked");
            Assert.assertTrue(issueDetailPage.isParentChecklistCheckboxSelected(parentChecklist1));
            Assert.assertTrue(issueDetailPage.isParentChecklistCheckboxSelected(parentChecklist2));
            Assert.assertTrue(issueDetailPage.isSubChecklistCheckboxSelected(parentChecklist1, subchecklist1));
            logger.info("all checboxes in checklist section are checked");


            logger.info("Step 15: Verify that the issue is closed");
            Assert.assertTrue(closeIssue(id), "Issue should be closed after completing all checklist items");

            logger.info("Test Passed: Issue is closed after checking all checklist items.");


        } catch (Exception e) {
            logger.error("the issue closes after completing the checklist - Test fail: " + e.getMessage());
            Assert.fail();
        } finally {
            logger.info("=== Verifying issue closes after completing the checklist when block issue closing disabled");
            logger.info("===== TC002: Verify Issue will close with or without checklist when BlockIssue is disable - Completed Execution =====");
        }
    }


}
