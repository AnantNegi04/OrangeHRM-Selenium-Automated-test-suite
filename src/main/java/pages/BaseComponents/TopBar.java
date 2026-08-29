package pages.BaseComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TopBar {
    private WebDriver driver;
    private By upgradeButton = By.className("orangehrm-upgrade-button");
    private By dropDown = By.className("oxd-userdropdown-icon");
    private By dropDownList = By.className("oxd-userdropdown-link");
    private WebDriverWait wait;

    public TopBar(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void clickUpgradePage() {
        driver.findElement(upgradeButton).click();
    }

    public void clickDropDown() {
        wait.until(
                ExpectedConditions.elementToBeClickable(dropDown)
        ).click();
    }

    private By dropdownList(String item){
        return By.xpath(
                "//a[@role='menuitem']" +
                "[normalize-space()='" + item + "']"
        );
    }

    public void getDropDownItem(String item) {
        clickDropDown();
        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(dropdownList(item))
        );

        option.click();
    }

    public boolean isDropDownVisible() {
        return !driver.findElements(dropDown).isEmpty();
    }
}
