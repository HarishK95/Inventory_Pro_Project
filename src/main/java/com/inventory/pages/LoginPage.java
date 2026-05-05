package com.inventory.pages;

import com.inventory.base.BasePage;
import com.inventory.utils.javaScriptUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By emailTxt = By.xpath("//input[@id = 'email']");
    private final By passwordTxt = By.xpath("//input[@id = 'password']");
    private final By signInButton = By.xpath("//button[text() ='Sign in']");
    private final By dashboardHeader = By.xpath("//a[text() ='Inventory Pro']");
    private final By errorMessageLoginFailed = By.xpath("//div[text()='User exists but password is wrong']");
   // private final By errorMessageEmptyFiled = By.xpath("//div[text()='User exists but password is wrong']");
    private final By logoutBtn = By.xpath("//button[text() ='Logout']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        type(emailTxt, email);
        type(passwordTxt, password);
        click(signInButton);
    }

    public void clickLoginWithoutData() {
        click(signInButton);
    }

    public boolean isLoginSuccessful() {
        return isDisplayed(dashboardHeader);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessageLoginFailed);
    }
    
    public String isErrorDisplayedForEmptyLogin() {
    	javaScriptUtil js = new javaScriptUtil(driver);
        return js.getValidateMessage(emailTxt);
    }

    public void logout() {
        click(logoutBtn);
    }
}
