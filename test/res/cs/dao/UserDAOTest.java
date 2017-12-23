package res.cs.dao;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.assertThat;

import res.cs.exception.RegistrationException;
import res.cs.model.User;


import java.io.IOException;
import java.sql.SQLException;

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
				{"Abdur", "Rahman", "hafizur1", "hafizur123", "M", "6117 Wooside Ave", 6465278509L, "rahman@restaurant.org", 1},
				{"Omar", "Khait", "samy", "samy", "M", "6127 Wooside Ave", 3475278519L, "samy@restaurant.org", 1}
		};
		return data;
		
	}
	
	@Test(dataProvider="registration")
	public void createUserTest(String firstName, String lastName, String userName, String password, String gender, String address, Long phoneNumber, String email, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(userName);
		user.setPassword(password);
		user.setGender(gender);
		user.setAddress(address);
		user.setPhoneNumber(phoneNumber);
		user.setEmail(email);
		int actual = dude.createUser(user);
		System.out.println(user);
		assertThat(actual, equalTo(expected));
		
	}
	
	@DataProvider(name="getuser")
	public Object[][] sampleData(){
		Object[][] data = {
				{"hafiz", "hafiz@restaurant.org"},
				{"hafizur", "hafizur@restaurant.org"},
				{"samy", "samy@restaurant.org"}
		};
		return data;
		
	}
	
	@Test(dataProvider="getuser")
	public void getUserTest(String userName, String expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		User actual = dude.getUser(userName);
		System.out.println(user);
		assertThat(actual.getEmail(), equalTo(expected));
		
	}
	
}
