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

public class LocationPageTest {
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
		// Get the login link and click it
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

		// Find the process order button and click it
		driver.findElement(By.id("process")).click();
		Thread.sleep(1000);
	}
	
	// Location page should be displayed correctly
	@Test
	public void locationPage() {
		String expected = StringUrlPath.LocationPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));
	}
	
	// Location page should display cancel order button
	@Test
	public void displayCancelOrderButton() {
		WebElement cancelOrder = driver.findElement(By.id("cancel"));
		assertThat(cancelOrder.isDisplayed(), equalTo(true));
	}
	
	// Select a location button should be displayed
	@Test
	public void selectLocationButton() {
		WebElement storeEl = driver.findElement(By.name("store"));
		assertThat(storeEl.isDisplayed(), equalTo(true));
	}
	
	// After selecting a location, review order page should be displayed
	@Test
	public void reviewOrderPage() {
		driver.findElement(By.name("store")).click();
		String actual = driver.getCurrentUrl();
		assertThat(actual, equalTo(StringUrlPath.ReviewOrderPage));
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
