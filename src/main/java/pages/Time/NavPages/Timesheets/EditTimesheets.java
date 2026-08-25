package pages.Time.NavPages.Timesheets;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;

import java.util.List;
import java.util.Scanner;

public class EditTimesheets extends BasePage {

    private By resetbutton = By.xpath("//button[normalize-space()='Reset']");
    private By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    private By allRows = By.xpath("//tr[contains(@class, 'orangehrm-timesheet-table-body-row')]");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By addRow = By.cssSelector("i.bi-plus");
    private Scanner scanner = new Scanner(System.in);
    private By deleteRow = By.cssSelector("i.bi-trash");

    int rowCount;

    public EditTimesheets(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allRows));
        rowCount = driver.findElements(allRows).size();
    }

    public void fillRow1(String project, String activity, String comment) {
        if (rowCount > 2) {
            int i = rowCount - 1;
            while (i > 2) {
                WebElement row = driver.findElements(allRows).get(i);
                row.findElement(deleteRow).click();
                i--;
                rowCount--;
            }
        }

        Rows row = new Rows(driver, 0);
        row.deleteRow();
        row.fillTimesheet(project, activity, comment);
        driver.findElement(saveButton).click();
    }

    private class Rows {
        private WebDriver driver;
        private WebElement row;
        private int index;

        private By project = By.xpath(".//input[@placeholder='Type for hints...']");
        private By activity = By.xpath(".//div[contains(@class, 'oxd-select-text-input')]");
        private By inputActivity = By.xpath(".//input[contains(@class, 'oxd-input--active')]");
        private By commentButton = By.cssSelector("[class$='dots']");
        private By commentBox = By.cssSelector("textarea[placeholder='Comment here']");
        private By saveComment = By.xpath("//div[contains(@class,'oxd-form-actions')]//button[@type='submit']");
        private By cancelComment = By.xpath("//div[contains(@class,'oxd-form-actions')]//button[@type='button']");
        private By commentModal = By.xpath("//div[@role='document']");
        private By deleteRow = By.cssSelector("i.bi-trash");

        public Rows(WebDriver driver, int index) {
            this.driver = driver;
            this.index = index;
        }

        private WebElement getRow() {
            return driver.findElements(allRows).get(this.index);
        }

        private WebElement getRowElement(By locator) {
            return getRow().findElement(locator);
        }

        private WebElement getPageElement(By locator) {
            return driver.findElement(locator);
        }

        private void waitForAutocompleteReset() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        private void selectProject(String projectName) {
            WebElement input = getRowElement(project);

            waitForAutocompleteReset();
            input.sendKeys("a");

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
                            "[.//span[normalize-space()='" + projectName + "']]"
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

            int count = getRow().findElements(inputActivity).size();

            for (int i = 0; i < count - 1; i++) {
                List<WebElement> inputs = getRow().findElements(inputActivity);
                WebElement element = inputs.get(i);

                wait.until(driver -> {
                    try {
                        element.click();
                        element.sendKeys("9");
                        return true;
                    } catch (StaleElementReferenceException e) {
                        return false;
                    }
                });
                if (i == 0 || i == 1) {
                    fillComment(comment);
                }
            }
        }

        private void deleteRow() {
            getRowElement(deleteRow).click();
        }

        private void comment() {
            getRowElement(commentButton).click();
        }

        private void saveComment() {
            getPageElement(saveComment).click();
        }

        private void cancelComment() {
            getPageElement(cancelComment).click();
        }

        private void fillComment(String comment) {
            comment();

//            wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(commentBox)
//            );

            WebElement box = wait.until(
                    ExpectedConditions.elementToBeClickable(commentBox)
            );

            box.sendKeys(comment);

            saveComment();

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(commentModal)
            );
        }

        private void saveRow() {
            getPageElement(saveButton).click();
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(allRows)
            );
        }

        private void addRow() {
            getRowElement(addRow).click();
        }

        private void fillTimesheet(String projectName, String option, String comment) {
            selectProject(projectName);
            selectActivity(option);
            fillTime(comment);
            wait.until(
                    ExpectedConditions.elementToBeClickable(saveButton)
            );
        }
    }
}
