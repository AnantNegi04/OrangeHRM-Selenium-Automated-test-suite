package pages.Time.NavPages.Timesheets;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.Time.Components.TopBarMenu;
import pages.Time.TimePage;

import java.util.List;
import java.util.Scanner;

public class MyTimesheets extends BasePage {

    private TopBarMenu topBarMenu;
    private SideBar sideBar;
    private TimePage timePage;
    private EditTimesheets editTimesheets;

    private By editButton = By.xpath("//button[contains(@class, 'oxd-button--ghost')]");
    private By submitButton = By.xpath("//button[contains(@class, 'oxd-button--secondary')]");
    private By prevButton = By.xpath("//i[contains(@class, 'bi-chevron-left')]/ancestor::button[contains(@class, '--prev')]");
    private By nextButton = By.xpath("//i[contains(@class, 'bi-chevron-right')]/ancestor::button[contains(@class, '--next')]");
    private By inputTimePeriod = By.xpath("//input[@placeholder='yyyy-mm-dd']");


    public MyTimesheets(WebDriver driver) {
        super(driver);
    }

    private WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public EditTimesheets clickEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        getElement(editButton).click();
        return new EditTimesheets(driver);
    }

    public void submitButton() {
        getElement(submitButton).click();
    }

    //Timesheet period format 2026-08-24 to 2026-08-30
    public void fillTimePeriod(String from, String to) {
        WebElement element = getElement(inputTimePeriod);
        element.sendKeys(from);
        element.sendKeys("to");
        element.sendKeys(to);
        element.sendKeys(Keys.TAB);
    }

    public void getTimesheetForm(String projectName, String option, String comment) {
        editTimesheets = clickEditButton();
        wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("oxd-layout-container"))
        );
        editTimesheets.fillRow1(projectName, option, comment);
        submitButton();
    }
}
