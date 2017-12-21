package res.cs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import res.cs.exception.RegistrationException;
import res.cs.model.Review;
import res.cs.util.OracleSqlQueries;

public class ReviewDAO {
	//Create a review for a particular item
	public int createReview(Review review) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int reviewId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String[] idColumn = {"review_id"};
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_REVIEW, idColumn);
			//Set attribute to the SQL query string
			stmt.setInt(1, review.getUserId());
			stmt.setInt(2, review.getItemId());
			stmt.setString(3, review.getDescription());
			//Create review for the particular item
			stmt.executeUpdate();
			
			//Retrieve any auto generated keys created as a result of executing this statement object
			result = stmt.getGeneratedKeys();
			if(result.next()) {
				reviewId = result.getInt(1);
			}
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			try {
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				
			}
		}
		
		return reviewId;
	}
	//Get all the reviews for a particular item using the item_id
	public List<Review> getAllReviews(int item_id) throws RegistrationException, SQLException, ClassNotFoundException, IOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Review> reviewsList = null;
		Review singleReview = null;
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.GET_REVIEWS);
			//Fill out the Query string by providing the user id
			stmt.setInt(1, item_id);
			//Execute the Query 
			resultSet = stmt.executeQuery();
			reviewsList = new ArrayList<Review>();
			
			while(resultSet.next()) {
				singleReview = new Review();
				singleReview.setReviewId(resultSet.getInt(1));
				singleReview.setUserId(resultSet.getInt(2));
				singleReview.setItemId(resultSet.getInt(3));
				singleReview.setDescription(resultSet.getString(4));
				
				//Add to the reviews list
				reviewsList.add(singleReview);
			}
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			try {
				resultSet.close();
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				
			}
		}
		return reviewsList;
	}
	
	//Get all the reviews for a particular user using the user_id
		public List<Review> getReviewsByUser(int user_id) throws RegistrationException, SQLException, ClassNotFoundException, IOException {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			List<Review> reviewsList = null;
			Review singleReview = null;
			OracleConnection oracle = new OracleConnection();
			
			try {
				conn = oracle.getConnection();
				stmt = conn.prepareStatement(OracleSqlQueries.GET_REVIEWS_BY_USER);
				//Fill out the Query string by providing the user id
				stmt.setInt(1, user_id);
				//Execute the Query 
				resultSet = stmt.executeQuery();
				reviewsList = new ArrayList<Review>();
				
				while(resultSet.next()) {
					singleReview = new Review();
					singleReview.setReviewId(resultSet.getInt(1));
					singleReview.setUserId(resultSet.getInt(2));
					singleReview.setItemId(resultSet.getInt(3));
					singleReview.setDescription(resultSet.getString(4));
					
					//Add to the reviews list
					reviewsList.add(singleReview);
				}
				
			}catch(SQLException e) {
				throw new RegistrationException(e.getMessage());
			}catch(Exception e) {
				throw new RegistrationException(e.getMessage());
			}finally {
				try {
					resultSet.close();
					stmt.close();
					conn.close();
				}catch(SQLException e) {
					
				}
			}
			return reviewsList;
		}
	
	//Admin can remove a review by using the review_id
	public void removeReview(int reviewId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.REMOVE_REVIEW);
			// set the corresponding parameter
			stmt.setInt(1, reviewId);
			// execute the delete statement
			stmt.executeUpdate();
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			try {
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				
			}
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Review review = new Review();
		review.setUserId(2);
		review.setItemId(1);
		review.setDescription("I love it.");
		ReviewDAO DAO = new ReviewDAO();
		//DAO.removeReview(2);
		int id = DAO.createReview(review);
		System.out.println("Last created Review id is: " + id);
		System.out.println(DAO.getAllReviews(1));
	}

}
