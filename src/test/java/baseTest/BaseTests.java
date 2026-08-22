package baseTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.BaseComponents.LoginPage;

public class BaseTests {
    private WebDriver driver;
    protected LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
    }

    @BeforeMethod
    public void goToLoginPage() {
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
