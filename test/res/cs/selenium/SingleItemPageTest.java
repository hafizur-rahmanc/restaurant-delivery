package res.cs.selenium;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import res.cs.bo.ReviewBO;
import res.cs.util.StringUrlPath;

public class SingleItemPageTest {
	WebDriver driver;
	String testReview ="This is a test Review!";
	boolean isReviewCreated;
	
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", StringUrlPath.DriverPath);
	}
	
	
	@BeforeMethod
	public void initialize() throws InterruptedException {
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
	
	// ItemReview page should be displayed correctly
	@Test
	public void singleItemReviewTitle() {
		String expected = "Item Review";
		assertThat(driver.getTitle(), equalTo(expected));
	}
	
	// Item information and it's corresponding review elements should display
	@Test
	public void itemAndReviewElements() {
		// Item information
		WebElement itemName = driver.findElement(By.id("item-name"));
		WebElement itemImage = driver.findElement(By.className("img-rounded"));
		WebElement itemPrice = driver.findElement(By.id("item-price"));
		WebElement itemDescription = driver.findElement(By.id("item-desc"));
		
		// Add to cart button
		WebElement addToCart = driver.findElement(By.name("item"));
		
		// Review elements
		WebElement reviewEl = driver.findElement(By.name("itemReview"));
		WebElement reviewDesc = driver.findElement(By.className("review-description"));
		
		assertThat(itemName.isDisplayed(), equalTo(true));
		assertThat(itemImage.isDisplayed(), equalTo(true));
		assertThat(itemPrice.isDisplayed(), equalTo(true));
		assertThat(itemDescription.isDisplayed(), equalTo(true));
		
		assertThat(addToCart.isDisplayed(), equalTo(true));
		assertThat(reviewEl.isDisplayed(), equalTo(true));
		assertThat(reviewDesc.isDisplayed(), equalTo(true));
	}
	
	// Post a valid review and check whether it is displayed on the page
	@Test
	public void submitValidReview() {
		driver.findElement(By.name("itemReview")).sendKeys(testReview);
		driver.findElement(By.name("review")).click();
		
		isReviewCreated = true;
		
		String message = driver.findElement(By.id("message")).getText();
		String expectedMessage = "Review created successfully!";
		String actual = driver.findElement(By.className("review-description")).getText();
		assertThat(message, equalTo(expectedMessage));
		assertThat(actual, equalTo(testReview));
	}
	
	@AfterMethod
	public void finalize() throws InterruptedException, Throwable, Exception, Exception, SQLException, IOException {
		// Delete the created review
		if(isReviewCreated) {
			String itemId = driver.getCurrentUrl().split("=")[1];
			ReviewBO reviewBO = new ReviewBO();
			reviewBO.deleteReview(reviewBO.getAllReviews(Integer.parseInt(itemId)).get(0).getReviewId());
		}
		
		// Log out and quit the driver
		driver.findElement(By.id("logout")).click();
		Thread.sleep(1000);
		driver.quit();
	}
}
