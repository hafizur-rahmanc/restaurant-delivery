package cucumber_stepDefinition.payment;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import res.cs.util.StringUrlPath;

public class PaymentPageSteps {
	public static WebDriver driver;
	boolean loggedIn;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^User confirmed order$")
	public void user_confirmed_order() throws Throwable {
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

	@When("^User enters credit card \"([^\"]*)\", security code \"([^\"]*)\", and zipcode \"([^\"]*)\"$")
	public void user_enters_credit_card_security_code_and_zipcode(String ccnumber, String scode, String zipcode) throws Throwable {
		driver.findElement(By.name("creditCard")).sendKeys(ccnumber);
		driver.findElement(By.name("secureCode")).sendKeys(scode);
		driver.findElement(By.name("zipcode")).sendKeys(zipcode);
	}

	@When("^User click the process button$")
	public void user_click_the_process_button() throws Throwable {
		driver.findElement(By.name("process")).click();   
	}

	@Then("^User sees alert message \"([^\"]*)\"$")
	public void user_sees_alert_message(String expected) throws Throwable {
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo(expected));
	}
	
	@After
	public void finalize(Scenario scenario) throws InterruptedException {
		if(scenario.isFailed()) {
			TakesScreenshot camera = (TakesScreenshot) driver;
            byte[] screenshot = camera.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
		if (loggedIn) {
			driver.findElement(By.id("logout")).click();
			Thread.sleep(1000);
		}
		driver.quit();
	}
}
