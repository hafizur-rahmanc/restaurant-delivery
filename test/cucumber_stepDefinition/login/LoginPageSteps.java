package cucumber_stepDefinition.login;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import res.cs.util.StringUrlPath;

public class LoginPageSteps {
	public static WebDriver driver;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^User is on home page$")
	public void user_is_on_home_page() throws Throwable {
		// Create a new instance of the Google Chrome driver
		driver = new ChromeDriver();
		// Navigate to the login page
		driver.navigate().to(StringUrlPath.htmlRoot);
		// Implicitly Wait 10 seconds to load the page 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Set the window width to maximum 
		driver.manage().window().maximize();
	}

	@When("^User clicks on login link$")
	public void user_clicks_on_login_link() throws Throwable {
		// Click the login link
		driver.findElement(By.id("login-link")).click();   
	}

	@Then("^User sees text input field for username$")
	public void user_sees_text_input_field_for_username() throws Throwable {
		String type = driver.findElement(By.id("userName")).getAttribute("type");
		assertThat(type, equalTo("text"));
	    
	}

	@Then("^User sees masked input field for password$")
	public void user_sees_masked_input_field_for_password() throws Throwable {
		String type = driver.findElement(By.id("Password")).getAttribute("type");
		assertThat(type, equalTo("password"));
	    
	}

	@Then("^User sees login page header as \"([^\"]*)\"$")
	public void user_sees_login_page_header_as(String pageTitle) throws Throwable {
		String actual = driver.getTitle();
		assertThat(actual, equalTo(pageTitle));   
	}

	@When("^User logs in using valid username as \"([^\"]*)\" and password as \"([^\"]*)\"$")
	public void user_logs_in_using_valid_username_as_and_password_as(String userName, String password) throws Throwable {
		driver.findElement(By.id("userName")).sendKeys(userName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("login")).click();
	}

	@Then("^User is sent to menu item page$")
	public void user_is_sent_to_menu_item_page() throws Throwable {
		assertThat(driver.getCurrentUrl(), equalTo(StringUrlPath.MenuItemPage));  
	}

	@When("^User logs in as Admin using valid username as \"([^\"]*)\" and password as \"([^\"]*)\"$")
	public void user_logs_in_as_Admin_using_valid_username_as_and_password_as(String userName, String password) throws Throwable {
		driver.findElement(By.id("userName")).sendKeys(userName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("login")).click();
	}
	
	@Then("^User is sent to admin home page$")
	public void user_is_sent_to_admin_home_page() throws Throwable {
		assertThat(driver.getCurrentUrl(), equalTo(StringUrlPath.htmlAdminRoot));
	    
	}
	
	@When("^User logs in using invalid username as \"([^\"]*)\" and password as \"([^\"]*)\"$")
	public void user_logs_in_using_invalid_username_as_and_password_as(String userName, String password) throws Throwable {
		driver.findElement(By.id("userName")).sendKeys(userName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("login")).click();
	}
	
	@Then("^User should see error message as \"([^\"]*)\"$")
	public void user_should_see_error_message_as(String expectedMessage) throws Throwable {
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo(expectedMessage));
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
