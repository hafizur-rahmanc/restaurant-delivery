package res.cs.model;

public class Payment {
	//Intance variables
	private int paymentId;
	private long creditCardNumber;
	private int secureCode;
	private int zipcode;
	
	//Constructor
	public Payment() {
		
	}
	public Payment(long creditCardNumber, int secureCode, int zipcode) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.secureCode = secureCode;
		this.zipcode = zipcode;
	}
	//Getter and setter methods
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public long getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public int getSecureCode() {
		return secureCode;
	}
	public void setSecureCode(int secureCode) {
		this.secureCode = secureCode;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	

}
