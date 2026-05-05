package com.inventory.tests;

import com.inventory.pages.LoginPage;
import com.inventory.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Verify successful login with valid admin credentials")
    public void verifySuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("adminEmail"), ConfigReader.get("adminPassword"));
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Dashboard should be displayed after valid login");
    }

    @Test(priority = 2, description = "Verify login failure with invalid password")
    public void verifyInvalidPasswordLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("adminEmail"), ConfigReader.get("invalidPassword"));
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed for invalid password");
    }

    @Test(priority = 3, description = "Verify login validation for empty fields")
    public void verifyEmptyLoginValidation() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginWithoutData();
        Assert.assertEquals(loginPage.isErrorDisplayedForEmptyLogin(), "Please fill in this field.");
    }
}
