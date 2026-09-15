package pages.Leave.Components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;

import java.util.List;
import java.util.NoSuchElementException;

public class TopBarMenu extends BasePage {

    private By topBarMenu = By.xpath("//li[contains(@class, 'oxd-topbar-body-nav-tab')]");
    private By menuDropdown = By.xpath("//a[contains(@class, 'oxd-topbar-body-nav-tab-link')]");
    private By moreOption = By.xpath("//a[contains(@class, '--more')]");

    public TopBarMenu(WebDriver driver) {
        super(driver);
    }

    private By topBarTab(String navTab) {
        return By.xpath(
                "//li[contains(@class, 'oxd-topbar-body-nav-tab')]" +
                        "//a[normalize-space()='" + navTab + "']"
        );
    }

    private By moreTab() {
        return By.xpath(
                "//li[contains(@class, 'oxd-topbar-body-nav-tab')]" +
                        "//a[normalize-space()='More']"
        );
    }

    private By dropdownItem(String navTab) {
        return By.xpath(
                "//a[contains(@class, 'oxd-topbar-body-nav-tab-link')]" +
                        "[normalize-space()='" + navTab + "']"
        );
    }

    public WebElement getTopBarMenu(String navTab) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(topBarMenu));

        if (!driver.findElements(topBarTab(navTab)).isEmpty()) {
            return driver.findElement(topBarTab(navTab));
        }

        if (!driver.findElements(moreTab()).isEmpty()) {
            driver.findElement(moreTab()).click();
            return getMoreOption(navTab);
        }

        throw new NoSuchElementException(
                "No such element exist " + navTab
        );
    }

    public WebElement getDropdown(String menuOption, String navTab) {
        getTopBarMenu(menuOption).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(menuDropdown));

        if (!driver.findElements(dropdownItem(navTab)).isEmpty()) {
            return driver.findElement(dropdownItem(navTab));
        }

        throw new NoSuchElementException(
                "No such element exist " + navTab
        );
    }

    public WebElement getMoreOption(String navTab) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(moreOption));

        List<WebElement> menuList = driver.findElements(moreOption);
        for (WebElement menu : menuList) {
            if (menu.getText().equals(navTab)) {
                return menu;
            }
        }
        throw new NoSuchElementException(
                "No such element exist " + navTab
        );
    }
}