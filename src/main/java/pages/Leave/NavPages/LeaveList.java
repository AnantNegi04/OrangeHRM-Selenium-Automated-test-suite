package pages.Leave.NavPages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.Leave.Components.TopBarMenu;
import pages.Leave.LeavePage;

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

    public LeaveList(WebDriver driver) {
        super(driver);
    }

    private WebElement getElements(By name) {
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

    public void setEmployeeName() {
        getElements(employeeName).sendKeys("A");

        WebElement firstName = wait.until(
                ExpectedConditions.elementToBeClickable(autoComplete)
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

    public void clickResetButton() {
        getElements(reset).click();
    }

    public void search(String fromDate, String toDate, String leaveStatus, String leaveType, String subUnit) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("oxd-layout-context")));
        enterFromDate(fromDate);
        enterToDate(toDate);
        selectDropDownLeaveStatus(leaveStatus);
        selectDropDownLeaveType(leaveType);
        setEmployeeName();
        enterSubunit(subUnit);
        getElements(search).click();
    }

}