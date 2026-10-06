package AssignLeaveTests;

import baseTest.BaseTests;
import baseTest.RetryAnalyzer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BaseComponents.SideBar;
import pages.BaseComponents.TopBar;
import pages.Leave.LeavePage;
import pages.Leave.NavPages.AssignLeave;
import pages.Leave.NavPages.LeaveList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class AssignLeaveTests extends BaseTests {
    private AssignLeave assignLeave;
    private TopBar topBar;

    private static final String[] EMPLOYEE_POOL = {
            "Thomas Kutty Benny",
            "Alice Brown",
            "Amelia Brown",
            "Ravi M B",
            "Rafael  Castillo",
            "Joan Carmona"
            // add 3–4 more real, valid employee names — check the actual autocomplete
            // list in the app rather than guessing; I don't have visibility into who
            // else exists in this dataset
    };

    private static String selectPooledEmployee() {
        int index = (int) ((System.currentTimeMillis() / 60000) % EMPLOYEE_POOL.length);
        return EMPLOYEE_POOL[index];
    }

    private static final String YEAR_LIMIT_BOUNDARY_FROM = "2027-12-25";
    private static final String YEAR_LIMIT_BOUNDARY_TO = "2027-12-31";

    private static final String BEYOND_YEAR_LIMIT_FROM = "2028-01-05";
    private static final String BEYOND_YEAR_LIMIT_TO = "2028-01-10";

    private static final String VALID_LEAVE_TYPE = "CAN - Personal";

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String from = LocalDate.now().plusDays(50).format(DATE_FORMAT);
    String to = LocalDate.now().plusDays(56).format(DATE_FORMAT);

    @BeforeMethod
    public void loginAndNavigateToAssignLeave() {
        loginPage.login("Admin", "admin123");
        SideBar sideBar = new SideBar(driver);
        LeavePage leavePage = sideBar.goToLeavePage();
        assignLeave = leavePage.goToAssignLeave();
        topBar = new TopBar(driver);
    }

    @AfterMethod
    public void logoutAfterEachTest() {
        topBar.logout();
    }

    @Test(description = "Employee Name left empty shows a required-field error")
    public void employeeNameEmptyShowsRequiredError() {
        assignLeave.selectDropDownLeaveType(VALID_LEAVE_TYPE);
        assignLeave.clickSubmit();
        assertTrue(assignLeave.isRequiredFieldErrorDisplayed("Employee Name"));
    }

    @Test(description = "Leave Type left unselected shows a required-field error")
    public void leaveTypeEmptyShowsRequiredError() {
        assignLeave.setEmployeeName(selectPooledEmployee());
        assignLeave.clickSubmit();
        assertTrue(assignLeave.isRequiredFieldErrorDisplayed("Leave Type"));
    }

    @Test(description = "Both Employee Name and Leave Type left empty shows both required errors")
    public void bothMandatoryDropdownsEmptyShowsBothErrors() {
        assignLeave.clickSubmit();
        assertTrue(assignLeave.isRequiredFieldErrorDisplayed("Employee Name"));
        assertTrue(assignLeave.isRequiredFieldErrorDisplayed("Leave Type"));
    }

    @Test(description = "Employee Name typed but not selected from autocomplete shows an Invalid error, distinct from Required")
    public void employeeNameTypedButNotSelectedShowsInvalidError() {
        assignLeave.selectDropDownLeaveType(VALID_LEAVE_TYPE);
        assignLeave.typeEmployeeNameWithoutSelecting("John");
        assignLeave.clickSubmit();

        String errorText = assignLeave.getFieldErrorText("Employee Name");
        assertEquals(errorText, "Invalid",
                "Expected a distinct 'Invalid' style message, not the generic Required error");
    }

    @Test( retryAnalyzer = RetryAnalyzer.class,
            description = "A valid, multi-day leave request is accepted")
    public void validAssignmentSucceeds() {
        String employeeName = selectPooledEmployee();
        boolean result = assignLeave.assignLeave(VALID_LEAVE_TYPE, from, to, employeeName, "Leave");
        assertTrue(result, "A valid employee, date range, and leave type should result in a successful assignment");

        LeaveList leaveList = new SideBar(driver).goToLeavePage().goToLeaveList();
        leaveList.search(from, to, "Scheduled", VALID_LEAVE_TYPE, employeeName, "");
        assertTrue(leaveList.cancelAllFoundRecords(), "Cleanup should succeed");
    }

    @Test( retryAnalyzer = RetryAnalyzer.class,
            description = "From Date equal to To Date (single-day boundary) is accepted")
    public void fromDateEqualsToDateIsAccepted() {
        String employeeName = selectPooledEmployee();
        boolean result = assignLeave.assignLeave(VALID_LEAVE_TYPE, from, from, employeeName, "Leave");
        assertTrue(result, "A single-day leave request should be accepted");

        LeaveList leaveList = new SideBar(driver).goToLeavePage().goToLeaveList();
        leaveList.search(from, from, "Scheduled", VALID_LEAVE_TYPE, employeeName, "");
        assertTrue(leaveList.cancelAllFoundRecords(), "Cleanup should succeed");
    }

    @Test(description = "To Date before From Date is rejected")
    public void toDateBeforeFromDateIsRejected() {
        // Deliberately swapped: the later date passed as From, the earlier as To.
        boolean result = assignLeave.assignLeave(VALID_LEAVE_TYPE, to, from, selectPooledEmployee(), "Leave");
        assertFalse(result, "Assignment should not succeed when To Date precedes From Date");
    }

    @Test(description = "Non-date character string in From Date is rejected gracefully")
    public void characterStringInFromDateIsRejected() {
        boolean result = assignLeave.assignLeave(VALID_LEAVE_TYPE, "notADate", to, selectPooledEmployee(), "Leave");
        assertFalse(result, "A non-date string should not result in a successful assignment");
    }

    @Test( retryAnalyzer = RetryAnalyzer.class,
            description = "A date range ending exactly at the application's year limit boundary is accepted")
    public void dateRangeAtYearLimitBoundaryIsAccepted() {
        String employeeName = selectPooledEmployee();
        boolean result = assignLeave.assignLeave(VALID_LEAVE_TYPE, YEAR_LIMIT_BOUNDARY_FROM, YEAR_LIMIT_BOUNDARY_TO, employeeName, "Leave");
        assertTrue(result, "A date range ending on the last valid day should be accepted");

        LeaveList leaveList = new SideBar(driver).goToLeavePage().goToLeaveList();
        leaveList.search(YEAR_LIMIT_BOUNDARY_FROM, YEAR_LIMIT_BOUNDARY_TO, "Scheduled", VALID_LEAVE_TYPE, employeeName, "");
        assertTrue(leaveList.cancelAllFoundRecords(), "Cleanup should succeed");
    }

    @Test(description = "A date range beyond the application's year limit is rejected")
    public void dateRangeBeyondYearLimitIsRejected() {
        boolean result = assignLeave.assignLeave(VALID_LEAVE_TYPE, BEYOND_YEAR_LIMIT_FROM, BEYOND_YEAR_LIMIT_TO, selectPooledEmployee(), "Leave");
        assertFalse(result, "A date range crossing into the disallowed year should be rejected");
    }

    @Test(description = "SQL-injection-style string in From Date is treated as ordinary invalid input")
    public void injectionStyleInputInFromDateRejectedNormally() {
        boolean result = assignLeave.assignLeave(VALID_LEAVE_TYPE, "' OR '1'='1", to, selectPooledEmployee(), "Leave");
        assertFalse(result, "Injection-style input should be rejected the same as any other invalid date");
    }

    @Test(description = "Comments left empty does not block a valid submission")
    public void commentsOptionalDoesNotBlockSubmission() {
        String employeeName = selectPooledEmployee();
        boolean result = assignLeave.assignLeave(VALID_LEAVE_TYPE, from, to, employeeName, "");
        assertTrue(result, "Leaving the optional comments field empty should not block assignment");

        LeaveList leaveList = new SideBar(driver).goToLeavePage().goToLeaveList();
        leaveList.search(from, to, "Scheduled", VALID_LEAVE_TYPE, employeeName, "");
        assertTrue(leaveList.cancelAllFoundRecords(), "Cleanup should succeed");
    }

    @Test( retryAnalyzer = RetryAnalyzer.class,
            description = "Submitting the same employee, type, and date range twice in one session triggers the overlap warning")
    public void duplicateAssignmentTriggersOverlapWarning() {
        String employeeName = selectPooledEmployee();

        boolean first = assignLeave.assignLeave(VALID_LEAVE_TYPE, from, to, employeeName, "Leave");
        assertTrue(first, "First assignment should succeed");

        LeavePage leavePage = new SideBar(driver).goToLeavePage();
        assignLeave = leavePage.goToAssignLeave();

        boolean second = assignLeave.assignLeave(VALID_LEAVE_TYPE, from, to, employeeName, "Leave");
        assertFalse(second);
        assertTrue(assignLeave.isOverlapping(), "Second, identical assignment should be rejected as overlapping");

        LeaveList leaveList = new SideBar(driver).goToLeavePage().goToLeaveList();
        leaveList.search(from, to, "Scheduled", VALID_LEAVE_TYPE, employeeName, "");
        assertTrue(leaveList.cancelAllFoundRecords(), "Cleanup should succeed");
    }
}
