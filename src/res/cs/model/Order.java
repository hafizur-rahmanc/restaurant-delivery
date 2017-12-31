package res.cs.model;

public class Order {
	//Instance variables
	private int orderId;
	private int userId;
	private int storeId;
	private int paymentId;
	private double subtotal;
	private double taxAmount;
	private double totalPrice;
	
	//Constructor
	public Order() {
		
	}
	
	public Order(int orderId, int userId, int storeId, int paymentId, double subtotal, double taxAmount,
			double totalPrice) {
		this.orderId = orderId;
		this.userId = userId;
		this.storeId = storeId;
		this.paymentId = paymentId;
		this.subtotal = subtotal;
		this.taxAmount = taxAmount;
		this.totalPrice = totalPrice;
	}

	public Order(int userId, int storeId, int paymentId, double subtotal, double taxAmount,
			double totalPrice) {
		this.userId = userId;
		this.storeId = storeId;
		this.paymentId = paymentId;
		this.subtotal = subtotal;
		this.taxAmount = taxAmount;
		this.totalPrice = totalPrice;
	}

	//Getter and setter methods
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	
}
