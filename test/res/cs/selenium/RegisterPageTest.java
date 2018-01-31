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

public class RegisterPageTest {
	WebDriver driver;
	WebElement firstNameEl;
	WebElement lastNameEl;
	WebElement userNameEl;
	WebElement passwordEl;
	WebElement rePasswordEl;
	WebElement genderEl;
	WebElement addressEl;
	WebElement phoneNumberEl;
	WebElement emailEl;
	WebElement loginBtn;
	WebElement registerBtn;
	
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@BeforeMethod
	public void initialize() {
		// Create a new instance of the Google Chrome driver
		driver = new ChromeDriver();
		// Navigate to the login page
		driver.navigate().to(StringUrlPath.RegistrationPage);
		// Implicitly Wait 10 seconds to load the page 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Set the window width to maximum 
		driver.manage().window().maximize();
		// Find the registration form elements
		firstNameEl = driver.findElement(By.id("firstName"));
		lastNameEl = driver.findElement(By.id("lastName"));
		// Get the userName and password element
		userNameEl = driver.findElement(By.id("userName"));
		passwordEl = driver.findElement(By.id("Password"));
		rePasswordEl = driver.findElement(By.name("repassword"));
		// Get the rest of the user input elements
		genderEl = driver.findElement(By.id("Male"));
		addressEl = driver.findElement(By.name("address"));
		phoneNumberEl = driver.findElement(By.name("phoneNumber"));
		emailEl = driver.findElement(By.name("email"));
		// Get the register button
		registerBtn = driver.findElement(By.id("register"));
	}
	
	// Page title should be User Details
	@Test
	public void registerPageTitle() {
		String pageTitle = driver.getTitle();
		assertThat(pageTitle, equalTo("User Details"));
	}
	
	// Valid registration data
	@Test
	public void validRegistration() {
		firstNameEl.sendKeys("Lance");
		lastNameEl.sendKeys("Luke");
		userNameEl.sendKeys("lance");
		passwordEl.sendKeys("lance");
		rePasswordEl.sendKeys("lance");
		genderEl.click();
		addressEl.sendKeys("138 Grand Concourse St");
		phoneNumberEl.sendKeys("1111111111");
		emailEl.sendKeys("lance@restaurant.org");
		
		registerBtn.click();
		assertThat(driver.getCurrentUrl(), equalTo(StringUrlPath.LoginPage));
	}
	
	// Unique user name test
	@Test
	public void uniqueUserName() {
		firstNameEl.sendKeys("Lance");
		lastNameEl.sendKeys("Luke");
		userNameEl.sendKeys("lance");
		passwordEl.sendKeys("lance");
		rePasswordEl.sendKeys("lance");
		genderEl.click();
		addressEl.sendKeys("138 Grand Concourse St");
		phoneNumberEl.sendKeys("1111111111");
		emailEl.sendKeys("lance@restaurant.org");
		
		registerBtn.click();
		
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo("User name already exists!"));
	}
	
	// Password and Re-enter Password mismatch
	@Test
	public void passwordMismatch() {
		firstNameEl.sendKeys("Lance");
		lastNameEl.sendKeys("Luke");
		userNameEl.sendKeys("lance");
		passwordEl.sendKeys("lance");
		rePasswordEl.sendKeys("lance123");
		genderEl.click();
		addressEl.sendKeys("138 Grand Concourse St");
		phoneNumberEl.sendKeys("1111111111");
		emailEl.sendKeys("lance@restaurant.org");
		
		registerBtn.click();
		
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo("Password does not match!"));
	}
	
	// Invalid registration data
	@Test
	public void invalidRegistration() {
		firstNameEl.sendKeys("Lance11111111111111111");
		lastNameEl.sendKeys("hello");
		userNameEl.sendKeys("lance123");
		passwordEl.sendKeys("lance");
		rePasswordEl.sendKeys("lance");
		genderEl.click();
		addressEl.sendKeys("138 Grand Concourse St");
		phoneNumberEl.sendKeys("1111111111");
		emailEl.sendKeys("lance@restaurant.org");
		
		registerBtn.click();
		
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo("One of the fields is not formatted correctly!"));
	}
	
	// Click login button to go to the login page
	@Test
	public void registerPageFromLogin() {
		loginBtn = driver.findElement(By.id("login"));
		loginBtn.click();
		assertThat(driver.getCurrentUrl(), equalTo(StringUrlPath.LoginPage));
	}
	
	@AfterMethod
	public void finalize() throws InterruptedException {
		Thread.sleep(1000);
		driver.quit();
	}
}
