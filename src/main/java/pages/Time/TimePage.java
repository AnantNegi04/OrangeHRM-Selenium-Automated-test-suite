package pages.Time;

import org.openqa.selenium.WebDriver;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.Time.Components.TopBarMenu;
import pages.Time.NavPages.*;
import pages.Time.NavPages.Timesheets.EmployeeTimesheets;
import pages.Time.NavPages.Timesheets.MyTimesheets;

public class TimePage extends BasePage {

    private SideBar sideBar;
    private TopBarMenu topBarMenu = new TopBarMenu(driver);

    public TimePage(WebDriver driver) {
        super(driver);
    }

    public MyTimesheets goToMyTimeSheets() {
        topBarMenu.getDropdown("Timesheets", "My Timesheets").click();
        return new MyTimesheets(driver);
    }

    public EmployeeTimesheets goToEmployeeTimeSheets() {
        topBarMenu.getDropdown("Timesheets", "Employee Timesheets").click();
        return new EmployeeTimesheets(driver);
    }

    public MyRecords goToMyRecords() {
        topBarMenu.getDropdown("Attendance", "My Records").click();
        return new MyRecords(driver);
    }

    public Punch goToPunch() {
        topBarMenu.getDropdown("Attendance", "Punch In/Out").click();
        return new Punch(driver);
    }

    public EmployeeRecords goToEmployeeRecords() {
        topBarMenu.getDropdown("Attendance", "Employee Records").click();
        return new EmployeeRecords(driver);
    }

    public Configuration goToConfiguration() {
        topBarMenu.getDropdown("Attendance", "Configuration").click();
        return new Configuration(driver);
    }

    public ProjectReports goToProjectReports() {
        topBarMenu.getDropdown("Reports", "Project Reports").click();
        return new ProjectReports(driver);
    }

    public EmployeeReports goToEmployeeReports() {
        topBarMenu.getDropdown("Reports", "Employee Reports").click();
        return new EmployeeReports(driver);
    }

    public AttendanceSummary goToAttendanceSummary() {
        topBarMenu.getDropdown("Reports", "Attendance Summary").click();
        return new AttendanceSummary(driver);
    }

    public Customers goToCustomers() {
        topBarMenu.getDropdown("Project Info", "Customers").click();
        return new Customers(driver);
    }

    public Projects goToProjects() {
        topBarMenu.getDropdown("Project Info", "Projects").click();
        return new Projects(driver);
    }
}
