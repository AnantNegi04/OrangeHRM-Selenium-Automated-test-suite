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

import java.util.List;

public class MyLeave extends BasePage {

    private TopBarMenu topBarMenu;
    private LeavePage leavePage;
    private SideBar sideBar;

    private By inputFromDate = By.xpath("//label[normalize-space()='From Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By inputToDate = By.xpath("//label[normalize-space()='To Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By leaveStatusDropdown = By.xpath("//label[normalize-space()='Show Leave with Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class, 'oxd-select-text-input')]");
    private By leaveTypeDropdown = By.xpath("//label[normalize-space()='Leave Type']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class, 'oxd-select-text-input')]");
    private By searchButton = By.xpath("//button[contains(@class, 'orangehrm-left-space')]");
    private By clearMultipleSelect = By.xpath("//i[contains(@class, '--clear')]");
    private By dropDownElements = By.xpath("//div[contains(@class, 'oxd-select-option')]");
    private By recordsFound = By.xpath("//div[contains(@class, 'orangehrm-header-container')]/ancestor::div[contains(@class, 'orangehrm-paper-container')]//span[contains(@class, 'oxd-text--span')]");

    public MyLeave(WebDriver driver) {
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

    public void search(String fromDate, String toDate, String leaveStatus, String leaveType) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("oxd-layout-context")));
        enterFromDate(fromDate);
        enterToDate(toDate);
        selectDropDownLeaveStatus(leaveStatus);
        selectDropDownLeaveType(leaveType);
        getElements(searchButton).click();
        System.out.println(driver.findElement(recordsFound).getText());
    }
}
