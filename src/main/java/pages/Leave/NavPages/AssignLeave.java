package pages.Leave.NavPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.Leave.Components.TopBarMenu;
import pages.Leave.LeavePage;

public class AssignLeave extends BasePage {

    private TopBarMenu topBarMenu;
    private LeavePage leavePage;
    private SideBar sideBar;

    private By inputFromDate = By.xpath("//label[normalize-space()='From Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By inputToDate = By.xpath("//label[normalize-space()='To Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By leaveTypeDropdown = By.xpath("//label[normalize-space()='Leave Type']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class, 'oxd-select-text-input')]");
    private By assignButton = By.xpath("//button[@type='submit']");
    private By commentBox = By.xpath("//textarea[contains(@class, 'oxd-textarea')]");
    private By employeeName = By.xpath("//input[contains(@placeholder, 'Type for hints...')]");
    private By leavebalance = By.xpath("//div[contains(@class, 'oxd-input-group')]/ancestor::div//p[contains(@class, 'orangehrm-leave-balance-text')]");
    private By partialDays = By.xpath("//label[normalize-space()='Partial Days']/ancestor::div[contains(@class, 'oxd-input-group')]//div[contains(@class, 'oxd-select-text-input')]");
    private By duration = By.xpath("//label[normalize-space()='Duration']/ancestor::div[contains(@class, 'oxd-input-group')]//div[contains(@class, 'oxd-select-text-input')]");
    private By confirmButton = By.xpath("//button[normalize-space()='Ok']");
    private By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    private By overlappingTitle = By.xpath("//h6[normalize-space()='Overlapping Leave Request(s) Found']");
    //private By recordsFound = By.xpath("//span[contains(@class, 'oxd-text--span')][text()=' (5) Records Found']");
    private By overlappingRecords = By.xpath("//div[contains(@class, 'oxd-table-card')]");

    public AssignLeave(WebDriver driver) {
        super(driver);
    }

    private WebElement getElements(By name) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(name)
        );
        return driver.findElement(name);
    }

    private By dropdown(String option) {
        return By.xpath(
                "//div[contains(@class,'oxd-select-option')]" +
                        "[normalize-space()='" + option + "']"
        );
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

    public void selectDropDownLeaveType(String type) {

        getElements(leaveTypeDropdown).click();

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        dropdown(type)
                )
        );

        option.click();
    }

    private By employeeNameOption(String employeeName) {
        return By.xpath(
                "//div[@role='listbox']//div[@role='option']" +
                        "[.//span[contains(normalize-space(), '" + employeeName + "')]]"
        );
    }

    public void setEmployeeName(String eName) {
        getElements(employeeName).sendKeys(eName);

        WebElement name = wait.until(
                ExpectedConditions.elementToBeClickable(
                        employeeNameOption(eName)
                )
        );

        name.click();
    }


    public void enterComments(String comments) {
        getElements(commentBox).sendKeys(comments);
    }

    private boolean isInsufficientBalanceDialogDisplayed() {
        try {
            return wait.until(d -> !d.findElements(confirmButton).isEmpty());
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickSubmit() {
        getElements(assignButton).click();
    }

    public void clickConfirm() {
        getElements(confirmButton).click();
    }

    public void clickCancel() {
        getElements(cancelButton).click();
    }

    public void selectPartialDays(String days) {
        getElements(partialDays).click();

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        dropdown(days)
                )
        );

        option.click();
    }

    public void selectDuration(String d) {
        getElements(duration).click();

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        dropdown(d)
                )
        );

        option.click();
    }

    public boolean isOverlapping() {
        try {
            return wait.until(d -> !d.findElements(overlappingTitle).isEmpty());
        } catch (TimeoutException e) {
            return false;
        }
    }

    private By requiredFieldError(String fieldLabel) {
        return By.xpath(
                "//label[normalize-space()='" + fieldLabel + "']/ancestor::div[contains(@class,'oxd-input-group')]" +
                        "//span[contains(@class,'oxd-input-group__message')]"
        );
    }

    public boolean isRequiredFieldErrorDisplayed(String fieldLabel) {
        return !driver.findElements(requiredFieldError(fieldLabel)).isEmpty();
    }

    public String getFieldErrorText(String fieldLabel) {
        return getElements(requiredFieldError(fieldLabel)).getText();
    }

    public void typeEmployeeNameWithoutSelecting(String text) {
        getElements(employeeName).sendKeys(text);
    }


    public boolean assignLeave(String leaveType, String FromDate, String ToDate, String eName, String comments) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("oxd-layout-context")));
        setEmployeeName(eName);
        selectDropDownLeaveType(leaveType);
        enterFromDate(FromDate);
        enterToDate(ToDate);
        enterComments(comments);
        clickSubmit();

        if (isInsufficientBalanceDialogDisplayed()) {
            clickConfirm();
        }

        if (isOverlapping()) {
            return false;
        }
        return true;
    }
}
