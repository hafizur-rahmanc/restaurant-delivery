package cucumber_stepDefinition.location;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import res.cs.util.StringUrlPath;

public class LocationPageSteps {
	public static WebDriver driver;
	boolean loggedIn;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^User has item in cart$")
	public void user_has_item_in_cart() throws Throwable {
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
	    
	}

	@When("^User processes order$")
	public void user_processes_order() throws Throwable {
		// Find the process order button and click it
		driver.findElement(By.id("process")).click();  
	}

	@Then("^User should see a button for each Location$")
	public void user_should_see_a_button_for_each_Location() throws Throwable {
		WebElement locationBtn = driver.findElement(By.name("store"));
		assertThat(locationBtn.isDisplayed(), equalTo(true));
	}
	

	@When("^User selects a location$")
	public void user_selects_a_location() throws Throwable {
		// Find the select location button and click it
		driver.findElement(By.name("store")).click();
	}

	@Then("^User is sent to the review order page$")
	public void user_is_sent_to_the_review_order_page() throws Throwable {
		String actual = driver.getCurrentUrl();
		assertThat(actual, equalTo(StringUrlPath.ReviewOrderPage));
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
