package res.cs.util;

import java.util.regex.Pattern;

public class InputValidator {
	public boolean isValidInput(String input) {
		String regex = "[a-zA-Z0-9_]{1,20}";
		return Pattern.matches(regex, input);
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
	
	public static void main(String[] args) {
		InputValidator input = new InputValidator();
		System.out.println(input.isValidGender("M"));
		System.out.println(input.isValidInput("NewUser"));
		System.out.println(input.isValidAddress("7119 35th Ave"));
		System.out.println(input.isValidRegistartion("Hafizur", "Rahman", "hafiz_ny", "h123", "h123", "138 Grand Concourse", "M", "1111111111", "hafizur@restaurant.org"));
	}
}
