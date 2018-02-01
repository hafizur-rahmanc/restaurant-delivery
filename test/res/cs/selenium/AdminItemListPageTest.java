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

public class AdminItemListPageTest {
	WebDriver driver;
	WebElement itemNameEl;
	WebElement categoryEl;
	WebElement itemPriceEl;
	WebElement itemDescEl;
	WebElement itemImageEl;
	
	boolean loggedIn;
	boolean isUpdated;
	boolean isCreated;
	String oldCategory;
	
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
		// Click the users list link
		driver.findElement(By.id("item-list")).click();
	}
	
	// Invalid account info field
	@Test
	public void invalidUpdateField() {
		itemNameEl = driver.findElement(By.id("itemName"));
		itemNameEl.clear();
		itemNameEl.sendKeys("wrong-item-name@");
		driver.findElement(By.name("update")).click();
		
		String actual = driver.findElement(By.id("message")).getText();
		String expected = "One of the fields is not formatted correctly!";
		
		assertThat(actual, equalTo(expected));
	}
	
	// Valid item category update
	@Test
	public void validItemUpdate() throws InterruptedException {
		categoryEl = driver.findElement(By.name("category"));
		// Grab the old value
		oldCategory = categoryEl.getAttribute("value");
		categoryEl.clear();
		categoryEl.sendKeys("BrandNewCategory");
		
		// Update the item category
		driver.findElement(By.name("update")).click();
		isUpdated = true;
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Item updated successfully!";
		String expectedCategory = "BrandNewCategory";
		String actual = driver.findElement(By.name("category")).getAttribute("value");
		
		assertThat(message, equalTo(expectedMessage));
		assertThat(actual, equalTo(expectedCategory));
	}
	
	// Create a new item
	@Test
	public void adminAddItem() {
		List<WebElement> itemList = driver.findElements(By.name("update"));
		int size = itemList.size();
		
		itemNameEl = driver.findElements(By.id("itemName")).get(size);
		itemNameEl.clear();
		itemNameEl.sendKeys("New item");
		
		itemPriceEl = driver.findElements(By.id("itemPrice")).get(driver.findElements(By.id("itemPrice")).size() - 1);
		itemPriceEl.clear();
		itemPriceEl.sendKeys("13.99");
		
		itemDescEl = driver.findElements(By.id("itemDescription")).get(driver.findElements(By.id("itemDescription")).size() - 1);
		itemDescEl.clear();
		itemDescEl.sendKeys("This is our newest item of this new year!");
		
		itemImageEl = driver.findElements(By.id("itemImage")).get( driver.findElements(By.id("itemImage")).size() - 1);
		itemImageEl.clear();
		itemImageEl.sendKeys("shrimp.jpg");
		
		driver.findElements(By.id("active")).get(driver.findElements(By.id("active")).size() - 1).click();
		
		categoryEl = driver.findElements(By.name("category")).get(driver.findElements(By.name("category")).size() - 1);
		categoryEl.clear();
		categoryEl.sendKeys("Brand New Category");
		
		driver.findElements(By.name("create")).get(driver.findElements(By.name("create")).size() - 1).click();
		isCreated = true;
		
		itemList = driver.findElements(By.name("update"));
		assertThat(itemList, hasSize(size + 1));
		
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Item craeted successfully!";
		assertThat(message, equalTo(expectedMessage));
	}
	
	// Delete an item
	@Test
	public void AdminDeleteItem() {
		// Find the delete button and click to delete the last created item
		List<WebElement> itemList = driver.findElements(By.name("delete"));
		int size = itemList.size();
		assertThat(itemList, hasSize(size));
		
		// Delete the last review
		itemList.get(0).click();
		itemList = driver.findElements(By.name("delete"));
		assertThat(itemList, hasSize(size - 1));
		
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Item deleted successfully!";
		assertThat(message, equalTo(expectedMessage));
	}
	
	@AfterMethod
	public void finalize() throws InterruptedException {
		if(isCreated){
			driver.findElement(By.name("delete")).click();
		}
		if(isUpdated) {
			// Go back to the original phone number
			categoryEl = driver.findElement(By.name("category"));
			categoryEl.clear();
			categoryEl.sendKeys(oldCategory);
			driver.findElement(By.name("update")).click();
		}
		if (loggedIn) {
			driver.findElement(By.id("logout")).click();
			Thread.sleep(1000);
		}
		driver.quit();
	}
	
}
