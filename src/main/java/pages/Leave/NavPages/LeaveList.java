package pages.Leave.NavPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.Leave.Components.TopBarMenu;
import pages.Leave.LeavePage;

import java.util.List;

public class LeaveList extends BasePage {

    private TopBarMenu topBarMenu;
    private LeavePage leavePage;
    private SideBar sideBar;

    private By inputFromDate = By.xpath("//label[normalize-space()='From Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By inputToDate = By.xpath("//label[normalize-space()='To Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By leaveStatusDropdown = By.xpath("//label[normalize-space()='Show Leave with Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class, 'oxd-select-text-input')]");
    private By leaveTypeDropdown = By.xpath("//label[normalize-space()='Leave Type']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class, 'oxd-select-text-input')]");
    private By clearMultipleSelect = By.xpath("//i[contains(@class, '--clear')]");
    private By employeeName = By.xpath("//input[contains(@placeholder, 'Type for hints...')]");
    private By autoComplete = By.cssSelector("div[role='listbox'][class*='oxd-autocomplete-dropdown']");
    private By subUnit = By.xpath("//label[normalize-space()='Sub Unit']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class, 'oxd-select-text-input')]");
    private By toggleButton = By.xpath("//input[@type ='checkbox']");
    private By search = By.xpath("//button[@type='submit']");
    private By reset =  By.xpath("//button[@type='reset']");
    private By records = By.xpath( "//div[contains(@class, 'orangehrm-header-container')]//span[contains(@class, 'oxd-text oxd-text--span')]");
    private By toastOutcome = By.xpath("//div[contains(@class,'oxd-toast')][contains(@class,'oxd-toast--')]");
    private By toastTitle = By.xpath("//p[contains(@class,'oxd-text--toast-title')]");
    private By selectAllCheckbox = By.xpath("//div[contains(@class, 'oxd-table-header')]//i[contains(@class, 'oxd-checkbox-input-icon')]");
    private By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    private By confirmCancelButton = By.xpath("//button[normalize-space()='Yes, Confirm']");
    private By declineCancelButton = By.xpath("//button[normalize-space()='No, Cancel']");

    public LeaveList(WebDriver driver) {
        super(driver);
    }

    private WebElement getElements(By name) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(name)
        );
        return driver.findElement(name);
    }

    private void clearMultipleSelect() {
        while (!driver.findElements(clearMultipleSelect).isEmpty()) {
            WebElement element =  driver.findElements(clearMultipleSelect).getFirst();

            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        }
    }

    private void enterFromDate(String date) {
        WebElement element = getElements(inputFromDate);

        element.click();
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(date);
        element.sendKeys(Keys.TAB);
    }

    private void enterToDate(String date) {
        WebElement element = getElements(inputToDate);

        element.click();
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(date);
    }

    private By getDropDownLeaveStatusElements(String status) {
        return By.xpath(
                "//div[contains(@class, 'oxd-select-option')]" +
                        "[normalize-space()='" + status + "']"
        );
    }

    private void selectDropDownLeaveStatus(String status) {
        clearMultipleSelect();

        getElements(leaveStatusDropdown).click();

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        getDropDownLeaveStatusElements(status)
                )
        );

        option.click();
    }

    private By leaveTypeOption(String type) {
        return By.xpath(
                "//div[contains(@class,'oxd-select-option')]" +
                        "[normalize-space()='" + type + "']"
        );
    }

    public void selectDropDownLeaveType(String type) {

        getElements(leaveTypeDropdown).click();

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        leaveTypeOption(type)
                )
        );

        option.click();
    }

    private By getEmployeeNameDropdown(String employeeName) {
        return By.xpath(
                "//div[@role='listbox']//div[@role='option']" +
                        "[.//span[contains(normalize-space(), '" + employeeName + "')]]"
        );
    }

    public void setEmployeeName(String name) {
        getElements(employeeName).sendKeys(name);

        WebElement firstName = wait.until(
                ExpectedConditions.elementToBeClickable(getEmployeeNameDropdown(name))
        );

        firstName.click();
    }

    private By getSubUnitOption(String subUnit) {
        return By.xpath(
                "//div[contains(@class,'oxd-select-option')]" +
                        "[normalize-space()='" + subUnit + "']"
        );
    }

    public void enterSubunit(String option) {
        getElements(subUnit).click();

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                getSubUnitOption(option))
        );

        element.click();
    }

    public void clickToggleButton() {
        getElements(toggleButton).click();
    }

    public String viewRecords() {
        try {
            return getElements(records).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public void clickResetButton() {
        getElements(reset).click();
    }

    public void selectAllRecords() {
        wait.until(ExpectedConditions.presenceOfElementLocated(selectAllCheckbox)).click();
    }

    public void clickCancel() {
        getElements(cancelButton).click();
    }

    public void confirmCancel() {
        getElements(confirmCancelButton).click();
    }

    public void declineCancel() {
        getElements(declineCancelButton).click();
    }

    private String getToastTitle() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(toastTitle)).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public boolean isCancelSuccessful() {
        List<WebElement> titles = driver.findElements(toastTitle);
        for (WebElement title : titles) {
            if (title.getText().equalsIgnoreCase("Success")) {
                return true;
            }
        }
        return false;
    }

    public boolean cancelAllFoundRecords() {
        selectAllRecords();

        boolean cancelAvailable;
        try {
            cancelAvailable = wait.until(d -> !d.findElements(cancelButton).isEmpty());
        } catch (TimeoutException e) {
            cancelAvailable = false;
        }

        if (!cancelAvailable) {
            return false;
        }

        clickCancel();
        confirmCancel();
        return isCancelSuccessful();
    }

    // NOTE: "Cancel button never appears" covers two distinct real-world cases —
    // zero matching records, or records that exist but are already partially
    // consumed and therefore locked from cancellation. This test suite only ever
    // creates fresh, fully-future leave (never yet started), so the second case
    // should never actually arise here — but a caller reusing this method against
    // arbitrary, pre-existing data should not assume `false` means "nothing found."

    public void search(String fromDate, String toDate, String leaveStatus, String leaveType, String name, String subUnit) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("oxd-layout-context")));

        String oldRecord = driver.findElements(records).isEmpty() ? null : driver.findElement(records).getText();

        enterFromDate(fromDate);
        enterToDate(toDate);
        selectDropDownLeaveStatus(leaveStatus);
        selectDropDownLeaveType(leaveType);
        setEmployeeName(name);
        getElements(search).click();

        if (oldRecord != null && oldRecord.equalsIgnoreCase("No Records Found")) {
            try{
                wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(records, "No Records Found")));
            } catch (TimeoutException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}