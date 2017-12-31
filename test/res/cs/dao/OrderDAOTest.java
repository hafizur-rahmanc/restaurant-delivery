package res.cs.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import res.cs.bo.ItemBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Order;

public class OrderDAOTest {
	private OrderDAO orderDAO;
	private Order theOrder;
	private boolean isCreated;
	private int orderId;
	
	@BeforeMethod
	public void initialize() {
		orderDAO = new OrderDAO();
		theOrder = null;
		isCreated = false;
		orderId = 0;
	}
	
	@DataProvider(name="newOrder")
	public Object[][] inputData(){
		Object[][] data = {
				// itemIds, userId, storeId, paymentId, expected
				{new ArrayList<Integer>(Arrays.asList(41, 42, 43)), 90, 1, 1, true},
				{new ArrayList<Integer>(Arrays.asList(1, 41, 42, 43)), 91, 21, 2, true},
				{new ArrayList<Integer>(Arrays.asList(21, 43)), 92, 22, 3, true}
		};
		return data;	
	}
	
	@Test(dataProvider="newOrder")
	public void createOrderTest(List<Integer> itemIds, int userId, int storeId, int paymentId, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		theOrder = new Order();
		ItemBO itemBO = new ItemBO();
		
		//Get the calculated sub-totals, tax amount and grand total prices
		List<Double> getTotals = itemBO.getTotals(itemIds);
		
		theOrder.setUserId(userId);
		theOrder.setStoreId(storeId);
		theOrder.setPaymentId(paymentId);
		//PreTax amount
		System.out.println("Subtotal = $" + getTotals.get(0));
		theOrder.setSubtotal(getTotals.get(0));
		//Tax amount
		System.out.println("Tax = $" + getTotals.get(1));
		theOrder.setTaxAmount(getTotals.get(1));
		//Grand total
		System.out.println("Grand Total = $" + getTotals.get(2));
		theOrder.setTotalPrice(getTotals.get(2));
		
		//Method creates an order and returns the order's id
		orderId = orderDAO.createOrder(theOrder, itemIds);
		
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (orderId != 0);
		assertThat(isCreated , equalTo(expected));
	}
	
	@DataProvider(name="getOrdersByUser")
	public Object[][] sampleData(){
		Object[][] data = {
				//userId, totalPrice, expected
				{2, 34.81, true},
				{91, 47.86, true},
				{2, 20.66, false}
		};
		return data;	
	}
	
	@Test(dataProvider="getOrdersByUser")
	public void getOrdersByUserTest(int userId, double totalPrice , boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Order> ordersList = orderDAO.getOrdersByUserId(userId);
		boolean actual = false;
		for(Order theOrder : ordersList) {
			if(totalPrice == theOrder.getTotalPrice()) {
				actual = true;
				break;
			}
		}
		assertThat(actual, equalTo(expected));
	}
	
	@DataProvider(name="getAllOrders")
	public Object[][] createData(){
		Object[][] data = {
				//orderId, expected
				{13, true},
				{14, true},
				{100, false}
		};
		return data;	
	}
	
	@Test(dataProvider="getAllOrders")
	public void getAllOrdersTest(int orderId, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Order> ordersList = orderDAO.getAllOrders();
		boolean actual = false;
		for(Order theOrder : ordersList) {
			if( orderId == theOrder.getOrderId()) {
				actual = true;
				break;
			}
		}
		assertThat(actual, equalTo(expected));
	}
	
	@DataProvider(name="deleteOrder")
	public Object[][] Data(){
		Object[][] data = {
				{81, 0}, // not available case
				{87, 0} // not available case
		};
		return data;	
	}
	
	@Test(dataProvider="deleteOrder")
	public void deletOrderTest(int orderId, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = orderDAO.deleteOrder(orderId);;
		assertThat(actual, equalTo(expected));	
	}
	
	@AfterMethod
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			orderDAO.deleteOrder(orderId);
		}
	}
}
