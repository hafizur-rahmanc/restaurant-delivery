package cucumber_stepDefinition.accountInfo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import res.cs.util.StringUrlPath;

public class AccountInfoPageSteps {
	public static WebDriver driver;
	WebElement emailEl;
	WebElement lastNameEl;
	WebElement passwordEl;
	WebElement rePasswordEl;
	WebElement phoneNumberEl;
	
	boolean loggedIn;
	boolean isUpdated;
	String oldPhoneNumber;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^User is on account details page$")
	public void user_is_on_account_details_page() throws Throwable {
		// Create a new instance of the Google Chrome driver
		driver = new ChromeDriver();
		// Navigate to the login page
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
		isUpdated = false;
		
		// Find the Account Information link and click it
		driver.findElement(By.id("account-info")).click();
	}

	@When("^User clears the email field$")
	public void user_clears_the_email_field() throws Throwable {
	  emailEl =  driver.findElement(By.name("email"));
	  emailEl.clear();
	}

	@When("^User clicks the update button$")
	public void user_clicks_the_update_button() throws Throwable {
		driver.findElement(By.id("update")).click();
	}

	@Then("^User sees Missing Credentials Message$")
	public void user_sees_Missing_Credentials_Message() throws Throwable {
		String validator = "Please fill out this field.";
		String result = emailEl.getAttribute("validationMessage");
		assertThat(result, equalTo(validator));  
	}

	@When("^User enters different password or re-enter password value$")
	public void user_enters_different_password_or_re_enter_password_value() throws Throwable {
		passwordEl = driver.findElement(By.id("Password"));
		passwordEl.clear();
		passwordEl.sendKeys("user");
		rePasswordEl = driver.findElement(By.name("repassword"));
		rePasswordEl.clear();
		rePasswordEl.sendKeys("user123");
	}

	@Then("^User sees that \"([^\"]*)\"$")
	public void user_sees_that(String expectedMessage) throws Throwable {
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo(expectedMessage));
	}

	@When("^User enters invalid data to a field$")
	public void user_enters_invalid_data_to_a_field() throws Throwable {
		lastNameEl = driver.findElement(By.id("lastName"));
		lastNameEl.clear();
		lastNameEl.sendKeys("user123123123-rt456");
	}

	@Then("^User sees alert message \"([^\"]*)\"$")
	public void user_sees_alert_message(String expectedMessage) throws Throwable {
		String actual = driver.findElement(By.id("message")).getText();
		
		assertThat(actual, equalTo(expectedMessage));    
	}

	@When("^User updates account information successfully$")
	public void user_updates_account_information_successfully() throws Throwable {
		phoneNumberEl = driver.findElement(By.name("phoneNumber"));
		// Grab the old value
		oldPhoneNumber = phoneNumberEl.getAttribute("value");
		phoneNumberEl.clear();
		phoneNumberEl.sendKeys("4444444444");
		// Update the account information
		driver.findElement(By.id("update")).click();
		isUpdated = true;   
	}

	@Then("^User sees the updated information and alert message$")
	public void user_sees_the_updated_information_and_alert_message() throws Throwable {
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "User information updated successfully!";
		String expectedPhoneNumber = "4444444444";
		String actual = driver.findElement(By.name("phoneNumber")).getAttribute("value");
		
		assertThat(message, equalTo(expectedMessage));
		assertThat(actual, equalTo(expectedPhoneNumber));    
	}
	
	@After
	public void finalize(Scenario scenario) throws InterruptedException {
		if(scenario.isFailed()) {
			TakesScreenshot camera = (TakesScreenshot) driver;
            byte[] screenshot = camera.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
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
