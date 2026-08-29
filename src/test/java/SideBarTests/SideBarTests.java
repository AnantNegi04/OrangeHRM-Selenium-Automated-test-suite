package SideBarTests;

import baseTest.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.BaseComponents.TopBar;
import pages.Maintenance.MaintenancePage;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class SideBarTests extends BaseTests {
    private SideBar sideBar;
    private TopBar topBar;

    @BeforeMethod
    public void loginAndInitSideBar() {
        loginPage.login("Admin", "admin123");
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

    @DataProvider(name = "sidebarModules")
    public Object[][] sidebarModules() {
        return new Object[][] {
                {"Admin", "/admin/"},
                {"PIM", "/pim/viewEmployeeList"},
                {"My Info", "/pim/viewPersonalDetails"},
                {"Leave", "/leave/viewLeaveList"},
                {"Time", "/time/viewEmployeeTimesheet"},
                {"Recruitment", "/recruitment/viewCandidates"},
                {"Performance", "/performance/searchEvaluatePerformanceReview"},
                {"Dashboard", "/dashboard/index"},
                {"Directory", "/directory/viewDirectory"},
                {"Claim", "/claim/viewAssignClaim"},
                {"Buzz", "/buzz/viewBuzz"}
        };
    }

    @Test(dataProvider = "sidebarModules",
            description = "Sidebar navigation lands on the correct module route")
    public void sidebarNavigatesToCorrectModule(String menuItem, String expectedRouteFragment) {
        sideBar.navigateToMenuItem(menuItem);
        assertTrue(
                driver.getCurrentUrl().contains(expectedRouteFragment),
                "Expected URL to contain '" + expectedRouteFragment + "' but was: " + driver.getCurrentUrl()
        );
    }

    @Test(description = "Sidebar navigation to Maintenance lands on the password confirmation screen, not the internal page")
    public void sidebarNavigatesToMaintenance() {
        MaintenancePage maintenancePage = sideBar.goToMaintenancePage();
        assertTrue(
                maintenancePage.isPasswordConfirmationDisplayed(),
                "Expected the Maintenance password confirmation screen to be displayed"
        );
    }

    @Test(description = "Searching a valid menu item and clicking the result navigates correctly")
    public void searchWithValidMatchNavigatesCorrectly() {
        sideBar.searchAndNavigate("Admin");
        assertTrue(driver.getCurrentUrl().contains("/admin/"));
    }

    @Test(description = "Searching text matching no menu item resolves to an empty result list, not a timeout")
    public void searchWithNoResultsHandledGracefully() {
        assertTrue(sideBar.searchHasNoResults("zzzNonExistentModuleXYZ"));
    }

    @Test(description = "Search still functions correctly when the sidebar starts collapsed")
    public void searchFunctionsCorrectlyFromCollapsedSidebar() {
        sideBar.collapseSideBar();
        sideBar.searchAndNavigate("Admin");
        assertTrue(driver.getCurrentUrl().contains("/admin/"));
    }

    @Test(description = "Brand logo navigates to the brand's website, away from the tool")
    public void brandLogoNavigatesToBrandPage() {
        sideBar.goToBrandPage();
        assertTrue(
                driver.getCurrentUrl().contains("orangehrm.com"),
                "Expected to have navigated to the brand website"
        );
    }
}
