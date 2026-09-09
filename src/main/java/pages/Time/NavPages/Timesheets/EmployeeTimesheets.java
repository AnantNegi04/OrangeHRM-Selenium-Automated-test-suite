package pages.Time.NavPages.Timesheets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;

public class EmployeeTimesheets extends BasePage {

    private By alert = By.xpath("//p[contains(@class, 'oxd-alert')]");
    private By inputEmployeeName = By.xpath("//input[@placeholder='Type for hints...']");
    private By autoComplete = By.cssSelector("div[role='listbox'][class*='oxd-autocomplete-dropdown']");
    private By viewButton = By.xpath("//button[@type='submit']");

    public EmployeeTimesheets(WebDriver driver) {
        super(driver);
    }

    private WebElement getElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    private By autoCompleteOption(String hint) {
        return By.xpath(
                "//div[@role='listbox']//div[@role='option']" +
                        "[.//span[contains(normalize-space(), '" + hint + "')]]"
        );
    }

    private void getAutoComplete(String hint) {
        getElement(inputEmployeeName).sendKeys(hint);

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(autoCompleteOption(hint))
        );

        option.click();
    }

    private void clickViewButton() {
        getElement(viewButton).click();
    }

    public String viewRecord(String hint) {
        getAutoComplete(hint);
        clickViewButton();
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(alert)
        );
        return getElement(alert).getText();
    }
}
