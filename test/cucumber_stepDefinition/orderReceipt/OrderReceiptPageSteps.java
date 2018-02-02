package cucumber_stepDefinition.orderReceipt;

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
import res.cs.util.StringUrlPath;

public class OrderReceiptPageSteps {
	public static WebDriver driver;
	boolean loggedIn;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^User navigated to their order receipt page$")
	public void user_navigated_to_their_order_receipt_page() throws Throwable {
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
		// Navigate to the order receipt page
		driver.navigate().to(StringUrlPath.OrderReceiptPage);  
	}

	@Then("^User sees receipt information for each item$")
	public void user_sees_receipt_information_for_each_item() throws Throwable {
		WebElement itemName = driver.findElement(By.id("item-name"));
		WebElement itemImage = driver.findElement(By.className("img-rounded"));
		WebElement itemPrice = driver.findElement(By.id("item-price"));
		
		assertThat(itemName.isDisplayed(), equalTo(true));
		assertThat(itemImage.isDisplayed(), equalTo(true));
		assertThat(itemPrice.isDisplayed(), equalTo(true));
	}

	@Then("^User sees logout link$")
	public void user_sees_logout_link() throws Throwable {
	    WebElement logoutLink = driver.findElement(By.id("logout"));
		assertThat(logoutLink.isDisplayed(), equalTo(true));
	}

	@Then("^User sees menu items link$")
	public void user_sees_menu_items_link() throws Throwable {
	    WebElement menuLink = driver.findElement(By.id("menu-link"));
		assertThat(menuLink.isDisplayed(), equalTo(true));   
	}

	@Then("^User sees past orders link$")
	public void user_sees_past_orders_link() throws Throwable {
	    WebElement ordersLink = driver.findElement(By.id("orders-link"));
		assertThat(ordersLink.isDisplayed(), equalTo(true));
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
