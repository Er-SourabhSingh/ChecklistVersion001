package testClasses;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import testBase.BaseClass;

public class TC008_VerifyChecklistTemplateCreationAndUsage extends BaseClass {
    @Test(priority = 1, groups = {"Sanity", "Regression", "Master"})
    public void openChecklistTemplateTab() {
        HomePage homePage = new HomePage(driver);
        AdministrationPage administrationPage = new AdministrationPage(driver);
        PluginsPage pluginsPage = new PluginsPage(driver);
        ChecklistConfigurationPage checklistConfigurationPage = new ChecklistConfigurationPage(driver);
        logger.info("=== TC008: Verify that Checklist template created for bug tracker and use on bug issue detail page - Start Execution ===");
        try {
            logger.info("Step 1: Login as admin");
            login(properties.getProperty("user1"), properties.getProperty("user1_pwd"));

            logger.info("Step 2: Navigate to Checklist plugin Configuration");
            homePage.clickONAdministrator();
            administrationPage.clickOnPlugins();
            pluginsPage.scrollToChecklistPlugin();
            pluginsPage.openChecklistConfiguration();

            logger.info("Step 3: Go to Checklist template tab");
            checklistConfigurationPage.openTemplateTab();

        } catch (Exception e) {
            logger.error("Open Checklist Template tab got failed': " + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 2, groups = {"Regression", "Master"},dependsOnMethods = "openChecklistTemplateTab")
    public void createChecklistTemplateForBugTracker(){

        ChecklistConfigurationPage checklistConfigurationPage = new ChecklistConfigurationPage(driver);

        ChecklistTemplateForm checklistForm = new ChecklistTemplateForm(driver);
        try{
            logger.info("Step 4: Click on add new Checklist btn");
            checklistConfigurationPage.clickAddNewChecklistBtn();

            // Fill out the form
            checklistForm.selectTracker("Bug");  // Select a tracker, like 'Bug' or 'Feature'
            checklistForm.setTxtTemplateName("Sample Checklist Template");

            // Add a parent checklist item
            checklistForm.setParentChecklistTxt(0, "Parent Checklist Item 1");

            // Add another parent checklist item
            checklistForm.clickAddBtnOfParentChecklist();
            checklistForm.setParentChecklistTxt(1, "Parent Checklist Item 2");

            // Add sub-checklist items
            checklistForm.clickAddSubChecklistBtn(1); // Click to add sub-checklist for the second parent item
            checklistForm.setSubChecklistTxt(0, 0, "Sub Checklist Item 1");

            checklistForm.clickAddSubChecklistBtn(2); // Click again to add another sub-checklist
            checklistForm.setSubChecklistTxt(1, 0, "Sub Checklist Item 2");

            checklistForm.clickAddSubChecklistBtn(2); // Click again to add another sub-checklist
            checklistForm.setSubChecklistTxt(1, 1, "Sub Checklist Item 3");
            // Submit the form
            checklistForm.clickOnCreateTemplateBtn();

            // Optionally, add a success message check or validation
            System.out.println("Checklist template created successfully!");

        }catch (Exception e){
            logger.error(e.getMessage());
            Assert.fail();
        }
    }
    @Test(priority = 3, groups = {"Regression", "Master"},dependsOnMethods = "createChecklistTemplateForBugTracker")
    public void addCheklistFromCreatedTemplateInBugIssue(){
        ChecklistConfigurationPage checklistConfigurationPage = new ChecklistConfigurationPage(driver);
        ProjectListPage projectsPage = new ProjectListPage(driver);
        ProjectOverviewPage projectOverviewPage = new ProjectOverviewPage(driver);
        IssueDetailPage issueDetailPage = new IssueDetailPage(driver);

        logger.info("Verify Checklist Creation from Template");
        try {
            logger.info("Step 5: navigate to issue list page of a project");
            checklistConfigurationPage.clickOnProjectsLink();
            projectsPage.clickToOpenProject(properties.getProperty("project_name"));
            projectOverviewPage.clickIssuesLnk();

            logger.info("Step 6: Create new issue");
            String id = createIssue("Created issue Add checklist from Template");

            logger.info("Step 7: Add Checklist from list");
            issueDetailPage.clickOnActionChecklist();
            issueDetailPage.clickOnAddFromTemplate();
            issueDetailPage.selectTemplateName("Sample Checklist Template");

            logger.info("Step 8: Verify Checklist Created or not from template");

            Assert.assertTrue(issueDetailPage.isParentChecklistCreated("Parent Checklist Item 1"));
            Assert.assertTrue(issueDetailPage.isParentChecklistCreated("Parent Checklist Item 2"));
            issueDetailPage.clickOnExpand("Parent Checklist Item 1");
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated("Parent Checklist Item 1","Sub Checklist Item 1"));
            issueDetailPage.clickOnExpand("Parent Checklist Item 2");
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated("Parent Checklist Item 2","Sub Checklist Item 2"));
            Assert.assertTrue(issueDetailPage.isSubChecklistCreated("Parent Checklist Item 2","Sub Checklist Item 3"));

            logger.info("Test pass - Checklist are add from template");

        } catch (Exception e) {
            logger.error("Cheklist add from template on issue detail page fail: " + e.getMessage());
            Assert.fail();
        } finally {
            logger.info("=== TC008: Verify that Checklist template created for bug tracker and use on bug issue detail page -Exection completed ===");
        }
    }





}
