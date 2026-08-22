package pages.Leave;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.Leave.Components.TopBarMenu;
import pages.Leave.NavPages.ApplyLeave;
import pages.Leave.NavPages.AssignLeave;
import pages.Leave.NavPages.Configure.Holidays;
import pages.Leave.NavPages.Configure.LeavePeriod;
import pages.Leave.NavPages.Configure.LeaveType;
import pages.Leave.NavPages.Configure.WorkWeek;
import pages.Leave.NavPages.Entitlements.AddEntitlements;
import pages.Leave.NavPages.Entitlements.EmployeeEntitlements;
import pages.Leave.NavPages.Entitlements.MyEntitlements;
import pages.Leave.NavPages.LeaveList;
import pages.Leave.NavPages.MyLeave;
import pages.Leave.NavPages.Reports.LeaveEntitlementAndUsageReport;
import pages.Leave.NavPages.Reports.MyLeaveEntitlementAndUsageReports;

public class LeavePage extends BasePage {

    private SideBar sideBar;
    private TopBarMenu topBarMenu =  new TopBarMenu(driver);

    public LeavePage(WebDriver driver) {
        super(driver);
    }

    public ApplyLeave goToApplyLeave() {
        topBarMenu.getTopBarMenu("Apply").click();
        return new ApplyLeave(driver);
    }

    public LeaveList goToLeaveList() {
        topBarMenu.getTopBarMenu("Leave List").click();
        return new LeaveList(driver);
    }

    public AssignLeave goToAssignLeave() {
        topBarMenu.getTopBarMenu("Assign Leave").click();
        return new AssignLeave(driver);
    }

    public MyLeave goToMyLeave() {
        topBarMenu.getTopBarMenu("My Leave").click();
        return new MyLeave(driver);
    }

    public Holidays goToHolidays() {
        topBarMenu.getDropdown("Configure", "Holidays").click();
        return new Holidays(driver);
    }

    public LeavePeriod goToLeavePeriod() {
        topBarMenu.getDropdown("Configure", "Leave Period").click();
        return new LeavePeriod(driver);
    }

    public LeaveType goToLeaveType() {
        topBarMenu.getDropdown("Configure", "Leave Type").click();
        return new LeaveType(driver);
    }

    public WorkWeek goToWorkWeek() {
        topBarMenu.getDropdown("Configure", "Work Week").click();
        return new WorkWeek(driver);
    }

    public AddEntitlements goToAddEntitlements() {
        topBarMenu.getDropdown("Entitlements", "Add Entitlements").click();
        return new AddEntitlements(driver);
    }

    public EmployeeEntitlements goToEmployeeEntitlements() {
        topBarMenu.getDropdown("Entitlements", "Employee Entitlements").click();
        return new EmployeeEntitlements(driver);
    }

    public MyEntitlements goToMyEntitlements() {
        topBarMenu.getDropdown("Entitlements", "My Entitlements").click();
        return new MyEntitlements(driver);
    }

    public LeaveEntitlementAndUsageReport goToLeaveEntitlementAndUsageReport() {
        topBarMenu.getDropdown("Reports", "leave Entitlements and Usage Report").click();
        return new LeaveEntitlementAndUsageReport(driver);
    }

    public MyLeaveEntitlementAndUsageReports goToMyLeaveEntitlementAndUsageReports() {
        topBarMenu.getDropdown("Reports", "My Leave Entitlements and Usage Report").click();
        return new MyLeaveEntitlementAndUsageReports(driver);
    }
}
