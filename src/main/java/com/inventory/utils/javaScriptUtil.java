package com.inventory.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class javaScriptUtil {
	
	private WebDriver driver;
	private JavascriptExecutor js;
	
	public javaScriptUtil(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
	}
	
	public String getValidateMessage(By locator) {
		WebElement element = driver.findElement(locator);
		//return (String) js.executeScript("return arguments[0].validationMessage;", element);
		
		return (String)((JavascriptExecutor)driver).executeScript("return arguments[0].validationMessage;", element);
	}
	
	public void clickElement(WebElement element) {
		js.executeScript("arguments[0].click()", element);
	}
	
    public void scrollToElement(WebElement element) {
    	js.executeScript("arguments[0].scrollTntoView(true)", element);
    }

}
