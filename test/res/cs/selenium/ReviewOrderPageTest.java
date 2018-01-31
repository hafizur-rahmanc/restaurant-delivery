package res.cs.selenium;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import res.cs.util.StringUrlPath;

public class ReviewOrderPageTest {
	WebDriver driver;
	boolean loggedIn;
	
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@BeforeMethod
	public void initialize() throws InterruptedException {
		// Create a new instance of the Google Chrome driver
		driver = new ChromeDriver();
		// Navigate to the home page
		driver.navigate().to(StringUrlPath.htmlRoot);
		// Implicitly Wait 10 seconds to load the page 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Set the window width to maximum 
		driver.manage().window().maximize();
		// Get the login link and click it
		driver.findElement(By.id("login-link")).click();
		// Get the userName and password element and fill out those fields with correct data
		driver.findElement(By.id("userName")).sendKeys("user");
		driver.findElement(By.id("Password")).sendKeys("user");
		// Click the login button
		driver.findElement(By.id("login")).click();
		loggedIn = true;
		// Click the add to cart button
		driver.findElement(By.name("item")).click();
		Thread.sleep(1000);
		// Switch to the active element in this case modal dialog
		driver.switchTo().activeElement();
		WebElement addItemBtn = driver.findElement(By.name("add-item"));
		Actions action = new Actions(driver);
		// Move to the element then click the add button to perform the action
		action.moveToElement(addItemBtn).click().perform();
		Thread.sleep(2000);
		// Add the second item to the cart
		driver.findElements(By.name("item")).get(2).click();
		Thread.sleep(3000);
		
		// Switch to the active element in this case modal dialog
		driver.switchTo().activeElement();
		addItemBtn = driver.findElements(By.name("add-item")).get(2);
		// Move to the element then click the add button to perform the action
		action.moveToElement(addItemBtn).click().perform();
		Thread.sleep(3000);

		// Find the process order button and click it
		driver.findElement(By.id("process")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("store")).click();
	}
	
	// Review Order page should be displayed correctly
	@Test
	public void reviewOrderPage() {
		String expected = StringUrlPath.ReviewOrderPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));
	}
	
	// Process order and cancel order should be displayed
	@Test
	public void processOrderCancelOrderDisplay() {
		WebElement processOrder = driver.findElement(By.id("process"));
		WebElement cancelOrder = driver.findElement(By.id("cancel"));
		assertThat(processOrder.isDisplayed(), equalTo(true));
		assertThat(cancelOrder.isDisplayed(), equalTo(true));
	}
	
	// Item name, description, image, price and a delete button should display
	@Test
	public void displayOrderSummary() {
		WebElement itemName = driver.findElement(By.id("item-name"));
		WebElement itemDescription = driver.findElement(By.id("item-desc"));
		WebElement itemImage = driver.findElement(By.className("img-rounded"));
		WebElement itemPrice = driver.findElement(By.id("item-price"));
		WebElement deleteBtn = driver.findElement(By.name("remove"));
		
		assertThat(itemName.isDisplayed(), equalTo(true));
		assertThat(itemDescription.isDisplayed(), equalTo(true));
		assertThat(itemImage.isDisplayed(), equalTo(true));
		assertThat(itemPrice.isDisplayed(), equalTo(true));
		assertThat(deleteBtn.isDisplayed(), equalTo(true));
	}
	
	// Remove an item from the cart
	@Test
	public void removeItemFromCart() {
		// Removed the second item from the cart
		driver.findElements(By.name("remove")).get(1).click();
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo("Item removed from the cart!"));
	}
	
	// Payment Information page
	@Test
	public void paymentInformation() {
		// Click the process order button
		driver.findElement(By.id("process")).click();
		String expected = StringUrlPath.PaymentPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));
	}
	
	// Cancel order should move to the menu item page
	@Test
	public void cancelOrderReturnToMenuItem() {
		// Click the cancel order button
		driver.findElement(By.id("cancel")).click();
		String expected = StringUrlPath.MenuItemPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));
	}
	@AfterMethod
	public void finalize() throws InterruptedException {
		if (loggedIn) {
			driver.findElement(By.id("logout")).click();
			Thread.sleep(1000);
		}
		driver.quit();
	}
}
