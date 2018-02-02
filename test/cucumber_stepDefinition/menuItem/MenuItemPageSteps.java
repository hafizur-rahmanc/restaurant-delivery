package cucumber_stepDefinition.menuItem;

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
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import res.cs.util.StringUrlPath;

public class MenuItemPageSteps {
	public static WebDriver driver;
	WebElement userNameEl;
	WebElement passwordEl;
	WebElement loginBtn;
	WebElement processBtn;
	WebElement addItemBtn;
	WebElement addToCartBtn;
	
	boolean loggedIn;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^User is logged in$")
	public void user_is_logged_in() throws Throwable {
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
		// Get the login link and click it
		driver.findElement(By.id("login")).click();
		loggedIn = true;
	}

	@When("^User navigates to menu item page$")
	public void user_navigates_to_menu_item_page() throws Throwable {
		driver.navigate().to(StringUrlPath.MenuItemPage);
	}

	@Then("^User sees logout link$")
	public void user_sees_logout_link() throws Throwable {
		WebElement logoutLink = driver.findElement(By.id("logout"));
		assertThat(logoutLink.isDisplayed(), equalTo(true)); 
	}

	@When("^User adds item to cart$")
	public void user_adds_item_to_cart() throws Throwable {
		// Click the add to cart button
		driver.findElement(By.name("item")).click();
		Thread.sleep(1000); 
	}

	@When("^User clicks add button from the modal to confirm purchase$")
	public void user_clicks_add_button_from_the_modal_to_confirm_purchase() throws Throwable {
		// Switch to the active element in this case modal dialog
		driver.switchTo().activeElement();
		addItemBtn = driver.findElement(By.name("add-item"));
		Actions action = new Actions(driver);
		// Move to the element then click the add button to perform the action
		action.moveToElement(addItemBtn).click().perform();
		Thread.sleep(1000); 
	}

	@Then("^User sees process order button$")
	public void user_sees_process_order_button() throws Throwable {
		// Find the process order button
		processBtn = driver.findElement(By.id("process"));
		// Check the process order button
		assertThat(processBtn.isDisplayed(), equalTo(true));
	   
	}

	@When("^User clicks process order button$")
	public void user_clicks_process_order_button() throws Throwable {
		driver.findElement(By.id("process")).click();
	}

	@Then("^User is sent to locations page$")
	public void user_is_sent_to_locations_page() throws Throwable {
		String expected = StringUrlPath.LocationPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));
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
