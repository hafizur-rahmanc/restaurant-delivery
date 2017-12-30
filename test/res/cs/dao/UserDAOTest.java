package res.cs.dao;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.assertThat;

import res.cs.exception.RegistrationException;
import res.cs.model.User;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDAOTest {
	private UserDAO userDAO;
	private User user;
	private boolean isCreated;
	private boolean isUpdated;
	private int userId;
	
	@BeforeMethod
	public void initialize() {
		userDAO = new UserDAO();
		user = new User();
		isCreated = false;
		isUpdated = false;
		userId = 0;
	}
	
	@DataProvider(name="registration")
	public Object[][] inputData(){
		Object[][] data = {
				{"Md", "Rahman", "hafizrahman", "hafiz_Rahman", "M", "6107 Wooside Ave", 3475278509L, "hafiz@restaurant.org", true},
				{"Abdu", "Rahman", "hafizur12345", "hafizur567", "M", "6117 Wooside Ave", 6465278509L, "rahman@restaurant.org", true},
				{"OmarSulaiman", "Khait", "samy123", "samy123", "M", "6127 Wooside Ave", 3475278519L, "samy@restaurant.org", true}
		};
		return data;	
	}
	
	@Test(dataProvider="registration")
	public void createUserTest(String firstName, String lastName, String userName, String password, String gender, String address, Long phoneNumber, String email, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(userName);
		user.setPassword(password);
		user.setGender(gender);
		user.setAddress(address);
		user.setPhoneNumber(phoneNumber);
		user.setEmail(email);
		userId = userDAO.createUser(user);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (userId != 0);
		assertThat(isCreated , equalTo(expected));
		
	}
	
	@DataProvider(name="getUser")
	public Object[][] sampleData(){
		Object[][] data = {
				{"hafizur12", "rahman@restaurant.org"},
				{"admin", "admin@retaurant.org"},
				{"user", "user@restaurant.org"}
		};
		return data;	
	}
	
	@Test(dataProvider="getUser")
	public void getUserTest(String userName, String expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		User actual = userDAO.getUser(userName);
		assertThat(actual.getEmail(), equalTo(expected));
		
	}
	
	@DataProvider(name="getAllUsers")
	public Object[][] Data(){
		Object[][] data = {
				{"Hafizur", true},
				{"Abdur", true},
				{"OmarSulaiman", true},
				{"Khait", false}
		};
		return data;	
	}
	
	@Test(dataProvider="getAllUsers")
	public void getAllUsersTest(String firstName, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<User> usersList = userDAO.getAllUsers();
		boolean actual = false;
		for(User user : usersList) {
			if(firstName.equals(user.getFirstName())) {
				actual = true;
				break;
			}
		}
		
		assertThat(actual, equalTo(expected));	
	}
	
	@DataProvider(name="updateUser")
	public Object[][] userData(){
		Object[][] data = {
				{90, "Abdur", "Rahman", "hafizur1", "hafizur1234", "M", "6117 Wooside Ave Woodside", 6465278520L, "rahman@restaurant.org", 1},
				{126, "Hafizur", "Rahman", "hafiz_ny", "password1234", "M", "6117 Wooside Ave Woodside", 3475278509L, "hafiz@restaurant.org", 1}
		};
		return data;	
	}
	
	@Test(dataProvider="updateUser")
	public void updateUserTest(int userId, String firstName, String lastName, String userName, String password, String gender, String address, Long phoneNumber, String email, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		user = userDAO.getUser(userName);
		User newUser = new User();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setUserName(userName);
		newUser.setPassword(password);
		newUser.setGender(gender);
		newUser.setAddress(address);
		newUser.setPhoneNumber(phoneNumber);
		newUser.setEmail(email);
		newUser.setUserId(userId);
		
		int actual = userDAO.updateUser(newUser);
		isUpdated = (actual != 0);
		assertThat(actual, equalTo(expected));
		
	}
	
	@DataProvider(name="removeUser")
	public Object[][] createData(){
		Object[][] data = {
				{81, 0},
				{87, 0} // not available case
		};
		return data;	
	}
	
	@Test(dataProvider="removeUser")
	public void voidUserTest(int userId, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = userDAO.removeUser(userId);
		assertThat(actual, equalTo(expected));	
	}
	
	@AfterMethod
	public void removeCreatedUser() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			userDAO.removeUser(userId);
		}
		if(isUpdated) {
			userDAO.updateUser(user);
		}
	}	
}
