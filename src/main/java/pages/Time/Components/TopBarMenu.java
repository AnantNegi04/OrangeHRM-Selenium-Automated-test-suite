package pages.Time.Components;

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

    public TopBarMenu(WebDriver driver) {
        super(driver);
    }

    public WebElement getTopBarMenu(String navTab) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(topBarMenu));

        List<WebElement> menuList = driver.findElements(topBarMenu);

        for (WebElement menu : menuList) {
            System.out.println(menu.getText());
            if (menu.getText().equals(navTab)) {
                return menu;
            }
        }
        throw new NoSuchElementException(
                "No such element exist " + navTab
        );
    }

    public WebElement getDropdown(String menuOption, String navTab) {
        getTopBarMenu(menuOption).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(menuDropdown));

        List<WebElement> menuList = driver.findElements(menuDropdown);
        for (WebElement menu : menuList) {
            System.out.println(menu.getText());
            if (menu.getText().equals(navTab)) {
                return menu;
            }
        }
        throw new NoSuchElementException(
                "No such element exist " + navTab
        );
    }
}
