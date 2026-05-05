# Inventory Pro Automation Framework

## Tech Stack
- Java 11
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model
- WebDriverManager
- Apache POI Excel Reader
- Extent Reports
- TestNG ITestListener

## Framework Structure

```text
src/main/java
 ├── com.inventory.base
 │    ├── BasePage.java
 │    └── DriverFactory.java
 ├── com.inventory.pages
 │    ├── LoginPage.java
 │    ├── ProductPage.java
 │    └── OrderPage.java
 ├── com.inventory.utils
 │    ├── ConfigReader.java
 │    ├── ExcelReader.java
 │    ├── ScreenshotUtil.java
 │    └── ExtentReportManager.java
 └── com.inventory.constants
      └── AppConstants.java

src/test/java
 ├── com.inventory.tests
 │    ├── BaseTest.java
 │    ├── LoginTest.java
 │    ├── ProductTest.java
 │    └── OrderTest.java
 └── com.inventory.listeners
      └── TestListener.java
```

## Covered Scenarios

### Login Tests
- Successful login with valid credentials
- Login failure with invalid password
- Login validation with empty fields

### Product Tests
- Add new product
- Search product by name/SKU
- Edit product
- Delete product

### Order Tests
- Create new order
- Verify order appears in order list

## How to Run

```bash
mvn clean test
```

## Configuration
Update browser, URL, credentials, and waits here:

```text
src/main/resources/config.properties
```

## Reports
After execution, Extent Report will be generated under:

```text
reports/
```

## Screenshots
Screenshots for failed test cases will be saved under:

```text
screenshots/
```

## Important Note
The locators are written using generic XPath based on expected UI labels. After inspecting the actual application DOM, update locators in page classes if required.
