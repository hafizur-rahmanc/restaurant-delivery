package res.cs.util;

import java.util.regex.Pattern;

public class InputValidator {
	public boolean isValidInput(String input) {
		String regex = "[a-zA-Z0-9_]{1,20}";
		return Pattern.matches(regex, input);
	}
	
	public boolean isValidItemName(String input) {
		String regex = "[a-zA-Z0-9 ]{1,20}";
		return Pattern.matches(regex, input);
	}
	public boolean isValidActive(String input) {
		String regex = "[01]{1}";
		return Pattern.matches(regex, input);
	}
	public boolean isValidPrice(String price) {
		String regex = "[0-9]+([,.][0-9]{1,2})?";
		return Pattern.matches(regex, price);
	}
	public boolean isValidImage(String image) {
		String regex = "[a-zA-Z0-9.-]{1,}";
		return Pattern.matches(regex, image);
	}
	
	public boolean isValidGender(String gender) {
		String regex = "[MF]{1}";
		return Pattern.matches(regex, gender);
	}
	
	public boolean isValidAddress(String input) {
		String regex = "[a-zA-Z0-9 .]{1,100}";
		return Pattern.matches(regex, input);
	}
	
	public boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "[0-9]{10}";
		return Pattern.matches(regex, phoneNumber);
	}
	
	public boolean isValidEmail(String email) {
		String regex = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}";
		return Pattern.matches(regex, email);
	}
	
	public boolean isValidCreditCard(String creditCardNumber) {
		String regex = "[0-9]{1,20}";
		return Pattern.matches(regex, creditCardNumber);
	}
	
	public boolean isValidSecureCode(String secureCode) {
		String regex = "[0-9]{3,}";
		return Pattern.matches(regex, secureCode);
	}
	
	public boolean isValidZipCode(String zipCode) {
		String regex = "[0-9]{5,}";
		return Pattern.matches(regex, zipCode);
	}
	
	public boolean isValidPaymentInfo(String creditCardNumber, String secureCode, String zipCode) {
		if (isValidCreditCard(creditCardNumber) && isValidSecureCode(secureCode) && isValidZipCode(zipCode)){
			return true;
		} else {
			return false;
		}
	}
	public boolean isValidLogin(String userName, String password) {
		if (isValidInput(userName) && isValidInput(password)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValidRegistartion(String firstName, String lastName, String userName, String password, 
			String repassword, String address, String gender, String phoneNumber, String email) {
		if (isValidInput(firstName) && isValidInput(lastName) && isValidInput(userName) && isValidInput(password) 
				&& isValidInput(repassword) && isValidAddress(address) && isValidGender(gender) && isValidPhoneNumber(phoneNumber) && isValidEmail(email)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValidReview(String reviewDesc) {
		if (reviewDesc != null && reviewDesc.length() <= 500) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValidDescription(String description) {
		if (description != null && description.length() <= 500) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValidUpdate(String firstName, String lastName, String userName, String password, 
			String repassword, String address, String gender, String phoneNumber, String email) {
		if (isValidInput(firstName) && isValidInput(lastName) && isValidInput(userName) && isValidInput(password) 
				&& isValidInput(repassword) && isValidAddress(address) && isValidGender(gender) && isValidPhoneNumber(phoneNumber) && isValidEmail(email)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValidItem(String itemName, String itemPrice,String itemDescription, String image, String active, String category) {
		if(isValidItemName(itemName) && isValidPrice(itemPrice) && isValidDescription(itemDescription) && 
				isValidImage(image) && isValidActive(active) && isValidItemName(category)) {
			return true;
		} else {
			return false;
		}
	}
	public static void main(String[] args) {
		InputValidator v = new InputValidator();
		System.out.println(v.isValidInput("1"));
		System.out.println(v.isValidImage("item-name.jpg"));
		System.out.println(v.isValidActive("0"));
		
	}
}
