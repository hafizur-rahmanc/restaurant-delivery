package res.cs.selenium;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import res.cs.util.StringUrlPath;

public class OrderHistoryPageTest {
	WebDriver driver;
	WebElement viewOrdersLink;
	
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
		// Get the view orders link
		viewOrdersLink = driver.findElement(By.id("orders-link"));
	}
	
	// After successful login views orders link should be displayed
	@Test
	public void viewOrdersLink() {
		assertThat(viewOrdersLink.isDisplayed(), equalTo(true));
	}
	
	// Verify the correct orders history page title
	@Test
	public void ordersHistoryPage() {
		viewOrdersLink.click();
		String expected = "Order's History";
		assertThat(driver.getTitle(), equalTo(expected));
	}
	
	// Orders history page elements 
	@Test
	public void orderHistoryPageElements() {
		viewOrdersLink.click();
		WebElement itemName = driver.findElement(By.className("item-name"));
		WebElement itemImage = driver.findElement(By.className("img-rounded"));
		WebElement itemPrice = driver.findElement(By.className("item-price"));
		
		assertThat(itemName.isDisplayed(), equalTo(true));
		assertThat(itemImage.isDisplayed(), equalTo(true));
		assertThat(itemPrice.isDisplayed(), equalTo(true));
	}
	
	@AfterMethod
	public void finalize() throws InterruptedException {
		driver.findElement(By.id("logout")).click();
		Thread.sleep(1000);
		driver.quit();
	}
}
