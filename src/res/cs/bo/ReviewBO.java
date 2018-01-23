package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import res.cs.dao.ReviewDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Review;

public class ReviewBO {
	// Create a review for a particular item and return it's id
	public int createReview(Review review) throws ClassNotFoundException, IOException, RegistrationException, SQLException{
		final ReviewDAO reviewDAO = new ReviewDAO();
		Integer reviewId = null;
		try {
			reviewId = reviewDAO.createReview(review);
			
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return reviewId;
	}
	
	// Get all the reviews for a particular item using the item_id
	public List<Review> getAllReviews(int item_id) throws RegistrationException, SQLException, ClassNotFoundException, IOException {
		final ReviewDAO reviewDAO = new ReviewDAO();
		List<Review> reviewsList = null;
		try {
			reviewsList = reviewDAO.getReviewsByItem(item_id);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return reviewsList;
	}
	
	// Get all the reviews by a particular user using the user_id
	public List<Review> getReviewsByUser(int user_id) throws RegistrationException, SQLException, ClassNotFoundException, IOException {
		final ReviewDAO reviewDAO = new ReviewDAO();
		List<Review> reviewsList = null;
		try {
			reviewsList = reviewDAO.getReviewsByUser(user_id);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return reviewsList;
	}
	
	// Admin can delete a review by the review_id
	public int deleteReview(int reviewId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		final ReviewDAO reviewDAO = new ReviewDAO();
		int result = 0;
		try {
			result = reviewDAO.deleteReview(reviewId);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return result;
	}
}
