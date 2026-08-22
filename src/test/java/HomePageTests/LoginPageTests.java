package HomePageTests;

import baseTest.BaseTests;
import org.testng.annotations.Test;
import pages.Dashboard.Dashboard;

import static org.testng.Assert.assertEquals;

public class LoginPageTests extends BaseTests {
    @Test
    public void loginTests() {
        Dashboard dashboard = loginPage.login("Admin", "admin123");
    }
}
