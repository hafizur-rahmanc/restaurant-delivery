package res.cs.junit;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import res.cs.dao.ReviewDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Review;

public class ReviewDAOTest {
	private ReviewDAO reviewDAO;
	private Review theReview;
	private boolean isCreated;
	private int reviewId;
	
	@Before
	public void initialize() {
		reviewDAO = new ReviewDAO();
		theReview = null;
		isCreated = false;
		reviewId = 0;
	}
	
	// Verify that create a review is working as expected
	public void createReviewTest(int userId, int itemId, String description, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		theReview = new Review(2, 1,"I really enjoyed the food" );
		reviewId = reviewDAO.createReview(theReview);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (reviewId != 0);
		assertThat(isCreated , equalTo(true));
	}
	
	// Verify that user can get their item reviews
	@Test
	public void getReviewsByUserTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Review> reviewsList = reviewDAO.getReviewsByUser(2);
		// Very the list size
		assertThat(reviewsList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(reviewsList, Every.everyItem(IsInstanceOf.instanceOf(Review.class)));
		// Verify that it has the description as a property 
		assertThat(reviewsList, Every.everyItem(HasProperty.hasProperty("description")));
		// Check the specific description in the reviews list
		assertThat(reviewsList, hasItem(Matchers.hasProperty("description", equalTo("I love this item"))));
	}
	
	// Verify that we can get item reviews correctly
	@Test
	public void itemReviewTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Review> reviewsList = reviewDAO.getReviewsByItem(1);
		// Very the list size
		assertThat(reviewsList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(reviewsList, Every.everyItem(IsInstanceOf.instanceOf(Review.class)));
		// Verify that it has the description as a property 
		assertThat(reviewsList, Every.everyItem(HasProperty.hasProperty("description")));
		// Check the specific description in the reviews list
		assertThat(reviewsList, hasItem(Matchers.hasProperty("description", equalTo("What a great food it was"))));
	}
	// Verify that invalid data returns expected result
	@Test
	public void deletReviewTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = reviewDAO.deleteReview(87);;
		assertThat(actual, equalTo(0));	
	}
	
	@After
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			reviewDAO.deleteReview(reviewId);
		}
	}
}
