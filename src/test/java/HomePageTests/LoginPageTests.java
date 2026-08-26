package HomePageTests;

import baseTest.BaseTests;
import org.testng.annotations.Test;
import pages.Dashboard.Dashboard;

import static org.testng.Assert.*;

public class LoginPageTests extends BaseTests {
    @Test(description = "Valid username and password should load the dashboard")
    public void validLoginLoadsDashboard() {
        Dashboard dashboard = loginPage.login("Admin", "admin123");
        assertFalse(loginPage.isOnLoginPage(), "Should have navigated away from login page");
        dashboard.logOut();
    }

    @Test(description = "Invalid username with valid password should be rejected")
    public void invalidUsernameValidPassword() {
        loginPage.attemptLogin("NotARealUser", "admin123");
        assertTrue(loginPage.isOnLoginPage(), "Should remain on login page");
        assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
    }

    @Test(description = "Valid username with invalid password should be rejected")
    public void validUsernameInvalidPassword() {
        loginPage.attemptLogin("Admin", "wrongPassword123");
        assertTrue(loginPage.isOnLoginPage(), "Should remain on login page");
        assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
    }

    @Test(description = "Both invalid username and password should be rejected")
    public void invalidUsernameInvalidPassword() {
        loginPage.attemptLogin("NotARealUser", "wrongPassword123");
        assertTrue(loginPage.isOnLoginPage(), "Should remain on login page");
        assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
    }

    @Test(description = "Empty username with filled password shows username-only required error")
    public void emptyUsernameFilledPassword() {
        loginPage.attemptLogin("", "admin123");
        assertTrue(loginPage.isUsernameRequiredErrorDisplayed(), "Username required error should show");
        assertFalse(loginPage.isPasswordRequiredErrorDisplayed(), "Password required error should NOT show");
    }

    @Test(description = "Filled username with empty password shows password-only required error")
    public void filledUsernameEmptyPassword() {
        loginPage.attemptLogin("Admin", "");
        assertFalse(loginPage.isUsernameRequiredErrorDisplayed(), "Username required error should NOT show");
        assertTrue(loginPage.isPasswordRequiredErrorDisplayed(), "Password required error should show");
    }

    @Test(description = "Both fields empty shows both required errors")
    public void bothFieldsEmpty() {
        loginPage.attemptLogin("", "");
        assertTrue(loginPage.isUsernameRequiredErrorDisplayed(), "Username required error should show");
        assertTrue(loginPage.isPasswordRequiredErrorDisplayed(), "Password required error should show");
    }

    @Test(description = "Very long username input should be rejected gracefully, no crash")
    public void longStringUsernameHandledGracefully() {
        String longUsername = "a".repeat(500);
        loginPage.attemptLogin(longUsername, "admin123");
        assertTrue(loginPage.isOnLoginPage(), "Should remain on login page without crashing");
    }

    @Test(description = "Injection-style input is treated as an ordinary invalid credential")
    public void injectionStyleInputRejectedNormally() {
        loginPage.attemptLogin("' OR '1'='1", "' OR '1'='1");
        assertTrue(loginPage.isOnLoginPage(), "Should remain on login page");
        assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
    }

    @Test(description = "Tampering with the CSRF token should cause the server to reject the login request")
    public void tamperedCsrfTokenIsRejected() {
        loginPage.tamperCsrfToken("clearly-invalid-token-value");
        loginPage.attemptLogin("Admin", "admin123");
        assertTrue(loginPage.isOnLoginPage(), "Should remain on login page after CSRF rejection");
        assertEquals(loginPage.getErrorMessage(), "CSRF token validation failed");
    }
}
