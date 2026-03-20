package testClasses;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import testBase.BaseClass;

public class TC004_CURDOperationWithEditIssuePermission extends BaseClass {
    String id_issue;
    String parentChecklist1, parentChecklist2;
    String subChecklist1Parent1, subChecklist2Parent1, subChecklist3Parent1, subChecklist1Parent2;
    String updatedChecklist;

    @Test(priority = 1, groups = {"Sanity", "Regression", "Master"})
    public void createChecklistAndSubChecklist() {
        logger.info("===== TC004: Verify user with Edit Issue Permission can perform curd operation -Start Execution =====");
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

            logger.info("Step 3: Create Issue");
            id_issue = createIssue("Curd operation for checklist with edit issue permission");

            logger.info("Step 4: Create two Parent checklists");
            parentChecklist1 = "Test";
            addParentChecklist(parentChecklist1);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist1));
            logger.info("Parent Checklist '" + parentChecklist1 + "' is created");
            parentChecklist2 = randomChecklist(10);
            addParentChecklist(parentChecklist2);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(parentChecklist2));
            logger.info("Parent Checklist '" + parentChecklist2 + "' is created");


            logger.info("Step 5: Create SubChecklists in parent");
            subChecklist1Parent1 = "Test";
            addSubChecklist(parentChecklist1, subChecklist1Parent1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subChecklist1Parent1));
            logger.info("subchecklist 1 created");
            subChecklist2Parent1 = randomChecklist(5);
            addSubChecklist(parentChecklist1, subChecklist2Parent1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,subChecklist2Parent1));
            logger.info("subchecklist 2 created");
            subChecklist3Parent1 = randomChecklist(6);
            addSubChecklist(parentChecklist1, subChecklist3Parent1);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1, subChecklist3Parent1));
            logger.info("subchecklist 3 created");


            subChecklist1Parent2 = randomChecklist(5);
            addSubChecklist(parentChecklist2, subChecklist1Parent2);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist2,subChecklist1Parent2));
            logger.info("subchecklist 1 created in 2nd parentchecklist");

        } catch (Exception e) {
            logger.debug(e.getMessage());
            Assert.fail();

        }
    }

    @Test(priority = 2, groups = {"Regression", "Master"}, dependsOnMethods = "createChecklistAndSubChecklist")
    public void verifyDuplicateParentChecklistAndSubChecklistNotCreated() {
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        logger.info("Verify The duplicate parent checklist and subchecklist should not be created on same issue");
        try {
            logger.info("Create same name parent checklist");
            addParentChecklist("Test");
            System.out.println(issueDetailPage.getErrorMessage());

            Assert.assertEquals(issueDetailPage.getErrorMessage(),"Checklist title must be unique within the issue");
            logger.info("Same Checklist is not created");

            logger.info("Create same name Subchecklist checklist");

            addSubChecklist("Test", "Test");
            Thread.sleep(3000);
            Assert.assertEquals(issueDetailPage.getErrorMessage(),"Checklist item must be unique within the same checklist");
            logger.info("Same  Sub Checklist is not created in same parentChecklist");

        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 3, groups = {"Regression", "Master"}, dependsOnMethods = "createChecklistAndSubChecklist")
    public void updateChecklistAndSubChecklist() {
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        try {
            logger.info("Step 9: updated created 1st parent checklist");
            updatedChecklist = "updated checklist";
            updateParentChecklist(parentChecklist2, updatedChecklist);
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated(updatedChecklist));
            logger.info("1st parent Checklist is updated");

            logger.info("update 1st subChecklist name");
            String upSub = "updated sub";
            updateSubChecklist(parentChecklist1, subChecklist1Parent1, upSub);
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated(parentChecklist1,upSub));
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }
    }


    @Test(priority = 4, groups = {"Regression", "Master"}, dependsOnMethods = "createChecklistAndSubChecklist")
    public void DeleteChecklistAndSubChecklist() {
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);
        logger.info("Verify Checklist should be delete checklist and subchecklist");
        try {
            logger.info("Delete sub checklist from 1st parentChecklist");
            deleteSubChecklist("updated checklist", subChecklist1Parent2);
            Thread.sleep(3000);
            Assert.assertFalse(issueDetailPage.isParentChecklistCheckboxSelected("updated checklist"));
            Thread.sleep(3000);
            Assert.assertEquals(issueDetailPage.getSuccessMessage(),"Checklist item deleted successfully.");
            logger.info("SubChecklist Checklist Deleted Successfully");

            logger.info("Delete 2nd parentChecklist");
            deleteParentChecklist(parentChecklist1);
            Thread.sleep(3000);
            Assert.assertEquals(issueDetailPage.getSuccessMessage(),"Checklist deleted successfully");
            logger.info("parent Checklist Deleted Successfully");


        } catch (Exception e) {
            logger.debug(e.getMessage());
            Assert.fail();
        } finally {
            logger.info("Verify user with Edit Issue Permission can perform curd operation");
            logger.info("===== TC004: Verify user with Edit Issue Permission can perform curd operation - Completed Execution =====");
        }

    }


}
