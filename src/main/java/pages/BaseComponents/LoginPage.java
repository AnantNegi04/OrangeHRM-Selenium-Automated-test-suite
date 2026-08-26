package pages.BaseComponents;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Dashboard.Dashboard;
import pages.Leave.Components.TopBarMenu;

import java.time.Duration;

public class LoginPage extends BasePage {
    private By userName = By.name("username");
    private By userPassword = By.xpath("//input[@type='password']");
    private By submit = By.xpath("//button[contains(normalize-space(.), 'Login')]");
    private By errorMessage = By.xpath("//p[contains(@class, 'oxd-alert-content-text')]");
    private By usernameRequiredError = By.xpath(
            "//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')]" +
                    "//span[contains(@class,'oxd-input-group__message')]"
    );
    private By passwordRequiredError = By.xpath(
            "//input[@name='password']/ancestor::div[contains(@class,'oxd-input-group')]" +
                    "//span[contains(@class,'oxd-input-group__message')]"
    );

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public boolean isUsernameRequiredErrorDisplayed() {
        try {
            return wait.until(d -> !d.findElements(usernameRequiredError).isEmpty());
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPasswordRequiredErrorDisplayed() {
        try {
            return wait.until(d -> !d.findElements(passwordRequiredError).isEmpty());
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("/auth/login");
    }

    public void attemptLogin(String username, String password) {
        find(userName).sendKeys(username);
        find(userPassword).sendKeys(password);
        click(submit);
    }

    public Dashboard login(String username, String password) {
        attemptLogin(username, password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-layout-context")));
        return new Dashboard(driver);
    }

    public void tamperCsrfToken(String fakeValue) {
        WebElement tokenInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.name("_token"))
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];", tokenInput, fakeValue
        );
    }
}
