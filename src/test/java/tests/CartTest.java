package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.List;

public class CartTest extends BaseTest {

    private final String PRODUCT_1 = "Sauce Labs Backpack";
    private final String PRODUCT_2 = "Sauce Labs Bike Light";
    private final String PRODUCT_3 = "Sauce Labs Bolt T-Shirt";

    @Test(priority = 1, description = "Select 3 products, add to cart, and verify cart contents count is 3")
    public void testAddThreeProductsToCart() {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isPageLoaded(), "Inventory page should be displayed after login");

        // Step 2: Add 3 products to cart
        inventoryPage.addProductToCart(PRODUCT_1);
        inventoryPage.addProductToCart(PRODUCT_2);
        inventoryPage.addProductToCart(PRODUCT_3);

        // Step 3: Verify badge count on products page is 3
        Assert.assertTrue(inventoryPage.waitForCartBadgeCount(3), "Cart badge on products page should show 3");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 3, "Cart badge on products page should show 3");

        // Step 4: Navigate to Cart page and verify cart contents count is 3
        CartPage cartPage = inventoryPage.openCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 3, "Cart page should contain exactly 3 items");

        // Step 5: Verify item names in cart
        List<String> cartItems = cartPage.getCartItemNames();
        Assert.assertTrue(cartItems.contains(PRODUCT_1), "Cart should contain: " + PRODUCT_1);
        Assert.assertTrue(cartItems.contains(PRODUCT_2), "Cart should contain: " + PRODUCT_2);
        Assert.assertTrue(cartItems.contains(PRODUCT_3), "Cart should contain: " + PRODUCT_3);
    }

    @Test(priority = 2, description = "Remove 1 product from products page using remove button and verify cart contents count is 2")
    public void testRemoveOneProductFromProductsPage() {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isPageLoaded(), "Inventory page should be displayed after login");

        // Step 2: Add 3 products to cart
        inventoryPage.addProductToCart(PRODUCT_1);
        inventoryPage.addProductToCart(PRODUCT_2);
        inventoryPage.addProductToCart(PRODUCT_3);
        Assert.assertTrue(inventoryPage.waitForCartBadgeCount(3), "Cart badge should initially show 3");

        // Step 3: Remove 1 product from products page using 'Remove' button
        inventoryPage.removeProductFromProductsPage(PRODUCT_1);

        // Step 4: Verify badge count on products page updates to 2
        Assert.assertTrue(inventoryPage.waitForCartBadgeCount(2), "Cart badge should show 2 after removing 1 product");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 2, "Cart badge should show 2 after removing 1 product");

        // Step 5: Navigate to Cart page and verify cart contents count is 2
        CartPage cartPage = inventoryPage.openCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart page should contain exactly 2 items");

        // Step 6: Verify removed item is absent and remaining items are present
        List<String> cartItems = cartPage.getCartItemNames();
        Assert.assertFalse(cartItems.contains(PRODUCT_1), "Removed product should not be in cart: " + PRODUCT_1);
        Assert.assertTrue(cartItems.contains(PRODUCT_2), "Cart should still contain: " + PRODUCT_2);
        Assert.assertTrue(cartItems.contains(PRODUCT_3), "Cart should still contain: " + PRODUCT_3);
    }
}
