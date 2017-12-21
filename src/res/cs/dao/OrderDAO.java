package res.cs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import res.cs.exception.RegistrationException;
import res.cs.model.Order;
import res.cs.util.OracleSqlQueries;

public class OrderDAO {
	//Before creating an order make sure Order object has all the necessary attributes
	public int createOrder(Order order, List<Integer> itemIds) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int orderId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
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
			result = stmt.getGeneratedKeys();
			if(result.next()) {
				orderId = result.getInt(1);
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
			stmt.close();
			result.close();
			conn.close();
		}
		
		return orderId;
	}
	
	//This method can be used later since create method already take care the function of this method
	//Method adds an item to an order and returns whether or not the update succeeded
	public int addItemToOrder(int orderId, int itemId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int orderItemId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String[] columns = {"order_item_id"};
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_ORDER_ITEMS, columns);
			//Fill out the '?' in the SQL query string
			stmt.setInt(1, orderId);
			stmt.setInt(2, itemId);
			stmt.executeUpdate();
			//Retrieve any auto generated keys created as a result of executing this statement
			result = stmt.getGeneratedKeys();
			if(result.next()) {
				orderItemId = result.getInt(1);
			}
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			result.close();
			stmt.close();
			conn.close();
		}
		
		return orderItemId;
	}
	
	public List<Order> getOrdersByUserId(int userId) throws RegistrationException{
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
			try {
				resultSet.close();
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				
			}
		}
		
		return ordersList;
	}
	
	public List<Order> getAllOrders() throws RegistrationException{
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
			try {
				resultSet.close();
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				
			}
		}
		
		return ordersList;
	}
	
	public boolean removeOrder(int orderId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		boolean status = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.REMOVE_ORDER);
			//Fill out the '?' in the SQL query string
			stmt.setInt(1, orderId);
			stmt.executeUpdate();
			status = true;
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			stmt.close();
			conn.close();
		}
		
		return status;
	}
}
