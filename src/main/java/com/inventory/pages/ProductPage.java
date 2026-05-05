package com.inventory.pages;

import com.inventory.base.BasePage;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    private final By addProductBtn = By.xpath("//button[text()='Add Product']");
    private final By productNameTxt = By.xpath("(//input[contains(@class,'mt-1 block w-full rounded-md border-gray-300 shadow-sm')])[1]");
    private final By skuTxt = By.xpath("(//input[contains(@class,'mt-1 block w-full rounded-md border-gray-300 shadow-sm')])[2]");
    private final By priceTxt = By.xpath("(//input[contains(@class,'mt-1 block w-full rounded-md border-gray-300 shadow-sm')])[3]");
    private final By quantityTxt = By.xpath("(//input[contains(@class,'mt-1 block w-full rounded-md border-gray-300 shadow-sm')])[4]");

    private final By saveBtn = By.xpath("//button[text()='Create']");
    private final By updateBtn = By.xpath("//button[text()='Update']");
    private final By searchTxt = By.xpath("//input[contains(@class,'block w-full sm:w-64 rounded-md border-gray-300')]");

    private final By successMsg = By.xpath("//div[contains(text(),'successfully')]");
    private final By deleteMsg = By.xpath("//div[contains(text(),'delete')]");
    
    private final By rowsLocator = By.xpath("//table/tbody/tr");
    private final By nextBtnLocator = By.xpath("//button[contains(text(),'Next')]");

    private final By nameCol = By.xpath("./td[1]");
    private final By skuCol = By.xpath("./td[2]");
    private final By priceCol = By.xpath("./td[3]");
    private final By quantityCol = By.xpath("./td[4]");

    private final By editBtnInRow = By.xpath(".//button[contains(text(),'Edit')]");
    private final By deleteBtnInRow = By.xpath(".//button[contains(text(),'Delete')]");

	public ProductPage(WebDriver driver) {
		super(driver);
	}

	public void addProduct(String productName, String sku, String price, String quantity) {
		click(addProductBtn);
		type(productNameTxt, productName);
		type(skuTxt, sku);
		type(priceTxt, price);
		type(quantityTxt, quantity);
		click(saveBtn);
	}

	public void searchProduct(String keyword) {
		type(searchTxt, keyword);
	}

	public boolean isProductDisplayed(String productName, String sku, String quantity, String price) {

		waitForVisibility(rowsLocator);
		List<WebElement> rows = driver.findElements(rowsLocator);

		for (WebElement row : rows) {

			String nameText = row.findElement(nameCol).getText().trim();
			String skuText = row.findElement(skuCol).getText().trim();
			String priceText = row.findElement(priceCol).getText().replace("$", "").trim();
			String quantityText = row.findElement(quantityCol).getText().trim();

			if (nameText.equalsIgnoreCase(productName) && skuText.equalsIgnoreCase(sku)
					&& priceText.equalsIgnoreCase(price) && quantityText.equalsIgnoreCase(quantity)) {
				return true;
			}
		}

		return false;
	}

	public void editProduct(String productName, String updatedName, String newPrice, String newQuantity) {

		waitForVisibility(rowsLocator);
		List<WebElement> rows = driver.findElements(rowsLocator);

		for (WebElement row : rows) {

			String nameText = row.findElement(nameCol).getText().trim();

			if (nameText.equalsIgnoreCase(productName)) {

				row.findElement(editBtnInRow).click();

				waitForVisibility(productNameTxt);
				type(productNameTxt, updatedName);

				waitForVisibility(priceTxt);
				type(priceTxt, newPrice);

				waitForVisibility(quantityTxt);
				type(quantityTxt, newQuantity);

				click(updateBtn);
				return;
			}
		}

		throw new RuntimeException("Product not found: " + productName);
	}

	public void deleteSingleProduct(String productName) {

		waitForVisibility(rowsLocator);
		List<WebElement> rows = driver.findElements(rowsLocator);

		for (WebElement row : rows) {

			String nameText = row.findElement(nameCol).getText().trim();

			if (nameText.equalsIgnoreCase(productName)) {

				row.findElement(deleteBtnInRow).click();
				acceptAlert();
				wait.until(ExpectedConditions.stalenessOf(row));
				return;
			}
		}

		throw new RuntimeException("Product not found: " + productName);
	}

	public void deleteAllSampleProducts() {

		driver.navigate().refresh();

		while (true) {

			waitForVisibility(rowsLocator);
			List<WebElement> rows = driver.findElements(rowsLocator);

			boolean deleted = false;

			for (int i = 0; i < rows.size(); i++) {
				waitForVisibility(rowsLocator);
				rows = driver.findElements(rowsLocator);
				WebElement row = rows.get(i);

				String nameText = row.findElement(nameCol).getText().trim();

				if (nameText.contains("SampleProduct")) {
					waitForVisibility(deleteBtnInRow);
					WebElement deleteButton = row.findElement(deleteBtnInRow);
					waitForClickable(deleteBtnInRow);
					deleteButton.click();

					acceptAlert();

					wait.until(ExpectedConditions.stalenessOf(row));

					deleted = true;
					break;
				}
			}

			if (deleted) {
				continue;
			}

			WebElement nextBtn = waitForVisibility(nextBtnLocator);

			if (!nextBtn.isEnabled()) {
				break;
			}

			nextBtn.click();

			wait.until(ExpectedConditions.stalenessOf(rows.get(0)));
		}
	}

	public boolean isNextButtonDisabled() {
		WebElement nextBtn = waitForVisibility(nextBtnLocator);
		return !nextBtn.isEnabled();
	}

	public boolean isDeleteMessageDisplayed() {
		return isDisplayed(deleteMsg);
	}

	public boolean isSuccessMessageDisplayed() {
		return isDisplayed(successMsg);
	}
}