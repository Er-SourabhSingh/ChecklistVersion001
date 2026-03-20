package testClasses;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import testBase.BaseClass;

public class TC005_VerifyWithoutEditPermissionChecklistNotCreated extends BaseClass {
    @Test(priority = 1, groups = {"Sanity", "Regression", "Master"})
    public void createChecklistAndSubChecklistWithoutPermission() {
        logger.info("===== TC006: Verify user without Edit Issue Permission can not Create checklist - Start Execution =====");
        HomePage homePage = new HomePage(driver);
        ProjectListPage projectsPage = new ProjectListPage(driver);
        ProjectOverviewPage projectOverviewPage = new ProjectOverviewPage(driver);
        IssueListPage issueListPage = new IssueListPage(driver);
        CreateIssueFromPage createIssueFromPage = new CreateIssueFromPage(driver);
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);

        try {
            logger.info("Step 1: Login with user who do not have permission edit issue ");
            login(properties.getProperty("user3"), properties.getProperty("user_pwd"));

            logger.info("Step 2: go to issue list page of project");
            homePage.clickOnProjects();
            projectsPage.clickToOpenProject(properties.getProperty("project_name"));
            projectOverviewPage.clickIssuesLnk();


            logger.info("Step 3: Create issue");
            String id = createIssue("User without issue edit permission");

            logger.info("Step 4: Check Action or three dot button is not clickable");
            Assert.assertEquals(issueDetailPage.getTooltipThreeDotChecklist(), "You don't have permission for edit issue and checklist.");

        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();

        } finally {
            logger.info("TC006: Verify user without Edit Issue Permission can not Create checklist-- Completed");
        }

    }
}
