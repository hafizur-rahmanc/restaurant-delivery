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

public class OrderReceiptPageTest {
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
		// Navigate to the order receipt page
		driver.navigate().to(StringUrlPath.OrderReceiptPage);
	}
	
	// Order receipt page should be displayed correctly
	@Test
	public void orderReceiptPageTitle() {
		String expected = "Order Receipt";
		assertThat(driver.getTitle(), equalTo(expected));
	}
	
	// Receipt summary should be displayed correctly
	@Test
	public void receiptSummaryElements() {
		WebElement itemName = driver.findElement(By.id("item-name"));
		WebElement itemImage = driver.findElement(By.className("img-rounded"));
		WebElement itemPrice = driver.findElement(By.id("item-price"));
		
		assertThat(itemName.isDisplayed(), equalTo(true));
		assertThat(itemImage.isDisplayed(), equalTo(true));
		assertThat(itemPrice.isDisplayed(), equalTo(true));
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
