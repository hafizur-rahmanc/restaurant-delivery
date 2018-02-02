package res.cs.testng;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.core.Every;
import org.hamcrest.core.IsInstanceOf;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import res.cs.dao.ReviewDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Review;

public class ReviewDAOTest {
	private ReviewDAO reviewDAO;
	private Review theReview;
	private boolean isCreated;
	private int reviewId;
	
	@BeforeMethod
	public void initialize() {
		reviewDAO = new ReviewDAO();
		theReview = null;
		isCreated = false;
		reviewId = 0;
	}
	
	@DataProvider(name="newReview")
	public Object[][] inputData(){
		Object[][] data = {
				{90, 21, "Awesome, definitely you will enjoy it", true},
				{92, 1, "What a great food it was", true},
				{2, 1, "I love this item", true}
		};
		return data;	
	}
	
	// Verify that create a review is working as expected
	@Test(dataProvider="newReview")
	public void createReviewTest(int userId, int itemId, String description, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		theReview = new Review(userId, itemId, description);
		reviewId = reviewDAO.createReview(theReview);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (reviewId != 0);
		assertThat(isCreated , equalTo(expected));
	}
	
	@DataProvider(name="getReviewsByUser")
	public Object[][] sampleData(){
		Object[][] data = {
				{2, "I love this item", true},
				{2, "Really Tasty.", false},
				{92, "Really Tasty", false}
		};
		return data;	
	}
	
	@Test(dataProvider="getReviewsByUser")
	public void getReviewsByUserTest(int userId, String description, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Review> reviewsList = reviewDAO.getReviewsByUser(userId);
		// Very the list size
		assertThat(reviewsList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(reviewsList, Every.everyItem(IsInstanceOf.instanceOf(Review.class)));
		// Verify that it has the description as a property 
		assertThat(reviewsList, Every.everyItem(HasProperty.hasProperty("description")));
		// Check the specific description in the reviews list
		if(expected) {
			assertThat(reviewsList, hasItem(Matchers.hasProperty("description", equalTo(description))));
		}
	}
	
	@DataProvider(name="getReviewsByItem")
	public Object[][] Data(){
		Object[][] data = {
				{1, "What a great food it was", true},
				{1, "Really Tasty.", false},
				{1, "Awesome, definitely you will enjoy it", false}
		};
		return data;	
	}
	
	@Test(dataProvider="getReviewsByItem")
	public void itemReviewTest(int itemId, String description, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Review> reviewsList = reviewDAO.getReviewsByItem(itemId);
		// Very the list size
		assertThat(reviewsList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(reviewsList, Every.everyItem(IsInstanceOf.instanceOf(Review.class)));
		// Verify that it has the description as a property 
		assertThat(reviewsList, Every.everyItem(HasProperty.hasProperty("description")));
		// Check the specific description in the reviews list
		if(expected) {
			assertThat(reviewsList, hasItem(Matchers.hasProperty("description", equalTo(description))));
		}
	}
	
	@DataProvider(name="deleteReview")
	public Object[][] createData(){
		Object[][] data = {
				{81, 0}, // not available case
				{87, 0} // not available case
		};
		return data;	
	}
	
	// Verify that invalid data returns expected result
	@Test(dataProvider="deleteReview")
	public void deletReviewTest(int reviewId, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = reviewDAO.deleteReview(reviewId);;
		assertThat(actual, equalTo(expected));	
	}
	
	@AfterMethod
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			reviewDAO.deleteReview(reviewId);
		}
	}

}
