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
				{"Md", "Rahman", "hafiz11", "hafiz11", "M", "6107 Wooside Ave", 3475278509L, "hafiz@restaurant.org", 1},
				{"Abdu", "Rahman", "hafizur12", "hafizur123", "M", "6117 Wooside Ave", 6465278509L, "rahman@restaurant.org", 1},
				{"OmarSulaiman", "Khait", "samy12", "samy12", "M", "6127 Wooside Ave", 3475278519L, "samy@restaurant.org", 1}
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
	
	@DataProvider(name="updateUser")
	public Object[][] userData(){
		Object[][] data = {
				{56, "Mohammed", "Rahman", "hafiz", "hafiz", "M", "6107 Wooside Ave Woodside", 3475278510L, "hafiz@restaurant.org", 1},
				{57, "Abdur", "Rahman", "hafizur1", "hafizur123", "M", "6117 Wooside Ave Woodside", 6465278520L, "rahman@restaurant.org", 1},
				{58, "Omar", "Khait", "samy", "samy", "M", "6127 Wooside Ave Woodside", 3475278530L, "samy@restaurant.org", 1}
		};
		return data;
		
	}
	
	@Test(dataProvider="updateUser")
	public void updateUserTest(int userId, String firstName, String lastName, String userName, String password, String gender, String address, Long phoneNumber, String email, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(userName);
		user.setPassword(password);
		user.setGender(gender);
		user.setAddress(address);
		user.setPhoneNumber(phoneNumber);
		user.setEmail(email);
		user.setUserId(userId);
		int actual = dude.updateUser(user);
		assertThat(actual, equalTo(expected));
		
	}
	
	@DataProvider(name="removeUser")
	public Object[][] createData(){
		Object[][] data = {
				{56, 1},
				{57, 1},
				{58, 1},
				{60, 0} // not available case
		};
		return data;
		
	}
	
	@Test(dataProvider="updateUser")
	public void voidUserTest(int userId, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = dude.removeUser(userId);
		assertThat(actual, equalTo(expected));
		
	}
	
}
