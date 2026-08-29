package SideBarTests;

import baseTest.BaseTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BaseComponents.SideBar;
import pages.BaseComponents.TopBar;

import static org.testng.AssertJUnit.assertTrue;

public class AppNavigationTests extends BaseTests {
    private SideBar sideBar;

    @BeforeMethod
    public void loginAndInitSideBar() {
        loginPage.login("Admin", "admin123");
        sideBar = new SideBar(driver);
    }

    @AfterMethod
    public void logoutAfterEachTest() {
        TopBar topBar = new TopBar(driver);
        if (!topBar.isDropDownVisible()) {
            driver.get(BASE_URL);
            topBar = new TopBar(driver);
        }
        topBar.getDropDownItem("Logout");
    }

    @Test(description = "Sequentially navigating across every module within a single session succeeds without degradation")
    public void sequentialNavigationAcrossAllModules() {
        sideBar.navigateToMenuItem("Admin");
        assertTrue(driver.getCurrentUrl().contains("/admin/"));

        sideBar.navigateToMenuItem("PIM");
        assertTrue(driver.getCurrentUrl().contains("/pim/viewEmployeeList"));

        sideBar.navigateToMenuItem("Leave");
        assertTrue(driver.getCurrentUrl().contains("/leave/viewLeaveList"));

        sideBar.navigateToMenuItem("Time");
        assertTrue(driver.getCurrentUrl().contains("/time/viewEmployeeTimesheet"));

        sideBar.navigateToMenuItem("Recruitment");
        assertTrue(driver.getCurrentUrl().contains("/recruitment/viewCandidates"));

        sideBar.navigateToMenuItem("My Info");
        assertTrue(driver.getCurrentUrl().contains("/pim/viewPersonalDetails"));

        sideBar.navigateToMenuItem("Performance");
        assertTrue(driver.getCurrentUrl().contains("/performance/searchEvaluatePerformanceReview"));

        sideBar.navigateToMenuItem("Dashboard");
        assertTrue(driver.getCurrentUrl().contains("/dashboard/index"));

        sideBar.navigateToMenuItem("Directory");
        assertTrue(driver.getCurrentUrl().contains("/directory/viewDirectory"));

        sideBar.navigateToMenuItem("Claim");
        assertTrue(driver.getCurrentUrl().contains("/claim/viewAssignClaim"));

        sideBar.navigateToMenuItem("Buzz");
        assertTrue(driver.getCurrentUrl().contains("/buzz/viewBuzz"));
    }
}
