package res.cs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import res.cs.exception.RegistrationException;
import res.cs.model.User;
import res.cs.util.OracleSqlQueries;

public class UserDAO {
	//Create new user and save it to the database
	public int createUser(User user) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int userId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		String[] idColumn = {"user_id"};
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			//What's the purpose of idColumn?
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_USER, idColumn);
			//Fill out the ? in the SQL query string
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getUserName());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, user.getGender());
			stmt.setString(6, user.getAddress());
			stmt.setLong(7, user.getPhoneNumber());
			stmt.setString(8, user.getEmail());
			
			//For new user registration
			userId = stmt.executeUpdate();
			
			//Retrieve any auto generated keys created as a result of executing this statement object
			resultSet = stmt.getGeneratedKeys();
			if(resultSet.next()) {
				userId = resultSet.getInt(1);
			}
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		
		return userId;
	}

	//Close resultSet, stmt and conn if they are open
	private void close(ResultSet resultSet, PreparedStatement stmt, Connection conn) throws SQLException {
		if(resultSet != null) {
			resultSet.close();
		}
		if(stmt != null) {
			stmt.close();
		}
		if(conn != null) {
			conn.close();
		}
	}

	//Get the user object by user_name
	public User getUser(String userName) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User user = null;
		OracleConnection oracle = new OracleConnection();
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.GET_USER);
			//Assign the userName to the sql query prepared statement
			stmt.setString(1, userName);
			//Execute the prepared statement query and store it to resultSet
			resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				user = new User();
				user.setUserId(resultSet.getInt(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setUserName(resultSet.getString(4));
				user.setPassword(resultSet.getString(5));
				user.setGender(resultSet.getString(6));
				user.setAddress(resultSet.getString(7));
				user.setPhoneNumber(resultSet.getLong(8));
				user.setEmail(resultSet.getString(9));
				user.setAdminRole(resultSet.getInt(10));
			}
			
			
		}catch(SQLException e){
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		return user;
	}
	
	//Get the user object by user_id
	public User getUserById(int userId) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User user = null;
		OracleConnection oracle = new OracleConnection();
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.GET_USER_BY_ID);
			//Assign the userId to the sql query prepared statement
			stmt.setInt(1, userId);
			//Execute the prepared statement query and store it to resultSet
			resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				user = new User();
				user.setUserId(resultSet.getInt(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setUserName(resultSet.getString(4));
				user.setPassword(resultSet.getString(5));
				user.setGender(resultSet.getString(6));
				user.setAddress(resultSet.getString(7));
				user.setPhoneNumber(resultSet.getLong(8));
				user.setEmail(resultSet.getString(9));
				user.setAdminRole(resultSet.getInt(10));
			}
			
			
		}catch(SQLException e){
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		return user;
	}
	
	//Get the user object by user_name and password
	public User loginUser(String userName, String password) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User theUser = null;
		OracleConnection oracle = new OracleConnection();
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.USER_LOGIN);
			//Assign the userName and password to the sql query prepared statement
			stmt.setString(1, userName);
			stmt.setString(2, password);
			//Execute the prepared statement query and store it to resultSet
			resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				theUser = new User();
				theUser.setUserId(resultSet.getInt(1));
				theUser.setFirstName(resultSet.getString(2));
				theUser.setLastName(resultSet.getString(3));
				theUser.setUserName(resultSet.getString(4));
				theUser.setPassword(resultSet.getString(5));
				theUser.setGender(resultSet.getString(6));
				theUser.setAddress(resultSet.getString(7));
				theUser.setPhoneNumber(resultSet.getLong(8));
				theUser.setEmail(resultSet.getString(9));
				theUser.setAdminRole(resultSet.getInt(10));
			}	
			
		}catch(SQLException e){
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		return theUser;
	}
	//Get all the users
	public List<User> getAllUsers() throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<User> usersList = null;
		User user = null;
		OracleConnection oracle = new OracleConnection();
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.GET_USERS);
			resultSet = stmt.executeQuery();
			usersList = new ArrayList<User>();
			
			while(resultSet.next()) {
				user = new User();
				user.setUserId(resultSet.getInt(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setUserName(resultSet.getString(4));
				user.setPassword(resultSet.getString(5));
				user.setGender(resultSet.getString(6));
				user.setAddress(resultSet.getString(7));
				user.setPhoneNumber(resultSet.getLong(8));
				user.setEmail(resultSet.getString(9));
				user.setAdminRole(resultSet.getInt(10));
				//Add to the users list
				usersList.add(user);
			}
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		
		return usersList;
	}
	
	//Update the user
	public int updateUser(User user) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		OracleConnection oracle = new OracleConnection();
		int result = 0;
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.UPDATE_USER);
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getUserName());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, user.getGender());
			stmt.setString(6, user.getAddress());
			stmt.setLong(7, user.getPhoneNumber());
			stmt.setString(8, user.getEmail());
			stmt.setInt(9, user.getUserId());
			
			// execute the update statement
			result = stmt.executeUpdate();
		
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(null, stmt, conn);
		}
		return result;
	}
	
	public int deleteUser(int userId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		OracleConnection oracle = new OracleConnection();
		int result = 0;
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.DELETE_USER);
			stmt.setInt(1, userId);
			// execute the update statement
			result = stmt.executeUpdate();
		
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(null, stmt, conn);
		}
		return result;
	}
	public static void main(String[] args) throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		UserDAO DAO = new UserDAO();
		User user = DAO.getUser("user");
		System.out.println(user.getFirstName() + "|" + user.getLastName() + "|" + user.getGender() + "|" + user.getUserName() + "|" + user.getPassword() + "|" + user.getEmail());
		
		User theUser = DAO.loginUser("user", "user1");
		System.out.println(theUser);
		
		User newUser = new User();
		newUser.setFirstName("Hafizur");
		newUser.setLastName("Rahman");
		newUser.setUserName("hafizur");
		newUser.setPassword("password");
		newUser.setGender("M");
		newUser.setAddress("3506 73rd St Jackson Heights");
		newUser.setPhoneNumber(3475278509L);
		newUser.setEmail("hafizur@restaurant.org");
		
		int userId = DAO.createUser(newUser);
		System.out.println("Last created user id is: " + userId);
//		DAO.updateUser(newUser);
		
		List<User> users = DAO.getAllUsers();
		System.out.println(users);
	}
}
