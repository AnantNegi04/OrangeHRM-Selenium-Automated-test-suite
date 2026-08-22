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

public class AssignLeave extends BasePage {

    private TopBarMenu topBarMenu;
    private LeavePage leavePage;
    private SideBar sideBar;

    private By inputFromDate = By.xpath("//label[normalize-space()='From Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By inputToDate = By.xpath("//label[normalize-space()='To Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By leaveTypeDropdown = By.xpath("//label[normalize-space()='Leave Type']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class, 'oxd-select-text-input')]");
    private By autoComplete = By.cssSelector("div[role='listbox'][class*='oxd-autocomplete-dropdown']");
    private By clearMultipleSelect = By.xpath("//i[contains(@class, '--clear')]");
    private By assignButton = By.xpath("//button[@type='submit']");
    private By commentBox = By.xpath("//textarea[contains(@class, 'oxd-textarea')]");
    private By employeeName = By.xpath("//input[contains(@placeholder, 'Type for hints...')]");
    private By leavebalance = By.xpath("//div[contains(@class, 'oxd-input-group')]/ancestor::div//p[contains(@class, 'orangehrm-leave-balance-text')]");

    public AssignLeave(WebDriver driver) {
        super(driver);
    }

    private WebElement getElements(By name) {
        return driver.findElement(name);
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

    public void enterComments(String comments) {
        getElements(commentBox).sendKeys(comments);
    }

    private boolean canAssign() {
        if (getElements(leavebalance).getText().equals("Balance not sufficient")) {
            return false;
        }
        return true;
    }

    public void assignLeave(String leaveType, String FromDate, String ToDate, String comments) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("oxd-layout-context")));
        setEmployeeName();
        selectDropDownLeaveType(leaveType);
        enterFromDate(FromDate);
        enterToDate(ToDate);
        if (!canAssign()) {
            System.out.println("cannot assign leave type due to insufficient balance");
            return;
        }
        enterComments(comments);
        getElements(assignButton).click();
    }
}
