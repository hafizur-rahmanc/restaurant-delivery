package res.cs.model;

public class Review {
	//Instance variables
	private int reviewId;
	private int userId;
	private int itemId;
	private String description;
	
	// Review UserName
	private String userName;
	
	//Constructor
	public Review() {
		
	}
	public Review(int userId, int itemId, String description) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.description = description;
	}
	public Review(int reviewId, int userId, int itemId, String description) {
		super();
		this.reviewId = reviewId;
		this.userId = userId;
		this.itemId = itemId;
		this.description = description;
	}
	//Setter and getter methods
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
