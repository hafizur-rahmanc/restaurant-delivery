package cucumber_stepDefinition.singleItem;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
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
import res.cs.bo.ReviewBO;
import res.cs.exception.RegistrationException;
import res.cs.util.StringUrlPath;

public class SingleItemPageSteps {
	WebDriver driver;
	String testReview ="This is a test Review!";
	boolean isReviewCreated;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	@Given("^User navigated to review an Item$")
	public void user_navigated_to_review_an_Item() throws Throwable {
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
		// Click the reviews button
		driver.findElement(By.name("reviews")).click();
		isReviewCreated = false;
	}

	@Then("^logout link should be displayed$")
	public void logout_link_should_be_displayed() throws Throwable {
	    WebElement logoutLink = driver.findElement(By.id("logout"));
		assertThat(logoutLink.isDisplayed(), equalTo(true));
	}

	@When("^User writes a review$")
	public void user_writes_a_review() throws Throwable {
		driver.findElement(By.name("itemReview")).sendKeys(testReview);	   
	}

	@When("^User submits a review$")
	public void user_submits_a_review() throws Throwable { 
		driver.findElement(By.name("review")).click();
		isReviewCreated = true;
	}

	@Then("^User should be able to see the submitted review$")
	public void user_should_be_able_to_see_the_submitted_review() throws Throwable {
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Review created successfully!";
		String actual = driver.findElement(By.className("review-description")).getText();
		assertThat(message, equalTo(expectedMessage));
		assertThat(actual, equalTo(testReview)); 
	}
	
	@After
	public void finalize(Scenario scenario) throws InterruptedException, NumberFormatException, ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(scenario.isFailed()) {
			TakesScreenshot camera = (TakesScreenshot) driver;
            byte[] screenshot = camera.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
		
		// Delete the created review
		if(isReviewCreated) {
			String itemId = driver.getCurrentUrl().split("=")[1];
			ReviewBO reviewBO = new ReviewBO();
			reviewBO.deleteReview(reviewBO.getAllReviews(Integer.parseInt(itemId)).get(0).getReviewId());
		}
		
		driver.findElement(By.id("logout")).click();
		Thread.sleep(1000);
		driver.quit();
	}
}
