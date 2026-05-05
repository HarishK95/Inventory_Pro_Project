package com.inventory.tests;

import com.inventory.pages.LoginPage;
import com.inventory.pages.ProductPage;
import com.inventory.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {
    private ProductPage productPage;
    private String productName;
    private String sku;
    private String quantity;
    private String price;
    private String newPrice;
    private String newQuantity;
    

    @BeforeMethod(alwaysRun = true)
    public void loginAsAdmin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("adminEmail"), ConfigReader.get("adminPassword"));
        productPage = new ProductPage(driver);
        productName = "SampleProduct" + System.currentTimeMillis();
        sku = "SKU" + System.currentTimeMillis();
        price = "10.0";
        quantity = "10";
    }

    @Test(priority = 1, description = "Verify admin can add a new product")
    public void verifyAddNewProduct() {
        productPage.addProduct(productName, sku, quantity, price);
        Assert.assertTrue(productPage.isSuccessMessageDisplayed() || productPage.isProductDisplayed(productName ,sku, quantity, price),
                "Product should be added successfully");
    }

    @Test(priority = 2, description = "Verify product can be searched by name or SKU")
    public void verifySearchProduct() {
        productPage.addProduct(productName, sku, price, quantity);
        productPage.searchProduct(productName);
        Assert.assertTrue(productPage.isProductDisplayed(productName, sku, quantity, price),
                "Product should be visible in search result");
    }

    @Test(priority = 3, description = "Verify admin can edit existing product")
    public void verifyEditProduct() {

        productPage.addProduct(productName, sku, price, quantity);
        productPage.searchProduct(productName);
        String updatedName = productName + " Updated";
        newPrice = "30.0";
        newQuantity = "20";
        productPage.editProduct(productName, updatedName, newPrice, newQuantity);
        productPage.searchProduct(updatedName);
        Assert.assertTrue(productPage.isSuccessMessageDisplayed() || productPage.isProductDisplayed(updatedName, sku, newPrice, newQuantity),
                "Product should be updated successfully"
        );
    }
    @Test(priority = 4, description = "Verify admin can delete existing product")
    public void verifyDeleteProduct() {
        productPage.addProduct(productName, sku, price, quantity);
        productPage.searchProduct(productName);
        productPage.deleteSingleProduct(productName);
        Assert.assertTrue(productPage.isDeleteMessageDisplayed(), "Product deleted successfully");
        productPage.deleteAllSampleProducts();
        Assert.assertTrue(productPage.isNextButtonDisabled(), "All products deleted and no more pages exist");
    }
}
