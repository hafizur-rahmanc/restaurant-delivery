package res.cs.selenium;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.equalTo;
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

public class AdminUserListPageTest {
	WebDriver driver;
	WebElement updateBtn;
	WebElement deleteBtn;
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
		driver.findElement(By.id("user-list")).click();
	}
	
	// Check the correct admin user list page title
	@Test
	public void adminUserListPage() {
		String expected = "Regular Users List";
		assertThat(driver.getTitle(), equalTo(expected));
	}
	
	// Check the update and delete button
	@Test
	public void updateAndDeleteButton() {
		updateBtn = driver.findElement(By.id("user-update"));
		deleteBtn = driver.findElement(By.name("delete"));
		
		assertThat(updateBtn.isDisplayed(), equalTo(true));
		assertThat(deleteBtn.isDisplayed(), equalTo(true));
	}
	
	// Clicking the update button should display single user update page
	@Test
	public void adminGetUserPage() {
		// Find the update button and click to get user page
		driver.findElement(By.id("user-update")).click();
		String expected = "User Account Info";
		assertThat(driver.getTitle(), equalTo(expected));
	}
	
	// Clicking the delete button should delete the user from the list
	// Need to find a way to recover the deleted data
	@Test
	public void adminDeleteUser() {
		// Find the delete button and click to delete the last user
		List<WebElement> userList = driver.findElements(By.name("delete"));
		int size = userList.size();
		assertThat(userList, hasSize(size));
		
		// Delete the last user from the list
		userList.get(size - 1).click();
		assertThat(userList, hasSize(size - 1));
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
