package cucumber_stepDefinition.reviewOrder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

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

public class ReviewOrderPageSteps {
	public static WebDriver driver;
	boolean loggedIn;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^User has multiple items in their Cart$")
	public void user_has_multiple_items_in_their_Cart() throws Throwable {
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
		// Add the second item to the cart
		driver.findElements(By.name("item")).get(2).click();
		Thread.sleep(3000);
		
		// Switch to the active element in this case modal dialog
		driver.switchTo().activeElement();
		addItemBtn = driver.findElements(By.name("add-item")).get(2);
		// Move to the element then click the add button to perform the action
		action.moveToElement(addItemBtn).click().perform(); 
	    
	}

	@When("^User selects a location$")
	public void user_selects_a_location() throws Throwable {
		// Find the process order button and click it
		driver.findElement(By.id("process")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("store")).click();
	    
	}

	@Then("^User sees the image, name and price of each item in their cart$")
	public void user_sees_the_image_name_and_price_of_each_item_in_their_cart() throws Throwable {
		WebElement itemName = driver.findElement(By.id("item-name"));
		WebElement itemImage = driver.findElement(By.className("img-rounded"));
		WebElement itemPrice = driver.findElement(By.id("item-price"));
		
		assertThat(itemName.isDisplayed(), equalTo(true));
		assertThat(itemImage.isDisplayed(), equalTo(true));
		assertThat(itemPrice.isDisplayed(), equalTo(true));  
	}

	@Then("^User sees delete button for each item in their cart$")
	public void user_sees_delete_button_for_each_item_in_their_cart() throws Throwable { 
		WebElement deleteBtn = driver.findElement(By.name("remove"));
		assertThat(deleteBtn.isDisplayed(), equalTo(true));
	}

	@When("^User clicks the process order button$")
	public void user_clicks_the_process_order_button() throws Throwable {
		driver.findElement(By.id("process")).click();   
	}

	@Then("^User is sent to payment Page$")
	public void user_is_sent_to_payment_Page() throws Throwable {
		String expected = StringUrlPath.PaymentPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));   
	}

	@When("^User cancels their purchase$")
	public void user_cancels_their_purchase() throws Throwable {
		// Click the cancel order button
		driver.findElement(By.id("cancel")).click(); 
	}

	@Then("^User is sent back to Menu Page$")
	public void user_is_sent_back_to_Menu_Page() throws Throwable {
		String expected = StringUrlPath.MenuItemPage;
		assertThat(driver.getCurrentUrl(), equalTo(expected));  
	}

	@When("^User clicks on the delete button$")
	public void user_clicks_on_the_delete_button() throws Throwable {
		driver.findElements(By.name("remove")).get(1).click();
	}

	@Then("^User is sent back to review order page$")
	public void user_is_sent_back_to_review_order_page() throws Throwable {
		String expected  = "Review Order";
		assertThat(driver.getTitle(), equalTo(expected));
	}

	@Then("^User no longer sees that item in the cart$")
	public void user_no_longer_sees_that_item_in_the_cart() throws Throwable {
		String message = driver.findElement(By.id("message")).getText();
		assertThat(message, equalTo("Item removed from the cart!"));
		assertThat(driver.findElements(By.name("remove")), hasSize(1));
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
