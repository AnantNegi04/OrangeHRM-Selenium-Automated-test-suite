package pages.Dashboard.Components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;
import pages.Leave.NavPages.ApplyLeave;
import pages.Leave.NavPages.AssignLeave;
import pages.Leave.NavPages.LeaveList;
import pages.Leave.NavPages.MyLeave;
import pages.Time.NavPages.Timesheets.EmployeeTimesheets;
import pages.Time.NavPages.Timesheets.MyTimesheets;

public class QuickLaunch extends BasePage {

    private By assignLeave = By.xpath("//button[@title='Assign Leave']");
    private By leaveList = By.xpath("//button[@title='Leave List']");
    private By timeSheets = By.xpath("//button[@title='Timesheets']");
    private By applyLeave = By.xpath("//button[@title='Apply Leave']");
    private By myLeave =  By.xpath("//button[@title='My Leave']");
    private By myTimeSheet = By.xpath("//button[@title='My Timesheet']");

    public QuickLaunch(WebDriver driver) {
        super(driver);
    }

    private WebElement getElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    private By getLocator(String locator) {
        return By.xpath(
                "//button[@title='" + locator + "']"
        );
    }

    public void navigateQuickLaunch(String locator) {
        By navLocator = getLocator(locator);
        getElement(navLocator).click();

        wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("oxd-layout-container"))
        );
    }

    public AssignLeave getAssignLeave() {
        getElement(assignLeave).click();
        return new AssignLeave(driver);
    }

    public LeaveList getLeaveList() {
        getElement(leaveList).click();
        return new LeaveList(driver);
    }

    public MyLeave getMyLeave() {
        getElement(myLeave).click();
        return new MyLeave(driver);
    }

    public MyTimesheets getMyTimesheets() {
        getElement(myTimeSheet).click();
        return new MyTimesheets(driver);
    }

    public ApplyLeave getApplyLeave() {
        getElement(applyLeave).click();
        return new ApplyLeave(driver);
    }

    public EmployeeTimesheets getEmployeeTimesheets() {
        getElement(timeSheets).click();
        return new EmployeeTimesheets(driver);
    }
}
