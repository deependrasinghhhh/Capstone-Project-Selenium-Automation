package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            // Test Case 1: Positive valid login
            {"standard_user", "secret_sauce", true, ""},
            // Test Case 2: Negative - locked out user
            {"locked_out_user", "secret_sauce", false, "Sorry, this user has been locked out"},
            // Test Case 3: Negative - invalid username
            {"invalid_user", "secret_sauce", false, "Username and password do not match"},
            // Test Case 4: Negative - invalid password
            {"standard_user", "wrong_sauce", false, "Username and password do not match"},
            // Test Case 5: Negative - empty username
            {"", "secret_sauce", false, "Username is required"},
            // Test Case 6: Negative - empty password
            {"standard_user", "", false, "Password is required"}
        };
    }

    @Test(dataProvider = "loginData", priority = 1, description = "Login positive and negative scenarios using DataProvider")
    public void testLoginScenarios(String username, String password, boolean isPositive, String expectedError) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (isPositive) {
            InventoryPage inventoryPage = new InventoryPage(driver);
            Assert.assertTrue(inventoryPage.isPageLoaded(), "Inventory page should be loaded on successful login");
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Current URL should contain 'inventory.html'");
            Assert.assertEquals(inventoryPage.getTitleText(), "Products", "Header title should be 'Products'");
        } else {
            String actualError = loginPage.getErrorMessage();
            Assert.assertTrue(actualError.contains(expectedError), 
                String.format("Expected error message to contain '%s', but got '%s'", expectedError, actualError));
        }
    }
}
