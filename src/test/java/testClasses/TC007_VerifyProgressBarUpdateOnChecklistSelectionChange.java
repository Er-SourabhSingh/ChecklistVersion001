package testClasses;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import testBase.BaseClass;

public class TC007_VerifyProgressBarUpdateOnChecklistSelectionChange extends BaseClass {

    String issueId;
    String parentChecklist1, parentChecklist2;
    String subChecklist1Parent1, subChecklist2Parent1, subChecklist3Parent1, subChecklist1Parent2, subChecklist2Parent2;

    @Test(priority = 1, groups = {"Master", "Regression"})
    public void checkOnCompletingParentChecklist() {
        logger.info("===== TC007: Verify After Selecting input checkbox of parent checklist progress bar 100% completed and subchecklist also completed=====");
        HomePage homePage = new HomePage(driver);
        ProjectListPage projectsPage = new ProjectListPage(driver);
        ProjectOverviewPage projectOverviewPage = new ProjectOverviewPage(driver);
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);

        try {
            logger.info("Step 1: Login with user who have permission edit issue ");
            login(properties.getProperty("user2"), properties.getProperty("user_pwd"));

            logger.info("Step 2: Go to issue list of project");
            homePage.clickOnProjects();
            projectsPage.clickToOpenProject(properties.getProperty("project_name"));
            projectOverviewPage.clickIssuesLnk();

            logger.info("Step 3: Enter Subject in issue form and clickToOpenProject on create button");
            issueId = createIssue("Test - Subject progress bar increases or decreases on cheking checklists");

            logger.info("Step 4: Create 2 Parent checklist");
            parentChecklist1 = randomChecklist(10);
            addParentChecklist(parentChecklist1);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist1));
            parentChecklist2 = randomChecklist(10);
            addParentChecklist(parentChecklist2);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist2));
            logger.info("Parent checklist are created");

            logger.info("Step 5: Create SubChecklists in parent");
            subChecklist1Parent1 = randomChecklist(5);
            addSubChecklist(parentChecklist1, subChecklist1Parent1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subChecklist1Parent1));

            subChecklist2Parent1 = randomChecklist(6);
            addSubChecklist(parentChecklist1, subChecklist2Parent1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subChecklist2Parent1));

            subChecklist3Parent1 = randomChecklist(13);
            addSubChecklist(parentChecklist1, subChecklist3Parent1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subChecklist3Parent1));


            logger.info("Step 6: create sub checklist in 2nd parent checklist");

            subChecklist1Parent2 = randomChecklist(5);
            addSubChecklist(parentChecklist2, subChecklist1Parent2);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist2,subChecklist1Parent2));
            subChecklist2Parent2 = randomChecklist(8);
            addSubChecklist(parentChecklist2, subChecklist2Parent2);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist2,subChecklist2Parent2));
            logger.info("step 6: check the parents checkbox");

            issueDetailPage.checkCheckBoxParentChecklist(parentChecklist1);




            logger.info("step 8: check progress bar of" + parentChecklist1 + " and sub checklist checkbox check");
            Thread.sleep(3000);
            Assert.assertTrue(issueDetailPage.isParentChecklistCheckboxSelected(parentChecklist1));
            Assert.assertTrue(issueDetailPage.isSubChecklistCheckboxSelected(parentChecklist1, subChecklist1Parent1));
            Assert.assertTrue(issueDetailPage.isSubChecklistCheckboxSelected(parentChecklist1, subChecklist2Parent1));
            //Assert.assertTrue(issueDetailPage.isSubChecklistCheckboxSelected(parentChecklist1, subChecklist3Parent1));
            logger.info("Test pass: on checking parentchecklist and all subchecklist check boxes are checked");

            logger.info("Step 9: Verify progress bar width 100% of first parent checklist");
            Assert.assertTrue(issueDetailPage.isProgressBarCompleted(parentChecklist1));
            logger.info("Test pass: parent Checklist bar is 100%");

            logger.info("Step 10: Verify all sub checklist dropdown in done stage");
            Assert.assertEquals(issueDetailPage.getSelectedSubChecklistOption(parentChecklist1, subChecklist1Parent1), "Done");
            Assert.assertEquals(issueDetailPage.getSelectedSubChecklistOption(parentChecklist1, subChecklist2Parent1), "Done");
            //Assert.assertEquals(issueDetailPage.getSelectedSubChecklistOption(parentChecklist1, subChecklist3Parent1), "Done");
            logger.info("Test pass: Done is selected in All sub checlist dropdowns of parentchecklist");

        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }
    }


    @Test(priority = 2, groups = {"Master", "Regression"} ,dependsOnMethods = "checkOnCompletingParentChecklist")
    public void verifyCheckSubChecklist() {
        logger.info("Verify Checking Subchecklist one by one then progress bar increases");
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        try {
            logger.info("Step 1: check 1st subchecklist and progress bar should increase 50%");
            issueDetailPage.checkCheckboxOfSubChecklist(parentChecklist2,subChecklist1Parent2);
            Thread.sleep(2000);
            Assert.assertEquals(issueDetailPage.getProgressBarWidth(parentChecklist2),"50%");
            Assert.assertTrue(issueDetailPage.isSubChecklistCheckboxSelected(parentChecklist2,subChecklist1Parent2));
            Assert.assertEquals(issueDetailPage.getSelectedSubChecklistOption(parentChecklist2,subChecklist1Parent2),"Done");
            logger.info("Test pass- on checking one checklist out of two subcehcklist progess bar increase 50%");

            logger.info("Step 2: After checking 2nd subchecklist progress bar should increase 100%");
            issueDetailPage.checkCheckboxOfSubChecklist(parentChecklist2,subChecklist2Parent2);
            Thread.sleep(2000);
            Assert.assertEquals(issueDetailPage.getProgressBarWidth(parentChecklist2),"100%");
            Assert.assertTrue(issueDetailPage.isSubChecklistCheckboxSelected(parentChecklist2,subChecklist2Parent2));
            Assert.assertEquals(issueDetailPage.getSelectedSubChecklistOption(parentChecklist2,subChecklist2Parent2),"Done");
            logger.info("Test pass- on checking last sub checklist progess bar increase 100%");

        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }
    }
}
