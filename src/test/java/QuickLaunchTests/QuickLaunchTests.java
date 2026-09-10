package QuickLaunchTests;

import baseTest.BaseTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BaseComponents.SideBar;
import pages.BaseComponents.TopBar;
import pages.Dashboard.Components.QuickLaunch;
import pages.Dashboard.Dashboard;

import static org.testng.Assert.assertTrue;

public class QuickLaunchTests extends BaseTests {
    private SideBar sideBar;
    private TopBar topBar;
    private Dashboard dashboard;

    @BeforeMethod
    public void loginAndInitSideBar() {
        dashboard = loginPage.login("Admin", "admin123");
        sideBar = new SideBar(driver);
    }

    @AfterMethod
    public void logoutAfterEachTest() {
        TopBar topBar = new TopBar(driver);
        if (!topBar.isDropDownVisible()) {
            // Landed somewhere without the tool's layout — the Maintenance
            // password gate, or the external brand site. Session is still
            // valid (we never logged out), so returning to the app recovers it.
            driver.get(BASE_URL);
            topBar = new TopBar(driver);
        }
        topBar.getDropDownItem("Logout");
    }

    @DataProvider(name = "quickLaunchItems")
    public Object[][] quickLaunchItems() {
        return new Object[][]{
                {"Assign Leave", "/assignLeave"},
                {"Leave List", "/viewLeaveList"},
                {"Timesheets", "/viewEmployeeTimesheet"},
                {"Apply Leave", "/applyLeave"},
                {"My Leave", "/viewMyLeaveList"},
                {"My Timesheet", "/viewMyTimesheet"}
        };
    }
    @Test(dataProvider = "quickLaunchItems")
    public void quickLaunchNavigatesToCorrectModule(String item, String expectedFragment) {
        QuickLaunch quickLaunch = dashboard.quickLaunch();
        quickLaunch.navigateQuickLaunch(item);
        assertTrue(driver.getCurrentUrl().contains(expectedFragment));

    }

    @Test(description = "Sequentially navigating through every Quick Launch item within a single session succeeds")
    public void sequentialQuickLaunchNavigationWithinSameSession() {
        SideBar sideBar = new SideBar(driver);
        QuickLaunch quickLaunch = new Dashboard(driver).quickLaunch();

        quickLaunch.navigateQuickLaunch("Assign Leave");
        assertTrue(driver.getCurrentUrl().contains("/assignLeave"));
        sideBar.navigateToMenuItem("Dashboard");
        quickLaunch = new Dashboard(driver).quickLaunch();

        quickLaunch.navigateQuickLaunch("Leave List");
        assertTrue(driver.getCurrentUrl().contains("/viewLeaveList"));
        sideBar.navigateToMenuItem("Dashboard");
        quickLaunch = new Dashboard(driver).quickLaunch();

        quickLaunch.navigateQuickLaunch("Timesheets");
        assertTrue(driver.getCurrentUrl().contains("/viewEmployeeTimesheet"));
        sideBar.navigateToMenuItem("Dashboard");
        quickLaunch = new Dashboard(driver).quickLaunch();

        quickLaunch.navigateQuickLaunch("Apply Leave");
        assertTrue(driver.getCurrentUrl().contains("/applyLeave"));
        sideBar.navigateToMenuItem("Dashboard");
        quickLaunch = new Dashboard(driver).quickLaunch();

        quickLaunch.navigateQuickLaunch("My Leave");
        assertTrue(driver.getCurrentUrl().contains("/viewMyLeaveList"));
        sideBar.navigateToMenuItem("Dashboard");
        quickLaunch = new Dashboard(driver).quickLaunch();

        quickLaunch.navigateQuickLaunch("My Timesheet");
        assertTrue(driver.getCurrentUrl().contains("/viewMyTimesheet"));
    }
}
