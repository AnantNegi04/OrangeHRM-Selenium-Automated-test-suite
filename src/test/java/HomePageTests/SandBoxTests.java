package HomePageTests;

import baseTest.BaseTests;
import org.testng.annotations.Test;
import pages.Dashboard.Dashboard;
import pages.Time.NavPages.Timesheets.MyTimesheets;

import static org.testng.Assert.assertEquals;

public class SandBoxTests extends BaseTests {
    @Test
    public void sandBoxTests() {
        Dashboard dashboard = loginPage.login("Admin", "admin123");
        MyTimesheets page = dashboard.NavTime().goToMyTimeSheets();
        page.getTimesheetForm("Apache Software Foundation - ASF - Phase 1", "Bug Fixes", "OK");
    }
}
