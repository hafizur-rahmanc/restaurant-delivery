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
import res.cs.model.Order;
import res.cs.model.Store;
import res.cs.util.OracleSqlQueries;

public class OrderDAO {
	//Before creating an order make sure Order object has all the necessary attributes
	public int createOrder(Order order, Set<Integer> itemIds) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int orderId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		String[] columns = {"order_id"};
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_ORDER, columns);
			//Fill out the '?' in the SQL query string
			stmt.setInt(1, order.getUserId());
			stmt.setInt(2, order.getStoreId());
			// assume that we already created a payment entry by this user 
			stmt.setInt(3, order.getPaymentId());
			stmt.setDouble(4, order.getSubtotal());
			stmt.setDouble(5, order.getTaxAmount());
			stmt.setDouble(6, order.getTotalPrice());
			//For the addition of the new item
			stmt.executeUpdate();
			//Retrieve any auto generated keys created as a result of executing this statement
			resultSet = stmt.getGeneratedKeys();
			if(resultSet.next()) {
				orderId = resultSet.getInt(1);
			}
			stmt.close();
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_ORDER_ITEMS);
			for(Integer itemId : itemIds) {
				stmt.setInt(1, orderId );
				stmt.setInt(2, itemId);
				stmt.executeUpdate();
			}
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		
		return orderId;
	}
	
	//This method can be used later since create method already take care the function of this method
	//Method adds an item to an order and returns whether or not the update succeeded
	public int addItemToOrder(int orderId, int itemId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int orderItemId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String[] columns = {"order_item_id"};
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_ORDER_ITEMS, columns);
			//Fill out the '?' in the SQL query string
			stmt.setInt(1, orderId);
			stmt.setInt(2, itemId);
			stmt.executeUpdate();
			//Retrieve any auto generated keys created as a result of executing the above statement
			resultSet = stmt.getGeneratedKeys();
			if(resultSet.next()) {
				orderItemId = resultSet.getInt(1);
			}
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		
		return orderItemId;
	}
	//Close all the opened connections
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
	
	// Get list of orders by user id
	public List<Order> getOrdersByUserId(int userId) throws RegistrationException, SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Order singleOrder = null;
		List<Order> ordersList = null;
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.GET_ORDERS_BY_USERID);
			stmt.setInt(1, userId);
			resultSet = stmt.executeQuery();
			ordersList = new ArrayList<Order>();
			while(resultSet.next()) {
				singleOrder = new Order();
				singleOrder.setOrderId(resultSet.getInt(1));
				singleOrder.setUserId(resultSet.getInt(2));
				singleOrder.setStoreId(resultSet.getInt(3));
				singleOrder.setPaymentId(resultSet.getInt(4));
				singleOrder.setSubtotal(resultSet.getDouble(5));
				singleOrder.setTaxAmount(resultSet.getDouble(6));
				singleOrder.setTotalPrice(resultSet.getDouble(7));
				
				//Add to the orders list
				ordersList.add(singleOrder);
			}
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		
		return ordersList;
	}
	
	// Get the order items by orderId
	public List<Item> getOrderItemsByOrderId(int orderId) throws RegistrationException, SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Item> itemsList = null;
		Item singleItem = null;
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.GET_ORDER_ITEMS_BY_ORDERID);
			stmt.setInt(1, orderId);
			resultSet = stmt.executeQuery();
			itemsList = new ArrayList<Item>();
			// Loop through the resultSet and add items to the itemsList
			while(resultSet.next()) {
				singleItem = new Item();
				singleItem.setItemId(resultSet.getInt(1));
				singleItem.setItemName(resultSet.getString(2));
				singleItem.setItemPrice(resultSet.getDouble(3));
				singleItem.setItemDescription(resultSet.getString(4));
				singleItem.setImage(resultSet.getString(5));
				
				// Add to the items list
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
	
	// Get the order receipt summary by using order id
	public Order getReceiptSummary(int orderId) throws SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Order theOrder = null;
		Store theStore = null;
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.GET_RECEIPT_SUMMARY_BY_ORDERID);
			stmt.setInt(1, orderId);
			resultSet = stmt.executeQuery();
			// Loop through the resultSet and populate the order receipt summary
			// subtotal, taxtAmount, totalPrice, storeName, address, city, zipcode 
			while(resultSet.next()) {
				theOrder = new Order();
				theStore = new Store();
				theOrder.setOrderId(orderId);
				theOrder.setSubtotal(resultSet.getDouble(1));
				theOrder.setTaxAmount(resultSet.getDouble(2));
				theOrder.setTotalPrice(resultSet.getDouble(3));
				
				// Store information
				theStore.setStoreName(resultSet.getString(4));
				theStore.setAddress(resultSet.getString(5));
				theStore.setCity(resultSet.getString(6));
				theStore.setZipcode(resultSet.getInt(7));
				
				// Assign the store object to the current order object
				theOrder.setStore(theStore);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet, stmt, conn);
		}
		return theOrder;
	}
	
	
	// Retrieve all the orders
	public List<Order> getAllOrders() throws RegistrationException, SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Order> ordersList = null;
		Order singleOrder = null;
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.GET_ORDERS);
			resultSet = stmt.executeQuery();
			ordersList = new ArrayList<Order>();
			
			while(resultSet.next()) {
				singleOrder = new Order();
				singleOrder.setOrderId(resultSet.getInt(1));
				singleOrder.setUserId(resultSet.getInt(2));
				singleOrder.setStoreId(resultSet.getInt(3));
				singleOrder.setPaymentId(resultSet.getInt(4));
				singleOrder.setSubtotal(resultSet.getDouble(5));
				singleOrder.setTaxAmount(resultSet.getDouble(6));
				singleOrder.setTotalPrice(resultSet.getDouble(7));
				
				//Add to the orders list
				ordersList.add(singleOrder);
			}
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		
		return ordersList;
	}
	
	// Admin can delete an order by its order id
	public int deleteOrder(int orderId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.DELETE_ORDER);
			//Fill out the '?' in the SQL query string
			stmt.setInt(1, orderId);
			result = stmt.executeUpdate();
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(null, stmt, conn);
		}
		
		return result;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		OrderDAO orderDAO = new OrderDAO();
		Order theOrder = orderDAO.getReceiptSummary(57);
		System.out.println("Order Id: " + theOrder.getOrderId() + " Subtotal: " + theOrder.getSubtotal() + " Tax Amount: "
				+ "" + theOrder.getTaxAmount() + " Grand Total: " + theOrder.getTotalPrice());
	}
}
