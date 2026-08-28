package pages.BaseComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Admin.AdminPage;
import pages.Buzz.BuzzPage;
import pages.Claim.ClaimPage;
import pages.Dashboard.Dashboard;
import pages.Directory.DirectoryPage;
import pages.Leave.LeavePage;
import pages.Maintenance.MaintenancePage;
import pages.MyInfo.MyInfoPage;
import pages.PIM.PIMPage;
import pages.Performance.PerformancePage;
import pages.Recruitment.RecruitmentPage;
import pages.Time.TimePage;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class SideBar extends BasePage{

    private By search = By.xpath("//input[contains(@class, 'oxd-input--active')]");
    private By sideBarToggle = By.xpath("//button[contains(@class,'oxd-main-menu-button')]");
    private By sideBarToggleLeft = By.xpath("//i[contains(@class,'bi-chevron-left')]");
    private By menuItems = By.cssSelector(".oxd-main-menu-item--name");
    private By brandLogo = By.cssSelector(".oxd-brand");

    public SideBar(WebDriver driver) {
        super(driver);
    }

    private void expandSideBar() {
        if (!isSideBarExpanded()) {
            wait.until(ExpectedConditions.elementToBeClickable(sideBarToggle)).click();
        }
    }

    private WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    private void typeSearchText(String searchText) {
        expandSideBar();
        WebElement input = driver.findElement(search);
        input.clear();
        input.sendKeys(searchText);
    }

    public boolean searchHasNoResults(String searchText) {
        typeSearchText(searchText);
        try {
            return wait.until(d -> d.findElements(menuItems).isEmpty());
        } catch (TimeoutException e) {
            return false;
        }
    }

    private WebElement searchResult(String searchText) {
        typeSearchText(searchText);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(menuItems));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("No search results found for: " + searchText);
        }
        return getElement(menuItems);
    }

    private boolean isSideBarExpanded() {
        return !driver.findElements(sideBarToggleLeft).isEmpty();
    }

    private By getSideBarElement(String item) {
        return By.xpath(
                "//a[contains(@class, 'oxd-main-menu-item')]" +
                "//span[normalize-space()='" + item + "']"
        );
    }

    private void navigate(String item) {
        expandSideBar();

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(getSideBarElement(item))
        );

        element.click();

        wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.className("oxd-layout-container")
                )
        );
    }

    public void navigateToMenuItem(String item) {
        navigate(item);
    }

    public AdminPage goToAdminPage() {
        navigate("Admin");
        return new AdminPage(driver);
    }

    public ClaimPage goToClaimPage() {
        navigate("Claim");
        return new ClaimPage(driver);
    }

    public Dashboard goToDashboardPage() {
        navigate("Dashboard");
        return new Dashboard(driver);
    }

    public DirectoryPage goToDirectoryPage() {
        navigate("Directory");
        return new DirectoryPage(driver);
    }

    public LeavePage goToLeavePage() {
        navigate("Leave");
        return new LeavePage(driver);
    }

    public MaintenancePage goToMaintenancePage() {
        navigate("Maintenance");
        return new MaintenancePage(driver);
    }

    public MyInfoPage goToMyInfoPage() {
        navigate("My Info");
        return new MyInfoPage(driver);
    }

    public PIMPage goToPIMPage() {
        navigate("PIM");
        return new PIMPage(driver);
    }

    public PerformancePage goToPerformancePage() {
        navigate("Performance");
        return new PerformancePage(driver);
    }

    public RecruitmentPage goToRecruitmentPage() {
        navigate("Recruitment");
        return new RecruitmentPage(driver);
    }

    public TimePage goToTimePage() {
        navigate("Time");
        return new TimePage(driver);
    }

    public BuzzPage goToBuzzPage() {
        navigate("Buzz");
        return new BuzzPage(driver);
    }

    public BrandPage goToBrandPage() {
        driver.findElement(brandLogo).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("main")));
        return new BrandPage(driver);
    }
}
