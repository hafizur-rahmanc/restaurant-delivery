package res.cs.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

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
}
