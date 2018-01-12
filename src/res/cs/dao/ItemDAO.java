package res.cs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import res.cs.exception.RegistrationException;
import res.cs.model.Item;
import res.cs.util.OracleSqlQueries;

public class ItemDAO {
	//Create a new item and save it to the database
	public int createItem(Item item) throws RegistrationException, ClassNotFoundException, IOException, SQLException {
		int itemId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		String[] columns = {"item_id"};
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_ITEM, columns);
			//Fill out the '?' in the SQL query string
			stmt.setString(1, item.getItemName());
			stmt.setDouble(2, item.getItemPrice());
			stmt.setString(3, item.getItemDescription());
			stmt.setString(4, item.getImage());
			stmt.setInt(5, item.getActive());
			stmt.setString(6, item.getCategory());
			//For the addition of the new item
			stmt.executeUpdate();
			//Retrieve any auto generated keys created as a result of executing this statement
			resultSet = stmt.getGeneratedKeys();
			if(resultSet.next()) {
				itemId = resultSet.getInt(1);
			}
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		
		return itemId;
	}
	
	//Close all the open connections
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

	//Get the item object by the item id
	public Item getItem(int itemId) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Item item = null;
		OracleConnection oracle = new OracleConnection();
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.GET_ITEM);
			//Assign the itemId to the sql query prepared statement
			stmt.setInt(1, itemId);
			//Execute the prepared statement query and store it to result
			resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				item = new Item();
				item.setItemId(resultSet.getInt(1));
				item.setItemName(resultSet.getString(2));
				item.setItemPrice(resultSet.getDouble(3));
				item.setItemDescription(resultSet.getString(4));
				item.setImage(resultSet.getString(5));
				item.setActive(resultSet.getInt(6));
				item.setCategory(resultSet.getString(7));
			}
			
		}catch(SQLException e){
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		return item;
	}
	
	public List<Item> getCartItems(Set<Integer> cartIds) throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		List<Item> itemsList = new ArrayList<Item>();
		// Loop through the cartIds to find each item
		for(Integer id: cartIds) {
			// Get the item by id and add to the list 
			itemsList.add(getItem(id));
		}
		// Return the populated list
		return itemsList;
	}
	
	//Retrieve all the items
	public List<Item> getAllItems() throws RegistrationException, SQLException, ClassNotFoundException, IOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Item> itemsList = null;
		Item singleItem = null;
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.GET_ITEMS);
			resultSet = stmt.executeQuery();
			itemsList = new ArrayList<Item>();
			
			while(resultSet.next()) {
				singleItem = new Item();
				singleItem.setItemId(resultSet.getInt(1));
				singleItem.setItemName(resultSet.getString(2));
				singleItem.setItemPrice(resultSet.getDouble(3));
				singleItem.setItemDescription(resultSet.getString(4));
				singleItem.setImage(resultSet.getString(5));
				singleItem.setActive(resultSet.getInt(6));
				singleItem.setCategory(resultSet.getString(7));
				
				//Add to the items list
				itemsList.add(singleItem);
			}
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		
		return itemsList;
	}
	
	//Update an item
	public int updateItem(Item item) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		OracleConnection oracle = new OracleConnection();
		int result = 0;
		
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.UPDATE_ITEM);
			stmt.setString(1, item.getItemName());
			stmt.setDouble(2, item.getItemPrice());
			stmt.setString(3, item.getItemDescription());
			stmt.setString(4, item.getImage());
			stmt.setInt(5, item.getActive());
			stmt.setString(6, item.getCategory());
			stmt.setInt(7, item.getItemId());
			
			//Execute the update statement
			result = stmt.executeUpdate();
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(null, stmt, conn);
		}
		return result;
	}
	
	
	// Admin can delete an item by using the item_id
	public int deleteItem(int itemId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		OracleConnection oracle = new OracleConnection();
		int result = 0;
		
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.REMOVE_ITEM);
			// set the corresponding parameter
			stmt.setInt(1, itemId);
			// execute the delete statement
			result = stmt.executeUpdate();
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(null, stmt, conn);
		}
		return result;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		ItemDAO DAO = new ItemDAO();
		
		Item newItem = new Item();
//		newItem.setItemId(1);
		newItem.setItemName("Ham burger");
		newItem.setItemPrice(10.99);
		newItem.setItemDescription("Tasty 11 Pie!!");
		newItem.setImage("pie.jpg");
		newItem.setActive(1);
		newItem.setCategory("Pie");
		newItem.setInventory(15);
		
//		DAO.updateItem(newItem);
//		
		int itemId = DAO.createItem(newItem);
		System.out.println("Last created item id is: " + itemId);
		
//		User user = DAO.getUser("user");
//		System.out.println(user.getFirstName() + "|" + user.getLastName() + "|" + user.getGender() + "|" + user.getUserName() + "|" + user.getPassword() + "|" + user.getEmail());
//		DAO.updateUser(newUser);
//		
		List<Item> items = DAO.getAllItems();
		System.out.println(items);
	}

}
