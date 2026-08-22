package pages.Dashboard.Components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BaseComponents.BasePage;

public class QuickLaunch extends BasePage {

    private By assignLeave = By.xpath("//button[@title='Assign Leave']");
    private By leaveList = By.xpath("//button[@title='Leave List']");
    private By timeSheets = By.xpath("//button[@title='Timesheets']");
    private By applyLeave = By.xpath("//button[@title='Apply Leave']");
    private By myLeave =  By.xpath("//button[@title='My Leave']");
    private By myTimeSheet = By.xpath("//button[@title='My Timesheet']");

    public QuickLaunch(WebDriver driver) {
        super(driver);
    }
}
