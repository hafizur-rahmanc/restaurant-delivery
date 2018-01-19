package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import res.cs.dao.UserDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.User;

public class UserBO {
	// Declare a UserDAO ariable
	final UserDAO userDAO = new UserDAO();

	//Create user by calling the UserDAO
	public int createUser(User user) throws IOException, RegistrationException, SQLException, ClassNotFoundException {
		Integer userId = null;
		try {
			userId = userDAO.createUser(user);
			
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return userId;
	}
	// Get the user object by the user name and password using UserDAO
	public User loginUser(String userName, String password) throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		User theUser = new User();
		theUser = userDAO.loginUser(userName, password);
		return theUser;
	}
	
	//Get the user object by the user name using the UserDAO
	public User getUser(String userName) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		User theUser = null;
		try {
			theUser = userDAO.getUser(userName);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return theUser;
	}
	
	//Get the user object by user_id
	public User getUserById(int userId) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		User theUser = new User();
		theUser = userDAO.getUserById(userId);
		return theUser;
	}
	
	//Get all the users list using UserDAO
	public List<User> getAllUsers() throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		final UserDAO userDAO = new UserDAO();
		List<User> usersList = null;
		try {
			usersList = userDAO.getAllUsers();
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return usersList;	
	}
	
	//Update the user using the UserDAO
	public int updateUser(User user) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		final UserDAO userDAO = new UserDAO();
		int result = 0;
		try {
			result = userDAO.updateUser(user);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return result;
	}
	public boolean isAdmin(User theUser) {
		// TODO Auto-generated method stub
		return theUser.getAdminRole() == 1 ? true : false;
	}
	
}
