package res.cs.selenium;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;
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

public class AdminLocationsListPageTest {
	WebDriver driver;
	WebElement addressEl;
	WebElement locationNameEl;
	WebElement zipcodeEl;
	WebElement imageEl;
	WebElement cityEl;
	WebElement staffNumberEl;
	
	boolean loggedIn;
	boolean isUpdated;
	boolean isCreated;
	String oldAddress;
	
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
		isCreated = false;
		
		// Find the navigate application link and click it
		driver.findElement(By.id("nav-app")).click();
		// Click the location list link
		driver.findElement(By.id("location-list")).click();
	}
	
	//  Check the correct locations list page title
	@Test
	public void locationPageTitle() {
		String expected = "Locations List";
		assertThat(driver.getTitle(), equalTo(expected));
	}
	
	// Successful location update
	@Test
	public void AdminLocationUpdate() {
		addressEl = driver.findElement(By.id("storeAddress"));
		// Grab the old value
		oldAddress = addressEl.getAttribute("value");
		addressEl.clear();
		addressEl.sendKeys("200 Hudson St");
		
		// Update the item category
		driver.findElement(By.name("update")).click();
		isUpdated = true;
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Location updated successfully!";
		String expectedCategory = "200 Hudson St";
		String actual = driver.findElement(By.id("storeAddress")).getAttribute("value");
		
		assertThat(message, equalTo(expectedMessage));
		assertThat(actual, equalTo(expectedCategory));
	}
	
	// Create a new location
	@Test
	public void adminAddLocation() {
		List<WebElement> locationList = driver.findElements(By.name("update"));
		int size = locationList.size();
		
		locationNameEl = driver.findElements(By.id("storeName")).get(size);
		locationNameEl.clear();
		locationNameEl.sendKeys("New Location");
		
		addressEl = driver.findElement(By.id("address"));
		addressEl.clear();
		addressEl.sendKeys("200 Hudson St");
		
		cityEl = driver.findElement(By.id("city"));
		cityEl.clear();
		cityEl.sendKeys("New York");
		
		staffNumberEl = driver.findElements(By.id("staffNumber")).get(size);
		staffNumberEl.clear();
		staffNumberEl.sendKeys("50");
		
		zipcodeEl = driver.findElement(By.id("zipcode"));
		zipcodeEl.clear();
		zipcodeEl.sendKeys("10004");
		
		imageEl = driver.findElement(By.id("image"));
		imageEl.clear();
		imageEl.sendKeys("pizzahutt.jpg");
		
		driver.findElement(By.name("create")).click();
		isCreated = true;
		
		locationList = driver.findElements(By.name("update"));
		assertThat(locationList, hasSize(size + 1));
		
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Location created successfully!";
		assertThat(message, equalTo(expectedMessage));
	}
	
	// Delete a location
	@Test
	public void AdminDeleteLocation() {
		// Find the delete button and click to delete the last created location
		List<WebElement> locationList = driver.findElements(By.name("delete"));
		int size = locationList.size();
		
		// Delete the last review
		locationList.get(0).click();
		locationList = driver.findElements(By.name("delete"));
		assertThat(locationList, hasSize(size - 1));
		
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Location deleted successfully!";
		assertThat(message, equalTo(expectedMessage));
	}
	
	
	@AfterMethod
	public void finalize() throws InterruptedException {
		if(isCreated){
			driver.findElement(By.name("delete")).click();
		}
		if(isUpdated) {
			// Go back to the original address
			addressEl = driver.findElement(By.id("storeAddress"));
			addressEl.clear();
			addressEl.sendKeys(oldAddress);
			driver.findElement(By.name("update")).click();
		}
		if (loggedIn) {
			driver.findElement(By.id("logout")).click();
			Thread.sleep(1000);
		}
		driver.quit();
	}
}
