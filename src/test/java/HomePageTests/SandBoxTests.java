package HomePageTests;

import baseTest.BaseTests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.Dashboard.Dashboard;
import pages.Leave.LeavePage;
import pages.Leave.NavPages.*;
import pages.Time.NavPages.*;
import pages.Time.TimePage;

import static org.testng.Assert.assertEquals;

public class SandBoxTests extends BaseTests {
    @Test
    public void sandBoxTests() {
        Dashboard dashboard = loginPage.login("Admin", "admin123");
        MyTimesheets page = dashboard.NavTime().goToMyTimeSheets();
        page.editTimesheet();
        page.fillRows(0, "Apache Software Foundation - ASF - Phase 1","Bug Fixes", "OK");
    }
}
