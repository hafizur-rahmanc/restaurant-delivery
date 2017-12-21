package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import res.cs.dao.OrderDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Order;

public class OrderBO {
	//Instantiate the orderDAO for this instance of this BO
	OrderDAO orderDAO = new OrderDAO();
	
	//Corresponds with OrderDAO.createOrder()
	public int createOrder(Order order, List<Integer> itemIds) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		//Method creates an order and returns the order's id
		int id = orderDAO.createOrder(order, itemIds);
		return id;
	}
	
	//Corresponds with OrderDAO.addItemToOrder()
	public int addItemToOrder(int orderId, int itemId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		//Method adds an item to an order and returns whether or not the update succeeded
		int result = orderDAO.addItemToOrder(orderId, itemId);
		return result;
	}
	
	//Corresponds with OrderDAO.getOrderByUserId()
	public List<Order> getOrdersByUserId(int userId) throws RegistrationException{
		//Method returns all orders that user has made
		List<Order> ordersList = orderDAO.getOrdersByUserId(userId);
		return ordersList;
	}
	
	//Corresponds with Order.getAllOrders()
	public List<Order> getAllOrders() throws RegistrationException{
		//Method returns all orders in user's history
		List<Order> ordersList = orderDAO.getAllOrders();
		return ordersList;
	}
	
	//Corresponds with OrdeDAO.removeOrder()
	public boolean removeOrder(int orderId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		boolean result = orderDAO.removeOrder(orderId);
		return result;
	}
}
