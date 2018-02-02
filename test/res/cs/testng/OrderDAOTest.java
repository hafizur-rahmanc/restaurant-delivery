package res.cs.testng;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import org.hamcrest.collection.IsEmptyCollection;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.core.Every;
import org.hamcrest.core.IsInstanceOf;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import res.cs.bo.ItemBO;
import res.cs.dao.OrderDAO;
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
				{new HashSet<Integer>(Arrays.asList(41, 42, 43)), 90, 1, 1, true},
				{new HashSet<Integer>(Arrays.asList(1, 41, 42, 43)), 91, 21, 2, true},
				{new HashSet<Integer>(Arrays.asList(21, 43)), 92, 22, 3, true}
		};
		return data;	
	}
	
	// Verify that new order create process is working as expected
	@Test(dataProvider="newOrder")
	public void createOrderTest(Set<Integer> itemIds, int userId, int storeId, int paymentId, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
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
				{2, 23.93, true},
				{20, 20.66, false}, // user is not in the users list
				{87, 15.66, false}
		};
		return data;	
	}
	
	// Verify users past orders
	@Test(dataProvider="getOrdersByUser")
	public void getOrdersByUserTest(int userId, double totalPrice , boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Order> ordersList = orderDAO.getOrdersByUserId(userId);
		if(expected) {
			// Very the list size
			assertThat(ordersList.size(), Matchers.greaterThan(0));
			// Verify the correct class type
			assertThat(ordersList, Every.everyItem(IsInstanceOf.instanceOf(Order.class)));
			// Verify that it has the totalPrice as a property 
			assertThat(ordersList, Every.everyItem(HasProperty.hasProperty("totalPrice")));
			// Check the specific totalPrice in the orders list
			assertThat(ordersList, hasItem(Matchers.hasProperty("totalPrice", equalTo(totalPrice))));
		} else {
			assertThat(ordersList, IsEmptyCollection.emptyCollectionOf(Order.class));
		}
	}
	
	@DataProvider(name="getAllOrders")
	public Object[][] createData(){
		Object[][] data = {
				//orderId, expected
				{23.93, true},
				{100, false}
		};
		return data;	
	}
	
	// Verify that Admin can retrieve all the orders correctly
	@Test(dataProvider="getAllOrders")
	public void getAllOrdersTest(double totalPrice, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Order> ordersList = orderDAO.getAllOrders();
		// Very the list size
		assertThat(ordersList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(ordersList, Every.everyItem(IsInstanceOf.instanceOf(Order.class)));
		// Verify that it has the totalPrice as a property 
		assertThat(ordersList, Every.everyItem(HasProperty.hasProperty("totalPrice")));
		if(expected) {
			assertThat(ordersList, hasItem(Matchers.hasProperty("totalPrice", equalTo(totalPrice))));
		}
	}
	
	@DataProvider(name="deleteOrder")
	public Object[][] Data(){
		Object[][] data = {
				{81, 0}, // not available case
				{87, 0} // not available case
		};
		return data;	
	}
	
	// Verify that invalid data returns expected result
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
