package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import res.cs.dao.OrderDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Order;

public class OrderBO {
	//Instantiate the orderDAO for this instance of this BO
	OrderDAO orderDAO = new OrderDAO();

	//Corresponds with OrderDAO.createOrder(); all the parameters are coming from the session; assume payment is created
	public int createOrder(Set<Integer> itemIds, int userId, int storeId, int paymentId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Order theOrder = new Order();
		ItemBO itemBO = new ItemBO();
		
		//Get the calculated sub-totals, tax amount and grand total prices
		List<Double> getTotals = itemBO.getTotals(itemIds);
		
		theOrder.setUserId(userId);
		theOrder.setStoreId(storeId);
		theOrder.setPaymentId(paymentId);
		//Pre Tax amount
		System.out.println("Subtotal = $" + getTotals.get(0));
		theOrder.setSubtotal(getTotals.get(0));
		//Tax amount
		System.out.println("Tax = $" + getTotals.get(1));
		theOrder.setTaxAmount(getTotals.get(1));
		//Grand total
		System.out.println("Grand Total = $" + getTotals.get(2));
		theOrder.setTotalPrice(getTotals.get(2));
		
		//Method creates an order and returns the order's id
		int id = orderDAO.createOrder(theOrder, itemIds);
		return id;
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
		Set<Integer> itemIds = new HashSet<Integer>();
		itemIds.add(41);
		itemIds.add(42);
		itemIds.add(43);
		int orderId = orderBO.createOrder(itemIds, 2, 21, 3);
		System.out.println("Recently Created order id is: " + orderId);
	}
}
