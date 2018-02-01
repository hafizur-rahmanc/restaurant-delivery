package res.cs.selenium;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import res.cs.util.StringUrlPath;

public class AdminOrdersListPageTest {
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
		driver.findElement(By.id("userName")).sendKeys("admin");
		driver.findElement(By.id("Password")).sendKeys("admin");
		// Click the login button
		driver.findElement(By.id("login")).click();
		loggedIn = true;
		
		// Find the navigate application link and click it
		driver.findElement(By.id("nav-app")).click();
		// Click the users list link
		driver.findElement(By.id("order-list")).click();
	}
	
	// Check the correct user account information page title
	@Test
	public void ordersListPage() {
		String expected = "Order's List";
		assertThat(driver.getTitle(), equalTo(expected));
	}
	
	// Admin can delete an order
	@Test
	public void AdminDeleteOrder() throws InterruptedException {
		List<WebElement> ordersList = driver.findElements(By.id("delete-order"));
		int size = ordersList.size();
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Actions action = new Actions(driver);
		
		Thread.sleep(1000);
		// Move to the element then click the delete button to perform the action
		action.moveToElement(ordersList.get(size - 1)).click().perform();
		ordersList = driver.findElements(By.id("delete-order"));
		assertThat(ordersList, hasSize(size - 1));
		
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Order deleted successfully!";
		assertThat(message, equalTo(expectedMessage));	
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
