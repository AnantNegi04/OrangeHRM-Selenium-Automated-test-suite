package pages.BaseComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Dashboard.Dashboard;

import java.time.Duration;

public class LoginPage extends BasePage {
    private By userName = By.name("username");
    private By userPassword = By.xpath("//input[@type='password']");
    private By submit = By.xpath("//button[contains(normalize-space(.), 'Login')]");

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

    public Dashboard login(String username, String password) {
        find(userName).sendKeys(username);
        find(userPassword).sendKeys(password);
        click(submit);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-navbar-nav")));
        return new Dashboard(driver);
    }
}
