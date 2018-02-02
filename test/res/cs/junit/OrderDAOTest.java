package res.cs.junit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.core.Every;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import res.cs.bo.ItemBO;
import res.cs.dao.OrderDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Order;

public class OrderDAOTest {
	private OrderDAO orderDAO;
	private Order theOrder;
	private boolean isCreated;
	private int orderId;
	
	@Before
	public void initialize() {
		orderDAO = new OrderDAO();
		theOrder = null;
		isCreated = false;
		orderId = 0;
	}
	
	// Verify that new order create process is working as expected
	@Test
	public void createOrderTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		theOrder = new Order();
		ItemBO itemBO = new ItemBO();
		
		//Get the calculated sub-totals, tax amount and grand total prices
		List<Double> getTotals = itemBO.getTotals(new HashSet<Integer>(Arrays.asList(41, 42, 43)));
		
		theOrder.setUserId(2);
		theOrder.setStoreId(1);
		theOrder.setPaymentId(1);
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
		orderId = orderDAO.createOrder(theOrder, new HashSet<Integer>(Arrays.asList(41, 42, 43)));
		
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (orderId != 0);
		assertThat(isCreated , equalTo(true));
	}
	
	// Verify users past orders
	@Test
	public void getOrdersByUserTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Order> ordersList = orderDAO.getOrdersByUserId(2);
		// Very the list size
		assertThat(ordersList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(ordersList, Every.everyItem(IsInstanceOf.instanceOf(Order.class)));
		// Verify that it has the totalPrice as a property 
		assertThat(ordersList, Every.everyItem(HasProperty.hasProperty("totalPrice")));
		// Check the specific totalPrice in the orders list
		assertThat(ordersList, hasItem(Matchers.hasProperty("totalPrice", equalTo(23.93))));
	}
	
	// Verify that Admin can retrieve all the orders correctly
	@Test
	public void getAllOrdersTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Order> ordersList = orderDAO.getAllOrders();
		// Very the list size
		assertThat(ordersList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(ordersList, Every.everyItem(IsInstanceOf.instanceOf(Order.class)));
		// Verify that it has the totalPrice as a property 
		assertThat(ordersList, Every.everyItem(HasProperty.hasProperty("totalPrice")));
		assertThat(ordersList, hasItem(Matchers.hasProperty("totalPrice", equalTo(23.93))));
	}
	
	// Verify that invalid data returns expected result
	@Test
	public void deletOrderTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = orderDAO.deleteOrder(87);;
		assertThat(actual, equalTo(0));	
	}
	
	@After
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			orderDAO.deleteOrder(orderId);
		}
	}
}
