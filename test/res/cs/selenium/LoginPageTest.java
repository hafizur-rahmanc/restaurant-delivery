package res.cs.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import res.cs.util.StringUrlPath;

public class LoginPageTest {
	WebDriver driver;
	WebElement userNameEl;
	WebElement passwordEl;
	WebElement loginBtn;
	WebElement registerLink;
	
	boolean loggedIn;
	
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	@BeforeMethod
	public void initialize() {
		// Create a new instance of the Google Chrome driver
		driver = new ChromeDriver();
		// Navigate to the login page
		driver.navigate().to(StringUrlPath.LoginPage);
		// Implicitly Wait 10 seconds to load the page 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Set the window width to maximum 
		driver.manage().window().maximize();
		// Get the userName and password element
		userNameEl = driver.findElement(By.id("userName"));
		passwordEl = driver.findElement(By.id("Password"));
		// Get the login button
		loginBtn = driver.findElement(By.id("login"));
		
		loggedIn = false;
	}
	
	@DataProvider
	public Object[][] loginData(){
		Object[][] data = {
				{"user", "user", true},
				{"hafiz_ny","password123", true},
				{"ruhul","dipu12", false}
		};
		return data;	
	}
	
	// Login as regular user
	@Test(dataProvider="loginData")
	public void loginTest(String userName, String password, boolean expected) throws InterruptedException {
		userNameEl.sendKeys(userName);
		passwordEl.sendKeys(password);
		loginBtn.click();
		Thread.sleep(1000);
		if(expected) {
			loggedIn = true;
			assertThat(driver.getCurrentUrl(), equalTo(StringUrlPath.MenuItemPage));
		} else {
			String message = driver.findElement(By.id("message")).getText();
			assertThat(message, equalTo("Invalid username or password!"));
		}
	}
	
	
	// Invalid username and password format
	@Test
	public void invalidFormat() throws InterruptedException {
		userNameEl.sendKeys("xxxxxxxxxxxxxxxxx11111111111111");
		passwordEl.sendKeys("password");
		loginBtn.click();
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo("Invalid username or password format!"));
	}
	@DataProvider
	public Object[][] adminLoginData(){
		Object[][] data = {
				{"admin", "admin", true},
				{"admin","admin1", false}
		};
		return data;	
	}
	
	// Login as Admin
	@Test(dataProvider="adminLoginData")
	public void loginAsAdmin(String userName, String password, boolean expected) throws InterruptedException {
		userNameEl.sendKeys(userName);
		passwordEl.sendKeys(password);
		loginBtn.click();
		if(expected) {
			loggedIn = true;
			assertThat(driver.getCurrentUrl(), equalTo(StringUrlPath.htmlAdminRoot));
		} else {
			String message = driver.findElement(By.id("message")).getText();
			assertThat(message, equalTo("Invalid username or password!"));
		}
	}
	
	// Click Register button to go to the register page
	@Test
	public void registerPageFromLogin() {
		registerLink = driver.findElement(By.id("register"));
		registerLink.click();
		assertThat(driver.getCurrentUrl(), equalTo(StringUrlPath.RegistrationPage));
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
