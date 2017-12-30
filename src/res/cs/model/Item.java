package res.cs.model;

public class Item {
	// instance variables
	private int itemId;
	private String itemName;
	private double itemPrice;
	private String itemDescription;
	private String image;
	private int active;
	private String category;
	private int inventory;
	
	//Constructor
	public Item() {
		
	}
	
	public Item(int itemId, String itemName, double itemPrice, String itemDescription, String image, int active,
			String category) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemDescription = itemDescription;
		this.image = image;
		this.active = active;
		this.category = category;
	}
	
	public Item(String itemName, double itemPrice, String itemDescription, String image, int active, String category) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemDescription = itemDescription;
		this.image = image;
		this.active = active;
		this.category = category;
	}

	// setter and getter methods
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}


}
