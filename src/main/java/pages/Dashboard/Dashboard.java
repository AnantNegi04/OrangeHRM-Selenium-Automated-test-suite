package pages.Dashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.BaseComponents.TopBar;
import pages.Dashboard.Components.QuickLaunch;
import pages.Leave.LeavePage;
import pages.Maintenance.MaintenancePage;
import pages.Time.TimePage;

public class Dashboard extends BasePage {
    private SideBar sideBar;
    private TopBar topBar;

    public Dashboard(WebDriver driver) {
        super(driver);
    }

    public void logOut() {
        topBar = new TopBar(driver);
        topBar.getDropDownItem("Logout");
    }

    public QuickLaunch quickLaunch() {
        return new QuickLaunch(driver);
    }
}
