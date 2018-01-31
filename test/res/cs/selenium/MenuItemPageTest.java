package res.cs.selenium;

import java.util.List;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import res.cs.util.StringUrlPath;

public class MenuItemPageTest {
	WebDriver driver;
	WebElement userNameEl;
	WebElement passwordEl;
	WebElement loginBtn;
	WebElement processBtn;
	WebElement addItemBtn;
	WebElement addToCartBtn;
	
	boolean loggedIn;
	
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@BeforeMethod
	public void initialize() {
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
		// Get the login link and click it
		driver.findElement(By.id("login")).click();
		loggedIn = true;
	}
	
	// After successful login user should be able to see menu item page
	@Test
	public void menuItemPage() {
		String expected = StringUrlPath.MenuItemPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));
	}
	
	// User should be able to add item to the cart and then see the process order button
	@Test
	public void addItemToCart() throws InterruptedException {
		// Click the add to cart button
		driver.findElement(By.name("item")).click();
		Thread.sleep(1000);
		// Switch to the active element in this case modal dialog
		driver.switchTo().activeElement();
		// Click the add button
		driver.findElement(By.name("add-item")).click();
		Thread.sleep(1000);
		
		// Find the process order button
		processBtn = driver.findElement(By.id("process"));
		// Check the process order button
		assertThat(processBtn.isDisplayed(), equalTo(true));
	}
	
	// Add more than one items to the cart
	@Test
	public void addMoreItemsToCart() throws InterruptedException {
		// Add the first item to the cart
		driver.findElement(By.name("item")).click();
		Thread.sleep(1000);
		// Switch to the active element in this case modal dialog
		driver.switchTo().activeElement();
		addItemBtn = driver.findElement(By.name("add-item"));
		Actions action = new Actions(driver);
		// Move to the element then click the add button to perform the action
		action.moveToElement(addItemBtn).click().perform();
		Thread.sleep(1000);
		
		// Add the second item to the cart
		driver.findElements(By.name("item")).get(2).click();
		Thread.sleep(1000);
		
		// Switch to the active element in this case modal dialog
		driver.switchTo().activeElement();
		addItemBtn = driver.findElements(By.name("add-item")).get(2);;
		// Move to the element then click the add button to perform the action
		action.moveToElement(addItemBtn).click().perform();
		Thread.sleep(1000);
		
		// Find the process order button
		processBtn = driver.findElement(By.id("process"));
		// Check the process order button
		assertThat(processBtn.isDisplayed(), equalTo(true));
	}
	
	// After clicking the Process order button location selection page should appear
	@Test
	public void locationSelectionPage() throws InterruptedException {
		// Add the first item to the cart
		driver.findElement(By.name("item")).click();
		Thread.sleep(1000);
		// Switch to the active element in this case modal dialog
		driver.switchTo().activeElement();
		addItemBtn = driver.findElement(By.name("add-item"));
		Actions action = new Actions(driver);
		// Move to the element then click the add button to perform the action
		action.moveToElement(addItemBtn).click().perform();
		Thread.sleep(1000);
		
		// Click the process order button
		driver.findElement(By.id("process")).click();
		String expected = StringUrlPath.LocationPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));
	}
	
	// Only logged in users can see the add to cart button
	@Test
	public void notLoggedInUsers() throws InterruptedException {
		if (loggedIn) {
			driver.findElement(By.id("logout")).click();
			loggedIn = false;
		}
		// Click the menu-link from the nav-bar
		driver.findElement(By.id("menu-link")).click();
		// Add to cart should not display
		List<WebElement> elements = driver.findElements(By.name("item")); 
		assertThat(elements, hasSize(0));
	}
	
	// Menu Item page has item review button
	@Test
	public void reviewButton() {
		// Find the review button
		WebElement reviewBtn = driver.findElement(By.name("reviews"));
		// Check the review button is displayed or not
		assertThat(reviewBtn.isDisplayed(), equalTo(true));
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
