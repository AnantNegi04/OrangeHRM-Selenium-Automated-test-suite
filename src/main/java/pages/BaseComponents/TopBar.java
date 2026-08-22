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
    private By drowpDown = By.className("oxd-userdropdown-icon");
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
        driver.findElement(drowpDown).click();
    }

    private List<WebElement> dropdownList(){
        clickDropDown();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropDownList));
        return driver.findElements(dropDownList);
    }

    public WebElement getDropDownItem(String item) {
        List<WebElement> dropdownList = dropdownList();
        for (WebElement element : dropdownList) {
            if (element.getText().equals(item)) {
                return element;
            }
        }
        throw new NoSuchElementException(
                "No such element found in dropdown list: " + item
        );
    }
}
