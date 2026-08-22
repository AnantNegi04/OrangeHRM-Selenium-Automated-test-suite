package pages.Time.NavPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.Time.Components.TopBarMenu;
import pages.Time.TimePage;

import java.util.List;

public class MyTimesheets extends BasePage {

    private TopBarMenu topBarMenu;
    private SideBar sideBar;
    private TimePage timePage;

    private By editButton = By.xpath("//button[contains(@class, 'oxd-button--ghost')]");
    private By submitButton = By.xpath("//button[contains(@class, 'oxd-button--secondary')]");
    private By allRows = By.xpath("//tr[contains(@class, 'orangehrm-timesheet-table-body-row')]");
    private By deleteRow = By.cssSelector("i.bi-trash");
    private By addRow = By.cssSelector("i.bi-plus");

    public MyTimesheets(WebDriver driver) {
        super(driver);
    }

    private WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public void editTimesheet() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        getElement(editButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-layout-container")));
    }

    private Rows getRow(int index) {
        List<WebElement> rows = driver.findElements(allRows);

        return new Rows(driver, rows.get(index));
    }

    public void fillRows(int index, String project, String option, String comment) {
        wait.until(ExpectedConditions.elementToBeClickable(allRows));
        getRow(index).fillTimesheet(project, option, comment);
    }

    private class Rows {
        private WebDriver driver;
        private WebElement row;

        private By project = By.xpath(".//input[@placeholder='Type for hints...']");
        private By autocomplete = By.cssSelector("div[role='listbox'][class*='oxd-autocomplete-dropdown']");
        private By dropdown = By.xpath(".//div[contains(@class, 'oxd-select-option')]");
        private By activity = By.xpath(".//div[contains(@class, 'oxd-select-text-input')]");
        private By inputActivity = By.xpath(".//input[contains(@class, 'oxd-input--active')]");
        private By resetbutton = By.xpath("//button[normalize-space()='Reset']");
        private By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
        private By saveButton = By.xpath("//button[normalize-space()='Save']");
        private By commentButton = By.cssSelector("[class$='dots']");
        private By commentBox = By.cssSelector("textarea[placeholder='Comment here']");
        private By saveComment = By.xpath("//div[contains(@class,'oxd-form-actions')]//button[@type='submit']");
        private By cancelComment = By.xpath("//div[contains(@class,'oxd-form-actions')]//button[@type='button']");
        private By commentModal = By.xpath("//div[@role='document']");

        private WebElement getPageElement(By locator) {
            return driver.findElement(locator);
        }

        public Rows(WebDriver driver, WebElement row) {
            this.driver = driver;
            this.row = row;
        }

        public WebElement getRowElement(By locator) {
            return row.findElement(locator);
        }

        private void clearInput(By locator) {
            WebElement element = getElement(locator);
            element.click();
            element.sendKeys(Keys.CONTROL, "a");
            element.sendKeys(Keys.BACK_SPACE);
        }

        private void selectProject(String projectName) {
            clearInput(project);
            WebElement input = row.findElement(project);
            input.click();
            input.sendKeys("a");

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(autocomplete)
            );

            WebElement option = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            projectOption(projectName)
                    )
            );

            option.click();
        }

        private By projectOption(String projectName) {
            return By.xpath(
                    "//div[@role='listbox']//div[@role='option']" +
                            "[normalize-space()='" + projectName + "']"
            );
        }

        private By selectDropdown(String option) {
            return By.xpath(
                    "//div[contains(@class, 'oxd-select-option')]" +
                            "//span[normalize-space()='"+ option + "']"
            );
        }

        private void selectActivity(String activityName) {
            getRowElement(activity).click();

            WebElement option = wait.until(
                    ExpectedConditions.elementToBeClickable(selectDropdown(activityName))
            );

            option.click();
        }

        private void fillTime(String comment) {
            List<WebElement> elements = row.findElements(inputActivity);

            for (WebElement element : elements) {
                element.click();
                element.sendKeys("9");
                fillComment(comment);
            }
        }

        public void deleteRow() {
            getRowElement(deleteRow).click();
        }

        public void resetValues() {
            getPageElement(resetbutton).click();
        }

        public void cancel() {
            getPageElement(cancelButton).click();
        }

        private void comment() {
            getPageElement(commentButton).click();
        }

        private void enterComment(String comment) {
            getPageElement(commentBox).sendKeys(comment);
        }

        private void saveComment() {
            getPageElement(saveComment).click();
        }

        private void cancelComment() {
            getPageElement(cancelComment).click();
        }

        public void fillComment(String comment) {
            comment();
            wait.until(ExpectedConditions.elementToBeClickable(commentBox)).click();
            enterComment(comment);
            saveComment();
            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(commentModal)
            );
        }

        public void fillTimesheet(String projectName, String option, String comment) {
            selectProject(projectName);
            selectActivity(option);
            fillTime(comment);
            wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        }
    }
}
