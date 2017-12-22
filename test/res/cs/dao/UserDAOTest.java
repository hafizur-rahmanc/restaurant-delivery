package res.cs.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import static org.junit.Assert.assertThat;

import res.cs.exception.RegistrationException;
import res.cs.model.User;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

public class UserDAOTest {
	private UserDAO dude;
	private User user;
	
	@BeforeMethod
	public void initialize() {
		dude = new UserDAO();
		user = new User();
	}
	
	@DataProvider(name="registration")
	public Object[][] inputData(){
		Object[][] data = {
				{"Mohammed", "Rahman", "hafiz", "hafiz", "M", "6107 Wooside Ave", 3475278509L, "hafiz@restaurant.org", 1},
				{"Abdur", "Rahman", "hafizur", "hafizur", "M", "6117 Wooside Ave", 6465278509L, "rahman@restaurant.org", 1},
				{"Omar", "Khait", "samy", "samy", "M", "6127 Wooside Ave", 3475278519L, "samy@restaurant.org", 1}
		};
		return data;
		
	}
	
	@Test(dataProvider="registration")
	public void registrationTest(String firstName, String lastName, String userName, String password, String gender, String address, Long phoneNumber, String email, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(userName);
		user.setPassword(password);
		user.setGender(gender);
		user.setAddress(address);
		user.setPhoneNumber(phoneNumber);
		user.setPhoneNumber(phoneNumber);
		int actual = dude.createUser(user);
		System.out.println(user);
		assertThat(actual, equalTo(expected));
		
	}
	

}
