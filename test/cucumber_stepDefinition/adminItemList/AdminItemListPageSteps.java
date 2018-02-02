package cucumber_stepDefinition.adminItemList;

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

public class AdminItemListPageSteps {
	public static WebDriver driver;
	WebElement itemNameEl;
	WebElement categoryEl;
	WebElement itemPriceEl;
	WebElement itemDescEl;
	WebElement itemImageEl;
	
	boolean loggedIn;
	boolean isUpdated;
	String oldCategory;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^Admin is on Admin Item List page$")
	public void admin_is_on_Admin_Item_List_page() throws Throwable {
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
		isUpdated = false;
		
		// Find the navigate application link and click it
		driver.findElement(By.id("nav-app")).click();
		// Click the item list link
		driver.findElement(By.id("item-list")).click();     
	}

	@Then("^Admin sees page header \"([^\"]*)\"$")
	public void admin_sees_page_header(String pageTitle) throws Throwable {
	   assertThat(driver.getTitle(), equalTo(pageTitle)); 
	}

	@Then("^Admin sees update and delete button$")
	public void admin_sees_update_and_delete_button() throws Throwable {
	   WebElement updateBtn = driver.findElement(By.name("update"));
	   WebElement deleteBtn = driver.findElement(By.name("delete"));
	   
	   assertThat(updateBtn.isDisplayed(), equalTo(true));
	   assertThat(deleteBtn.isDisplayed(), equalTo(true));
	}

	@When("^Admin enters invalid data to a field$")
	public void admin_enters_invalid_data_to_a_field() throws Throwable {
		itemNameEl = driver.findElement(By.id("itemName"));
		itemNameEl.clear();
		itemNameEl.sendKeys("wrong-item-name@");   
	}

	@When("^Admin clicks the update button$")
	public void admin_clicks_the_update_button() throws Throwable {
		driver.findElement(By.name("update")).click();   
	}

	@Then("^Admin sees alert message \"([^\"]*)\"$")
	public void admin_sees_alert_message(String expected) throws Throwable {
		String actual = driver.findElement(By.id("message")).getText();
		assertThat(actual, equalTo(expected));   
	}

	@When("^Admin updates account information successfully$")
	public void admin_updates_account_information_successfully() throws Throwable {
		categoryEl = driver.findElement(By.name("category"));
		// Grab the old value
		oldCategory = categoryEl.getAttribute("value");
		categoryEl.clear();
		categoryEl.sendKeys("BrandNewCategory");
		
		// Update the item category
		driver.findElement(By.name("update")).click();
		isUpdated = true;    
	}

	@Then("^Admin sees the updated information and alert message$")
	public void admin_sees_the_updated_information_and_alert_message() throws Throwable {
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Item updated successfully!";
		String expectedCategory = "BrandNewCategory";
		String actual = driver.findElement(By.name("category")).getAttribute("value");
		
		assertThat(message, equalTo(expectedMessage));
		assertThat(actual, equalTo(expectedCategory));    
	}

	@Then("^Add button should be displayed$")
	public void add_button_should_be_displayed() throws Throwable {
		WebElement addBtn = driver.findElement(By.name("create"));  
		assertThat(addBtn.isDisplayed(), equalTo(true));
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
			categoryEl = driver.findElement(By.name("category"));
			categoryEl.clear();
			categoryEl.sendKeys(oldCategory);
			driver.findElement(By.name("update")).click();
		}
		if (loggedIn) {
			driver.findElement(By.id("logout")).click();
			Thread.sleep(1000);
		}
		driver.quit();
	}
}
