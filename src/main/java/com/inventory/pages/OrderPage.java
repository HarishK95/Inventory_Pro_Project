package com.inventory.pages;

import com.inventory.base.BasePage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class OrderPage extends BasePage {

    private final By ordersMenu = By.xpath("//a[contains(normalize-space(),'Orders')]");
    private final By createOrderBtn = By.xpath("//button[contains(normalize-space(),'Create Order')]");
    private final By productDropdown = By.xpath("//select[contains(@class,'mt-1') and contains(@class,'w-full')]");
    private final By quantityTxt = By.xpath("//input[contains(@class,'mt-1') and contains(@class,'w-full')]");
    private final By submitOrderBtn = By
            .xpath("//button[@class = 'px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700']");
    private final By orderSuccessMsg = By.xpath("//div[contains(normalize-space(),'Order created successfully')]");
    private final By orderNumberTxt = By.xpath("//span[text()='Order #']");
    private final By productNameColumn = By.xpath("//table/tbody/tr/td[1]");

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public void openOrders() {
        click(ordersMenu);
    }

    public void createOrder(String productName, String quantity) {
        openOrders();
        click(createOrderBtn);

        selectProduct(productName);

        type(quantityTxt, quantity);
        click(submitOrderBtn);
    }

    private void selectProduct(String productName) {
        boolean found = false;

        for (int i = 0; i < 10; i++) { // retry 10 times
            Select select = new Select(driver.findElement(productDropdown));

            for (WebElement option : select.getOptions()) {
                String text = option.getText().trim();

                if (text.contains(productName)) {
                    option.click();
                    found = true;
                    break;
                }
            }

            if (found) {
                break;
            } else {
                throw new RuntimeException("Product not found in dropdown: " + productName);
            }
        }
    }

    public boolean isOrderCreated() {
        waitForVisibility(orderSuccessMsg);
        return isDisplayed(orderSuccessMsg);
    }

    public boolean isOrderVerification(String productName) {

        // ✅ Added safety check
        if (productName == null || productName.trim().isEmpty()) {
            throw new RuntimeException("Product name is null or empty");
        }

        openOrders();
        waitForVisibility(orderNumberTxt);

        List<WebElement> orders = driver.findElements(orderNumberTxt);

        for (WebElement order : orders) {

            order.click();
            waitForVisibility(productNameColumn);

            List<WebElement> products = driver.findElements(productNameColumn);

            for (WebElement prod : products) {
                String text = prod.getText().trim();
                if (text.contains(productName)) {
                    return true;
                }
            }
        }
        return false;
    }
}