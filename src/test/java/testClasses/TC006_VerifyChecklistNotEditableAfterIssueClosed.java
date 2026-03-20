package testClasses;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import testBase.BaseClass;

public class TC006_VerifyChecklistNotEditableAfterIssueClosed extends BaseClass {

    //create issue add checklist and subChecklist and close issue (checked)
    //Issue is closed, you cannot perform this action
    //Issue is closed, you cannot perform this action
    //Issue is closed, you cannot perform this action
    //dropdown is enabled

    String parentChecklist1, parentChecklist2;
    String subChecklist1Parent1, subChecklist2Parent1, subChecklist3Parent1, subChecklist1Parent2;

    @Test(priority = 1, groups = {"Sanity", "Regression", "Master"})
    public void checkElementsOfChecklistAfterClosingIssue() {
        logger.info("===== TC005: Verify After closing issue checklist is not modified -Start Execution =====");
        HomePage homePage = new HomePage(driver);
        ProjectListPage projectsPage = new ProjectListPage(driver);
        ProjectOverviewPage projectOverviewPage = new ProjectOverviewPage(driver);
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);

        try {
            logger.info("Step 1: Login with user who have permission edit issue ");
            login(properties.getProperty("user2"), properties.getProperty("user_pwd"));

            logger.info("Step 2: Go to issue list of a project");
            homePage.clickOnProjects();
            projectsPage.clickToOpenProject(properties.getProperty("project_name"));
            projectOverviewPage.clickIssuesLnk();

            logger.info("Step 2: Create a issue");
            String id = createIssue("After closing checklist is not editable");

            logger.info("Step 3: Create 2 Parent checklist");
            parentChecklist1 = randomChecklist(10);
            addParentChecklist(parentChecklist1);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist1));

            parentChecklist2 = randomChecklist(10);
            addParentChecklist(parentChecklist2);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist2));

            logger.info("Step 4: Create SubChecklists in parent");

            subChecklist1Parent1 = randomChecklist(5);
            addSubChecklist(parentChecklist1, subChecklist1Parent1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subChecklist1Parent1));

            subChecklist2Parent1 = randomChecklist(5);
            addSubChecklist(parentChecklist1, subChecklist2Parent1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subChecklist2Parent1));

            subChecklist3Parent1 = randomChecklist(5);
            addSubChecklist(parentChecklist1, subChecklist3Parent1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subChecklist3Parent1));


            logger.info("Step 5: create sub checklist in 2nd parent checklist");

            subChecklist1Parent2 = randomChecklist(5);
            addSubChecklist(parentChecklist2, subChecklist1Parent2);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist2,subChecklist1Parent2));

            logger.info("step 6: check the parents checkbox");
            Thread.sleep(2000);
            issueDetailPage.checkCheckBoxParentChecklist(parentChecklist1);
            Thread.sleep(2000);
            issueDetailPage.checkCheckBoxParentChecklist(parentChecklist2);

            logger.info("Step 11: close issue after completing checklist");
            Assert.assertTrue(closeIssue(id));

            logger.info("Step 12: Verifing checkbox threedots are not enabled");

            issueDetailPage.clickOnExpand(parentChecklist1);
            issueDetailPage.clickOnExpand(parentChecklist2);

            Assert.assertEquals(issueDetailPage.getTooltipThreeDotChecklist(), "Issue is closed, you cannot perform this action");
            Assert.assertEquals(issueDetailPage.getTooltipOfParentChecklistThreeDot(parentChecklist1), "Issue is closed, you cannot perform this action");
            Assert.assertEquals(issueDetailPage.getTooltipOfParentChecklistThreeDot(parentChecklist2), "Issue is closed, you cannot perform this action");
            Assert.assertEquals(issueDetailPage.getTootipThreeDotSubChecklist(parentChecklist1, subChecklist1Parent1), "Issue is closed, you cannot perform this action");
            Assert.assertEquals(issueDetailPage.getTootipThreeDotSubChecklist(parentChecklist1, subChecklist2Parent1), "Issue is closed, you cannot perform this action");
            Assert.assertEquals(issueDetailPage.getTootipThreeDotSubChecklist(parentChecklist1, subChecklist3Parent1), "Issue is closed, you cannot perform this action");
            Assert.assertEquals(issueDetailPage.getTootipThreeDotSubChecklist(parentChecklist2, subChecklist1Parent2), "Issue is closed, you cannot perform this action");

            Assert.assertFalse(issueDetailPage.isParentChecklistCheckboxEnabled(parentChecklist1));
            Assert.assertFalse(issueDetailPage.isParentChecklistCheckboxEnabled(parentChecklist2));
            Assert.assertFalse(issueDetailPage.isSubChecklistCheckboxEnabled(parentChecklist1, subChecklist1Parent1));
            Assert.assertFalse(issueDetailPage.isSubChecklistCheckboxEnabled(parentChecklist1, subChecklist2Parent1));
            Assert.assertFalse(issueDetailPage.isSubChecklistCheckboxEnabled(parentChecklist1, subChecklist3Parent1));
            Assert.assertFalse(issueDetailPage.isSubChecklistCheckboxEnabled(parentChecklist2, subChecklist1Parent2));

            logger.info("Test passed: Checklist is not editable after closing issue");

        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }
        finally {
            logger.info("===== TC005: Verify After closing issue checklist is not modified - Execution Completed =====");
        }
    }


}
