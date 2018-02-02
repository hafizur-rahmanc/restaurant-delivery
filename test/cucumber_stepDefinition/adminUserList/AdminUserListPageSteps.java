package cucumber_stepDefinition.adminUserList;

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

public class AdminUserListPageSteps {
	public static WebDriver driver;
	WebElement updateBtn;
	WebElement deleteBtn;
	boolean loggedIn;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}

	@Given("^Admin is on Admin User List page$")
	public void admin_is_on_Admin_User_List_page() throws Throwable {
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
	
	@Then("^Admin sees page header \"([^\"]*)\"$")
	public void admin_sees_page_header(String expected) throws Throwable {
		assertThat(driver.getTitle(), equalTo(expected));    
	}
	
	@Then("^Admin sees update and delete button$")
	public void admin_sees_update_and_delete_button() throws Throwable {
		updateBtn = driver.findElement(By.id("user-update"));
		deleteBtn = driver.findElement(By.name("delete"));
		
		assertThat(updateBtn.isDisplayed(), equalTo(true));
		assertThat(deleteBtn.isDisplayed(), equalTo(true));       
	}
	
	@When("^Admin clicks the update button of a user$")
	public void admin_clicks_the_update_button_of_a_user() throws Throwable {
		// Find the update button and click to get user page
		driver.findElement(By.id("user-update")).click();      
	}
	
	@Then("^Admin is send to the admin get user page$")
	public void admin_is_send_to_the_admin_get_user_page() throws Throwable {
		String expected = "User Account Info";
		assertThat(driver.getTitle(), equalTo(expected));   
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

