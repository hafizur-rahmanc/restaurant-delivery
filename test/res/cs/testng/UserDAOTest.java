package res.cs.testng;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.hasItem;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.core.Every;
import static org.junit.Assert.assertThat;

import res.cs.dao.UserDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.User;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDAOTest {
	private UserDAO userDAO;
	private User theUser;
	private boolean isCreated;
	private boolean isUpdated;
	private int userId;
	
	@BeforeMethod
	public void initialize() {
		userDAO = new UserDAO();
		theUser = new User();
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
	// Verify registration with valid data
	@Test(dataProvider="registration")
	public void createUserTest(String firstName, String lastName, String userName, String password, String gender, String address, Long phoneNumber, String email, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		theUser.setFirstName(firstName);
		theUser.setLastName(lastName);
		theUser.setUserName(userName);
		theUser.setPassword(password);
		theUser.setGender(gender);
		theUser.setAddress(address);
		theUser.setPhoneNumber(phoneNumber);
		theUser.setEmail(email);
		userId = userDAO.createUser(theUser);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (userId != 0);
		assertThat(isCreated , equalTo(expected));
		
	}
	
	@DataProvider(name="loginUser")
	public Object[][] loginUserData(){
		Object[][] data = {
				{"user", "user", "user@restaurant.org"},
				{"samy12", "samy12", "samy@restaurant.org"},
				{"hafiz_ny", "hafiz", null}
		};
		return data;
	}
	
	// Verify the login query
	@Test(dataProvider="loginUser")
	public void loginUserTest(String userName, String password, String expected) throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		User tempUser = userDAO.loginUser(userName, password);
		String actual = (tempUser != null) ? tempUser.getEmail() : null;
		assertThat(actual, equalTo(expected));
	}
	
	@DataProvider(name="getUserNameTest")
	public Object[][] sampleData(){
		Object[][] data = {
				{"hafizur123", true},
				{"admin", false},
				{"user", false}
		};
		return data;	
	}
	
	
	// Verify that the UserName is already in the database or not
	@Test(dataProvider="getUserNameTest")
	public void isUserNameAvailable(String userName, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		boolean actual = userDAO.isUserNameAvailable(userName);
		assertThat(actual, equalTo(expected));
		
	}
	
	@DataProvider(name="getAllUsers")
	public Object[][] getAllUsersData(){
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
		// Very the list size
		assertThat(usersList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(usersList, Every.everyItem(IsInstanceOf.instanceOf(User.class)));
		// Verify that it has the firstName as a property 
		assertThat(usersList, Every.everyItem(HasProperty.hasProperty("firstName")));
		// Check the specific first name in the users list
		if(expected) {
			assertThat(usersList, hasItem(Matchers.hasProperty("firstName", equalTo(firstName))));
		}
		
	}
	
	@DataProvider(name="updateUser")
	public Object[][] updateUserData(){
		Object[][] data = {
				{90, "Abdur", "Rahman", "hafizur1", "hafizur1234", "M", "6117 Wooside Ave Woodside", 6465278520L, "rahman@restaurant.org", 1},
				{126, "Hafizur", "Rahman", "hafiz_ny", "password1234", "M", "6117 Wooside Ave Woodside", 3475278509L, "hafiz@restaurant.org", 1}
		};
		return data;	
	}
	
	// Verify the uodateUserTest method
	@Test(dataProvider="updateUser")
	public void updateUserTest(int userID, String firstName, String lastName, String userName, String password, String gender, String address, Long phoneNumber, String email, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		// preserve the original user
		theUser = userDAO.getUserById(userID);
		// create a new user
		User newUser = new User();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setUserName(userName);
		newUser.setPassword(password);
		newUser.setGender(gender);
		newUser.setAddress(address);
		newUser.setPhoneNumber(phoneNumber);
		newUser.setEmail(email);
		newUser.setUserId(userID);
		
		int actual = userDAO.updateUser(newUser);
		isUpdated = (actual != 0);
		assertThat(actual, equalTo(expected));
		
	}
	
	@DataProvider(name="deleteUser")
	public Object[][] deleteUserData(){
		Object[][] data = {
				{81, 0},
				{87, 0} // not available case
		};
		return data;	
	}
	
	// Verify that invalid data returns expected result
	@Test(dataProvider="deleteUser")
	public void voidUserTest(int userId, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = userDAO.deleteUser(userId);
		assertThat(actual, equalTo(expected));	
	}
	
	@AfterMethod
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			userDAO.deleteUser(userId);
		}
		if(isUpdated) {
			userDAO.updateUser(theUser);
		}
	}	
}
