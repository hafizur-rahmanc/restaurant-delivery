package cucumber_stepDefinition.adminOrderList;

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

public class AdminOrderListPageSteps {
	public static WebDriver driver;
	
	boolean loggedIn;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}

	@Given("^Admin is on Admin Order List page$")
	public void admin_is_on_Admin_Order_List_page() throws Throwable {
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
		driver.findElement(By.id("order-list")).click();     
	}
	
	@Then("^Admin sees page header \"([^\"]*)\"$")
	public void admin_sees_page_header(String expected) throws Throwable {
		assertThat(driver.getTitle(), equalTo(expected));   
	}
	
	@Then("^Admin sees delete button$")
	public void admin_sees_delete_button() throws Throwable {
		WebElement deleteBtn = driver.findElement(By.id("delete-order"));
	    assertThat(deleteBtn.isDisplayed(), equalTo(true));
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
