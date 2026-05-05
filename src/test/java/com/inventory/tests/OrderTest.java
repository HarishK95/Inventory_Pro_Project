package com.inventory.tests;

import com.inventory.pages.LoginPage;
import com.inventory.pages.OrderPage;
import com.inventory.pages.ProductPage;
import com.inventory.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OrderTest extends BaseTest {

    private OrderPage orderPage;
    private String productName;
    private String sku;

    private static String createdProductName;

    @BeforeMethod(alwaysRun = true)
    public void loginAndCreateProduct() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                ConfigReader.get("adminEmail"),
                ConfigReader.get("adminPassword")
        );

        productName = "OrderProduct " + System.currentTimeMillis();
        sku = "ORDSKU" + System.currentTimeMillis();

        ProductPage productPage = new ProductPage(driver);
        productPage.addProduct(productName, sku, "200", "20");

        orderPage = new OrderPage(driver);
    }

    @Test(priority = 1, description = "Verify user can create a new order")
    public void verifyCreateNewOrder() {
        orderPage.createOrder(productName, "2");

        createdProductName = productName;

        Assert.assertTrue(
                orderPage.isOrderCreated(),
                "Order should be created successfully"
        );
    }

    @Test(priority = 2, dependsOnMethods = "verifyCreateNewOrder",
            description = "Verify order appears in order list")
    public void verifyOderIsCreated() {

        Assert.assertTrue(
                orderPage.isOrderVerification(createdProductName),
                "Order should appear in order list"
        );
    }
}