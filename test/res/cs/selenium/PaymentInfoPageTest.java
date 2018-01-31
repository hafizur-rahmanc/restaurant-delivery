package res.cs.selenium;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import res.cs.util.StringUrlPath;

public class PaymentInfoPageTest {
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
		// Click the add to cart button
		driver.findElement(By.name("item")).click();
		Thread.sleep(1000);
		// Switch to the active element in this case modal dialog
		driver.switchTo().activeElement();
		WebElement addItemBtn = driver.findElement(By.name("add-item"));
		Actions action = new Actions(driver);
		// Move to the element then click the add button to perform the action
		action.moveToElement(addItemBtn).click().perform();
		Thread.sleep(2000);

		// Find the process order button and click it
		driver.findElement(By.id("process")).click();
		// Select a store
		driver.findElement(By.name("store")).click();
		// Click to process the order
		driver.findElement(By.id("process")).click();
	}
	
	// Payment information page should be displayed correctly
	@Test
	public void paymentInformationPage() {
		String expected = StringUrlPath.PaymentPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));
	}
	
	@DataProvider
	public Object[][] invalidPaymentData(){
		Object[][] data = {
				{"11111111111111111111111111111111111111", "111", "11372", "One of the fields is not formatted correctly!"},
				{"11111111111111111", "11", "11372", "One of the fields is not formatted correctly!"},
				{"11111111111111111", "111", "1137", "One of the fields is not formatted correctly!"}
		};
		return data;	
	}
	
	// Invalid payment information
	@Test(dataProvider="invalidPaymentData")
	public void invalidPaymentInformation(String ccnumber, String scode, String zipcode, String expected) throws InterruptedException {
		driver.findElement(By.name("creditCard")).sendKeys(ccnumber);
		driver.findElement(By.name("secureCode")).sendKeys(scode);
		driver.findElement(By.name("zipcode")).sendKeys(zipcode);

		driver.findElement(By.name("process")).click();
		
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo(expected));
	}
	
	// Valid payment information data should process the order
	@Test
	public void validPaymentInformation() throws InterruptedException {
		driver.findElement(By.name("creditCard")).sendKeys("22222222222222222");
		driver.findElement(By.name("secureCode")).sendKeys("222");
		driver.findElement(By.name("zipcode")).sendKeys("11377");

		driver.findElement(By.name("process")).click();
		
		String expected = "Order Receipt";
		assertThat(driver.getTitle(), equalTo(expected));
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
