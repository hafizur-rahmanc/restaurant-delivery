package res.cs.junit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.core.Every;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import res.cs.dao.UserDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.User;

public class UserDAOTest {
	private UserDAO userDAO;
	private User theUser;
	private boolean isCreated;
	private boolean isUpdated;
	private int userId;
	
	@Before
	public void initialize() {
		userDAO = new UserDAO();
		theUser = new User();
		isCreated = false;
		isUpdated = false;
		userId = 0;
	}
	
	// Verify registration with valid data
	@Test
	public void createUserTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		theUser.setFirstName("Hafizur");
		theUser.setLastName("Rahman");
		theUser.setUserName("hafizrahman");
		theUser.setPassword("hafiz_Rahman");
		theUser.setGender("M");
		theUser.setAddress("6107 Wooside Ave");
		theUser.setPhoneNumber(3475278509L);
		theUser.setEmail("hafiz@restaurant.org");
		userId = userDAO.createUser(theUser);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (userId != 0);
		assertThat(isCreated , equalTo(true));
		
	}
	
	// Verify the login query
	@Test
	public void loginUserTest() throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		User tempUser = userDAO.loginUser("user", "user");
		assertThat(tempUser, IsInstanceOf.instanceOf(User.class));
	}
	
	// Verify the uodateUserTest method
	@Test
	public void updateUserTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		// Preserve the original user
		theUser = userDAO.getUserById(90);
		// Create a new user
		User newUser = new User();
		newUser.setFirstName("Abdur");
		newUser.setLastName("Rahman");
		newUser.setUserName("hafizur1");
		newUser.setPassword("hafizur1234");
		newUser.setGender("M");
		newUser.setAddress("6117 Wooside Ave Woodside");
		newUser.setPhoneNumber(6465278520L);
		newUser.setEmail("rahman@restaurant.org");
		newUser.setUserId(90);
		
		int actual = userDAO.updateUser(newUser);
		isUpdated = (actual != 0);
		assertThat(actual, equalTo(1));	
	}
	
	@Test
	public void getAllUsersTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<User> usersList = userDAO.getAllUsers();
		// Very the list size
		assertThat(usersList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(usersList, Every.everyItem(IsInstanceOf.instanceOf(User.class)));
		// Verify that it has the firstName as a property 
		assertThat(usersList, Every.everyItem(HasProperty.hasProperty("firstName")));
		// Check the specific first name in the users list
		assertThat(usersList, hasItem(Matchers.hasProperty("firstName", equalTo("Hafizur"))));	
	}
	
	// Verify that invalid data returns expected result
	@Test
	public void voidUserTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = userDAO.deleteUser(87);
		assertThat(actual, equalTo(0));	
	}
	
	@After
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			userDAO.deleteUser(userId);
		}
		if(isUpdated) {
			userDAO.updateUser(theUser);
		}
	}
}
