package res.cs.model;

public class Store {
	// instance variables
	private int storeId;
	private String storeName;
	private String address;
	private String city;
	private int staffNumber;
	private int zipcode;
	private String image;
	
	// constructors
	public Store() {
		
	}
	
	public Store(int storeId, String storeName, String address, String city, int staffNumber, int zipcode,
			String image) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.address = address;
		this.city = city;
		this.staffNumber = staffNumber;
		this.zipcode = zipcode;
		this.image = image;
	}
	
	public Store(String storeName, String address, String city, int staffNumber, int zipcode, String image) {
		super();
		this.storeName = storeName;
		this.address = address;
		this.city = city;
		this.staffNumber = staffNumber;
		this.zipcode = zipcode;
		this.image = image;
	}

	// setter and getter methods
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getStaffNumber() {
		return staffNumber;
	}
	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

}
