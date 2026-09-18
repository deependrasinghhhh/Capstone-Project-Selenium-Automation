package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductFilterTest extends BaseTest {

    @Test(priority = 1, description = "Verify product sorting filter by Price (low to high)")
    public void testFilterPriceLowToHigh() {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isPageLoaded(), "Inventory page should be displayed after login");

        // Step 2: Select 'Price (low to high)' from filter dropdown
        inventoryPage.selectSortFilter("lohi");

        // Step 3: Get all product prices from the page
        List<Double> actualPrices = inventoryPage.getProductPrices();
        Assert.assertFalse(actualPrices.isEmpty(), "Product prices list should not be empty");

        // Step 4: Create expected sorted list
        List<Double> expectedSortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedSortedPrices);

        // Step 5: Assert that actual prices are sorted in ascending order (low to high)
        Assert.assertEquals(actualPrices, expectedSortedPrices, 
            "Product prices should be sorted in ascending order (low to high). Actual: " + actualPrices);
    }
}
