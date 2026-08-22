package pages.Leave.NavPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;
import pages.BaseComponents.SideBar;
import pages.Leave.Components.TopBarMenu;
import pages.Leave.LeavePage;

public class ApplyLeave extends BasePage {

    private TopBarMenu topBarMenu;
    private LeavePage leavePage;
    private SideBar sideBar;

    private By leaveBalance = By.xpath("//p[contains(@class, 'oxd-text--subtitle-2')]");

    public ApplyLeave(WebDriver driver) {
        super(driver);
    }

    public void displayLeaveBalance() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(leaveBalance));
        System.out.println(driver.findElement(leaveBalance).getText());
    }
}
