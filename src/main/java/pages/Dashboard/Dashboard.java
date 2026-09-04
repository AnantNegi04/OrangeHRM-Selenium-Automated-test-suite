package pages.Dashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.BaseComponents.TopBar;
import pages.Leave.LeavePage;
import pages.Maintenance.MaintenancePage;
import pages.Time.TimePage;

public class Dashboard extends BasePage {
    private SideBar sideBar;
    private TopBar topBar;

    public Dashboard(WebDriver driver) {
        super(driver);
    }

    public LeavePage Nav() {
        SideBar sideBar = new SideBar(driver);
        return sideBar.goToLeavePage();
    }

    public TimePage NavTime() {
        SideBar sideBar = new SideBar(driver);
        return sideBar.goToTimePage();
    }

    public void logOut() {
        topBar = new TopBar(driver);
        topBar.getDropDownItem("Logout");
    }

    public boolean isDisplayed() {
        sideBar = new SideBar(driver);
        return !sideBar.searchHasNoResults("Admin");
    }

    public boolean maintenancePage() {
        sideBar = new SideBar(driver);
        MaintenancePage page = sideBar.goToMaintenancePage();
        return page.isPasswordConfirmationDisplayed();
    }
}
