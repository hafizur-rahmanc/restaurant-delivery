package cucumber_stepDefinition.registration;

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

public class RegistrationPageSteps {
	public static WebDriver driver;
	WebElement firstNameEl;
	WebElement lastNameEl;
	WebElement userNameEl;
	WebElement passwordEl;
	WebElement rePasswordEl;
	WebElement genderEl;
	WebElement addressEl;
	WebElement phoneNumberEl;
	WebElement emailEl;
	WebElement registerBtn;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^User is on Registration page$")
	public void user_is_on_Registration_page() throws Throwable {
		// Create a new instance of the Google Chrome driver
		driver = new ChromeDriver();
		// Navigate to the login page
		driver.navigate().to(StringUrlPath.htmlRoot);
		// Implicitly Wait 10 seconds to load the page 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Set the window width to maximum 
		driver.manage().window().maximize();
		// Click the register
		driver.findElement(By.id("register-link")).click();
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

	@When("^User forgets to enter email field$")
	public void user_forgets_to_enter_email_field() throws Throwable {
		firstNameEl.sendKeys("Lance");
		lastNameEl.sendKeys("Luke");
		userNameEl.sendKeys("lance");
		passwordEl.sendKeys("lance");
		rePasswordEl.sendKeys("lance123");
		genderEl.click();
		addressEl.sendKeys("138 Grand Concourse St");
		phoneNumberEl.sendKeys("1111111111");
		
		registerBtn.click();  
	}

	@Then("^User sees Missing Credentials Message$")
	public void user_sees_Missing_Credentials_Message() throws Throwable {
		String validator = "Please fill out this field.";
		String result = emailEl.getAttribute("validationMessage");
		assertThat(result, equalTo(validator));
	    
	}

	@When("^User enters different password and re-enter password value$")
	public void user_enters_different_password_and_re_enter_password_value() throws Throwable {
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
	}

	@Then("^User sees that \"([^\"]*)\"$")
	public void user_sees_that(String expectedMessage) throws Throwable {
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo(expectedMessage));
	    
	}

	@When("^User enters invalid data to a field$")
	public void user_enters_invalid_data_to_a_field() throws Throwable {
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
	}

	@Then("^User sees alert message \"([^\"]*)\"$")
	public void user_sees_alert_message(String expectedMessage) throws Throwable {
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo(expectedMessage));
	    
	}

	@Then("^User sees Registration Page Header \"([^\"]*)\"$")
	public void user_sees_Registration_Page_Header(String pageHeader) throws Throwable {
		String pageTitle = driver.getTitle();
		assertThat(pageTitle, equalTo(pageHeader));
	    
	}

	@When("^User successfully registers$")
	public void user_successfully_registers() throws Throwable {
		firstNameEl.sendKeys("Lance");
		lastNameEl.sendKeys("Luke");
		userNameEl.sendKeys("lance12");
		passwordEl.sendKeys("lance");
		rePasswordEl.sendKeys("lance");
		genderEl.click();
		addressEl.sendKeys("138 Grand Concourse St");
		phoneNumberEl.sendKeys("1111111111");
		emailEl.sendKeys("lance@restaurant.org");
		
		registerBtn.click();	    
	}

	@Then("^User is redirected to Login Page$")
	public void user_is_redirected_to_Login_Page() throws Throwable {
		assertThat(driver.getCurrentUrl(), equalTo(StringUrlPath.LoginPage)); 
	}
	
	@After
	public void finalize(Scenario scenario) throws InterruptedException {
		if(scenario.isFailed()) {
			TakesScreenshot camera = (TakesScreenshot) driver;
            byte[] screenshot = camera.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
		driver.findElement(By.id("logout")).click();
		Thread.sleep(1000);
		driver.quit();
	}
}
