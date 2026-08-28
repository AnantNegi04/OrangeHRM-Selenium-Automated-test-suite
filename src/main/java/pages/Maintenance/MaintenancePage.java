package pages.Maintenance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pages.BaseComponents.BasePage;

public class MaintenancePage extends BasePage {
    private By password = By.xpath("//input[@type='password']");
    private By confirmPasswordHeading = By.xpath("//h6[normalize-space()='Administrator Access']");

    public MaintenancePage(WebDriver driver) {
        super(driver);
    }

    public boolean isPasswordConfirmationDisplayed() {
        return !driver.findElements(confirmPasswordHeading).isEmpty();
    }
}
