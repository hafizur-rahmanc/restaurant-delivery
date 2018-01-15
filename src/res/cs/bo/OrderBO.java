package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import res.cs.dao.OrderDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;
import res.cs.model.Order;

public class OrderBO {
	//Instantiate the orderDAO for this instance of this BO
	OrderDAO orderDAO = new OrderDAO();

	//Corresponds with OrderDAO.createOrder(); all the parameters are coming from the session; assume payment is created
	public int createOrder(Order theOrder, Set<Integer> itemIds) throws ClassNotFoundException, IOException, RegistrationException, SQLException {		
		// Method creates an order and returns the order's id
		int orderId = 0;
		orderId = orderDAO.createOrder(theOrder, itemIds);
		return orderId;
	}
	
	//Corresponds with OrderDAO.addItemToOrder()
	public int addItemToOrder(int orderId, int itemId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		//Method adds an item to an order and returns whether or not the update succeeded
		int result = orderDAO.addItemToOrder(orderId, itemId);
		return result;
	}
	
	//Corresponds with OrderDAO.getOrderByUserId()
	public List<Order> getOrdersByUserId(int userId) throws RegistrationException, SQLException{
		//Method returns all orders that user has made
		List<Order> ordersList = orderDAO.getOrdersByUserId(userId);
		return ordersList;
	}
	
	// Get all the order items by using the order id
	public List<Item> getOrderItemsByOrderId(int orderId) throws RegistrationException, SQLException{
		List<Item> orderItems = orderDAO.getOrderItemsByOrderId(orderId);
		return orderItems;
	}
	
	// Get the ReceiptSummary by using the order id
	public Order getReceiptSummary(int orderId) throws SQLException, ClassNotFoundException, IOException{
		Order receiptSummary = orderDAO.getReceiptSummary(orderId);
		return receiptSummary;
	}
	//Corresponds with Order.getAllOrders()
	public List<Order> getAllOrders() throws RegistrationException, SQLException{
		//Method returns all orders in user's history
		List<Order> ordersList = orderDAO.getAllOrders();
		return ordersList;
	}
	
	//Corresponds with OrdeDAO.removeOrder()
	public int deleteOrder(int orderId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int result = orderDAO.deleteOrder(orderId);
		return result;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		OrderBO orderBO = new OrderBO();
		Order theOrder = new Order();
		theOrder.setUserId(2);
		theOrder.setStoreId(21);
		theOrder.setPaymentId(3);
		theOrder.setSubtotal(41.97);
		theOrder.setTaxAmount(3.72);
		theOrder.setTotalPrice(45.69);
		
		Set<Integer> itemIds = new HashSet<Integer>();
		itemIds.add(41);
		itemIds.add(42);
		itemIds.add(43);
		int orderId = orderBO.createOrder(theOrder, itemIds);
		System.out.println("Newly Created order id is: " + orderId);
	}
}
