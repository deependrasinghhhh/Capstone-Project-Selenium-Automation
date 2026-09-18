# SauceDemo Automation Capstone Project (Selenium + TestNG)

A clean, modern, and robust automation framework for testing [SauceDemo](https://www.saucedemo.com) built with **Selenium WebDriver (Java)**, **TestNG**, and the **Page Object Model (POM)** pattern.

---

## 📋 Features & Implemented Scenarios

1. **Login Positive and Negative Scenarios (Externalized via `@DataProvider`)**:
   - Positive login with `standard_user` and `secret_sauce`.
   - Negative login: Locked out user (`locked_out_user`).
   - Negative login: Invalid username (`invalid_user`).
   - Negative login: Invalid password (`wrong_sauce`).
   - Negative login: Empty username (`""`).
   - Negative login: Empty password (`""`).
   - Complete assertion on page navigation, URLs, and error messages.

2. **Cart - Add 3 Products & Verify Contents Count**:
   - Adds 3 products to the cart (`Sauce Labs Backpack`, `Sauce Labs Bike Light`, `Sauce Labs Bolt T-Shirt`).
   - Asserts cart badge on products page shows `3`.
   - Navigates to `/cart.html` and asserts cart items count is `3` and verifies item names.

3. **Cart - Remove 1 Product from Products Page & Verify Contents Count**:
   - Adds 3 products.
   - Removes 1 product (`Sauce Labs Backpack`) directly from the products page using the "Remove" button.
   - Asserts cart badge on products page decreases to `2`.
   - Navigates to `/cart.html` and asserts cart items count is `2`, the removed item is gone, and the remaining 2 items persist.

4. **Product Sorting Filter - Price (Low to High)**:
   - Selects "Price (low to high)" (`lohi`) from the sort dropdown.
   - Collects all item prices and asserts they are strictly in ascending numerical order.

5. **Logout Flow**:
   - Opens the sidebar menu.
   - Clicks "Logout".
   - Asserts successful redirection to the login page and checks login button visibility.

---

## 🏗️ Project Structure

```
├── pom.xml                                      # Maven configuration (Selenium 4 + TestNG)
├── src
│   ├── main
│   │   └── java
│   │       └── pages
│   │           ├── LoginPage.java               # Page Object for login page & error validation
│   │           ├── InventoryPage.java           # Page Object for products, add/remove, sort, logout
│   │           └── CartPage.java                # Page Object for cart items verification
│   └── test
│       ├── java
│       │   ├── base
│       │   │   └── BaseTest.java                # @BeforeMethod (driver init) & @AfterMethod (teardown)
│       │   └── tests
│       │       ├── LoginTest.java               # Scenario 1 (Positive & Negative with @DataProvider)
│       │       ├── CartTest.java                # Scenario 2 & 3 (Add 3 items, Remove 1 from inventory)
│       │       ├── ProductFilterTest.java       # Scenario 4 (Price low to high)
│       │       └── LogoutTest.java              # Scenario 5 (Logout verification)
│       └── resources
│           └── testng.xml                       # TestNG suite runner
└── target
    └── surefire-reports                         # HTML & XML TestNG reports
```

---

## 🚀 How to Run the Tests

### Option 1: Run all tests via Maven Command Line
```powershell
mvn clean test
```

### Option 2: Run with browser UI visible (Headed mode)
By default, tests execute in headless Chrome. To view the browser window while running:
```powershell
mvn clean test -Dheadless=false
```

### Option 3: Run specific test class
```powershell
mvn test -Dtest=LoginTest
mvn test -Dtest=CartTest
mvn test -Dtest=ProductFilterTest
mvn test -Dtest=LogoutTest
```

### Option 4: Run directly from IntelliJ IDEA / Eclipse
1. Right-click on `src/test/resources/testng.xml` -> **Run '.../testng.xml'**.
2. Or right-click any test class in `src/test/java/tests/` -> **Run 'TestName'**.

---

## 📊 Test Reports

After running the tests, TestNG generates interactive reports at:
- `target/surefire-reports/emailable-report.html`
- `target/surefire-reports/index.html`
