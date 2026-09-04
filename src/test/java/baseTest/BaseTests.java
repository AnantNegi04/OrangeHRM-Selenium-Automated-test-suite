package baseTest;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.BaseComponents.LoginPage;

import java.util.HashMap;
import java.util.Map;

public class BaseTests {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected static final String BASE_URL = "https://opensource-demo.orangehrmlive.com/";

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding");
        options.addArguments("--incognito");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
    }

    @BeforeMethod
    public void resetState(ITestContext context) {
        context.setAttribute("driver", driver);
        driver.get(BASE_URL);
        driver.manage().deleteAllCookies();
        driver.get(BASE_URL);
        loginPage = new LoginPage(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
