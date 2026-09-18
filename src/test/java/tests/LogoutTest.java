package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class LogoutTest extends BaseTest {

    @Test(priority = 1, description = "Verify successful logout from the application")
    public void testLogout() {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isPageLoaded(), "Inventory page should be displayed after login");

        // Step 2: Perform logout via sidebar menu
        LoginPage returnedLoginPage = inventoryPage.logout();

        // Step 3: Assert user is redirected back to login page
        Assert.assertTrue(returnedLoginPage.isLoginButtonDisplayed(), "Login button should be visible on login page after logout");
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"), "Current URL should be the base login URL");
    }
}
