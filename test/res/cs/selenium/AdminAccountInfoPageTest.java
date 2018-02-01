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

public class AdminAccountInfoPageTest {
	WebDriver driver;
	WebElement lastNameEl;
	WebElement passwordEl;
	WebElement rePasswordEl;
	WebElement phoneNumberEl;
	
	boolean loggedIn;
	boolean isUpdated;
	String oldPhoneNumber;
	
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
		isUpdated = false;
		
		// Find the Account Information link and click it
		driver.findElement(By.id("account-info")).click();
	}
	
	// Check the correct account information page title
	@Test
	public void accountInfoPage() {
		String expected = "Admin Account Details";
		assertThat(driver.getTitle(), equalTo(expected));
	}
	
	// Password and Re-enter Password mismatch
	@Test
	public void passwordMismatch() {
		passwordEl = driver.findElement(By.id("Password"));
		passwordEl.clear();
		passwordEl.sendKeys("user");
		rePasswordEl = driver.findElement(By.name("rePassword"));
		rePasswordEl.clear();
		rePasswordEl.sendKeys("admin123");
		
		driver.findElement(By.id("update")).click();
		
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo("Password does not match!"));
	}
	
	// Invalid account info field
	@Test
	public void invalidUpdateField() {
		lastNameEl = driver.findElement(By.id("lastName"));
		lastNameEl.clear();
		lastNameEl.sendKeys("admin123123-rt456");
		driver.findElement(By.id("update")).click();
		
		String actual = driver.findElement(By.id("message")).getText();
		String expected = "One of the fields is not formatted correctly!";
		
		assertThat(actual, equalTo(expected));
	}
	
	// Valid account information update
	@Test
	public void validAccountUpdate() throws InterruptedException {
		phoneNumberEl = driver.findElement(By.name("phoneNumber"));
		// Grab the old value
		oldPhoneNumber = phoneNumberEl.getAttribute("value");
		phoneNumberEl.clear();
		phoneNumberEl.sendKeys("4444444444");
		
		// Update the account information
		driver.findElement(By.id("update")).click();
		isUpdated = true;
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "User information updated successfully!";
		String expectedPhoneNumber = "4444444444";
		String actual = driver.findElement(By.name("phoneNumber")).getAttribute("value");
		
		assertThat(message, equalTo(expectedMessage));
		assertThat(actual, equalTo(expectedPhoneNumber));
	}
	
	@AfterMethod
	public void finalize() throws InterruptedException {
		if(isUpdated) {
			// Go back to the original phone number
			phoneNumberEl = driver.findElement(By.name("phoneNumber"));
			phoneNumberEl.clear();
			phoneNumberEl.sendKeys(oldPhoneNumber);
			driver.findElement(By.id("update")).click();
		}
		if (loggedIn) {
			driver.findElement(By.id("logout")).click();
			Thread.sleep(1000);
		}
		driver.quit();
	}

}
