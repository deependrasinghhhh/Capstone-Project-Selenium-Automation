package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By pageTitle = By.className("title");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");
    private By sortDropdown = By.className("product_sort_container");
    private By productPrices = By.className("inventory_item_price");
    private By burgerMenuBtn = By.id("react-burger-menu-btn");
    private By logoutSidebarLink = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isPageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText().equalsIgnoreCase("Products");
        } catch (Exception e) {
            return false;
        }
    }

    public String getTitleText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public void addProductToCart(String productName) {
        // Find product container by name, then click Add to cart button
        String xpath = String.format("//div[@class='inventory_item_name ' and text()='%s']/ancestor::div[@class='inventory_item']//button[contains(@id, 'add-to-cart')]", productName);
        // Fallback for flexible text match
        By addToCartBy = By.xpath(String.format("//div[contains(@class,'inventory_item_name') and text()='%s']/ancestor::div[@class='inventory_item']//button[text()='Add to cart']", productName));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartBy));
        button.click();
    }

    public void removeProductFromProductsPage(String productName) {
        // Find product container by name, then click Remove button on the products page
        By removeBy = By.xpath(String.format("//div[contains(@class,'inventory_item_name') and text()='%s']/ancestor::div[@class='inventory_item']//button[text()='Remove']", productName));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(removeBy));
        button.click();
    }

    public int getCartBadgeCount() {
        try {
            List<WebElement> badges = driver.findElements(cartBadge);
            if (badges.isEmpty() || !badges.get(0).isDisplayed()) {
                return 0;
            }
            return Integer.parseInt(badges.get(0).getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean waitForCartBadgeCount(int expectedCount) {
        try {
            if (expectedCount == 0) {
                return wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
            } else {
                return wait.until(ExpectedConditions.textToBe(cartBadge, String.valueOf(expectedCount)));
            }
        } catch (Exception e) {
            return false;
        }
    }

    public CartPage openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        return new CartPage(driver);
    }

    public void selectSortFilter(String optionValueOrText) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
        Select select = new Select(dropdown);
        try {
            select.selectByValue(optionValueOrText);
        } catch (Exception e) {
            select.selectByVisibleText(optionValueOrText);
        }
    }

    public List<Double> getProductPrices() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productPrices));
        List<WebElement> priceElements = driver.findElements(productPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : priceElements) {
            String text = element.getText().replace("$", "").trim();
            prices.add(Double.parseDouble(text));
        }
        return prices;
    }

    public LoginPage logout() {
        WebElement menuBtn = wait.until(ExpectedConditions.presenceOfElementLocated(burgerMenuBtn));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(menuBtn)).click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", menuBtn);
        }

        WebElement logoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(logoutSidebarLink));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutBtn);
        }
        return new LoginPage(driver);
    }
}
