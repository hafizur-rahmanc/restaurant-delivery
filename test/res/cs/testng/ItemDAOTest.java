package res.cs.testng;

import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.hasItem;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.core.Every;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import res.cs.dao.ItemDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;

public class ItemDAOTest {
	private ItemDAO itemDAO;
	private Item theItem;
	private boolean isCreated;
	private boolean isUpdated;
	private int itemId;
	
	@BeforeMethod
	public void initialize() {
		itemDAO = new ItemDAO();
		theItem = null;
		isCreated = false;
		isUpdated = false;
		itemId = 0;
	}
	
	@DataProvider(name="newItem")
	public Object[][] inputData(){
		Object[][] data = {
				{"Salads", 12.99, "Salad appeals to those customers looking to eat healthier.", "salads.jpg", 1, "Salad", true},
				{"Simple Pie", 10.99, "Most of the people don’t feel like they have the time to whip up an apple pie.", "simplepie.jpg", 1, "Pie", true},
				{"Egg Dishes", 7.99, "Eggs are the cornerstone of any breakfast menu.", "eggs.jpg", 1, "Egg", true}
		};
		return data;	
	}
	
	// Verify the createItem process
	@Test(dataProvider="newItem")
	public void createItemTest(String itemName, Double itemPrice, String itemDescription, String image, int active, String category, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		theItem = new Item(itemName, itemPrice, itemDescription, image, active, category );
		itemId = itemDAO.createItem(theItem);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (itemId != 0);
		assertThat(isCreated , equalTo(expected));
		
	}
	
	@DataProvider(name="getItem")
	public Object[][] sampleData(){
		Object[][] data = {
				{41, "Greek Salad"},
				{42, "Pepperoni Pie"},
				{43, "Classical Salad"}
		};
		return data;	
	}
	
	@Test(dataProvider="getItem")
	public void getItemTest(int itemId, String expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Item actual = itemDAO.getItem(itemId);
		assertThat(actual.getItemName(), equalTo(expected));
	}
	
	@DataProvider(name="getAllItems")
	public Object[][] Data(){
		Object[][] data = {
				{"Greek Salad", 12.99, true},
				{"Plain Pizza Pie", 10.99, true},
				{"Ham Burger", 9.99, true},
				{"Dominos", 12.99, false}
		};
		return data;	
	}
	
	@Test(dataProvider="getAllItems")
	public void getAllItemsTest(String itemName, Double itemPrice, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Item> itemsList = itemDAO.getAllItems();
		// Very the list size
		assertThat(itemsList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(itemsList, Every.everyItem(IsInstanceOf.instanceOf(Item.class)));
		// Verify that it has the itemName as a property 
		assertThat(itemsList, Every.everyItem(HasProperty.hasProperty("itemName")));
		// Check the specific first name in the users list
		if(expected) {
			assertThat(itemsList, hasItem(Matchers.hasProperty("itemName", equalTo(itemName))));
			assertThat(itemsList, hasItem(Matchers.hasProperty("itemPrice", equalTo(itemPrice))));
		}
	}
	
	@DataProvider(name="updateItem")
	public Object[][] itemData(){
		Object[][] data = {
				{41, "Salads", 10.99, "Salad appeals to those customers looking to eat healthier.", "salads.jpg", 1, "Salad", 1},
				{42, "Simple Pie", 11.99, "Most of the people don’t feel like they have the time to whip up an apple pie.", "simplepie.jpg", 1, "Pie", 1},
				{43, "Egg Dishes", 5.99, "Eggs are the cornerstone of any breakfast menu.", "eggs.jpg", 1, "Egg", 1}
		};
		return data;	
	}
	
	// Verify the update an item process
	@Test(dataProvider="updateItem")
	public void updateItemTest(int itemId, String itemName, Double itemPrice, String itemDescription, String image, int active, String category, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		//Preserve the original item
		theItem = itemDAO.getItem(itemId);
		//Create a brand new item with the updated info
		Item newItem = new Item(itemId, itemName, itemPrice, itemDescription, image, active, category );
		//Update the item with new information
		int actual = itemDAO.updateItem(newItem);
		//Equivalent to isUpdated = (actual != 0) ? true : false
		isUpdated = (actual != 0);
		
		assertThat(actual , equalTo(expected));
		
	}
	
	@DataProvider(name="deleteItem")
	public Object[][] createData(){
		Object[][] data = {
				{81, 0}, // not available case
				{87, 0} // not available case
		};
		return data;	
	}
	
	// Verify that invalid data returns expected result 
	@Test(dataProvider="deleteItem")
	public void deleteItemTest(int itemId, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = itemDAO.deleteItem(itemId);
		assertThat(actual, equalTo(expected));	
	}
	
	@AfterMethod
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			itemDAO.deleteItem(itemId);
		}
		if(isUpdated) {
			itemDAO.updateItem(theItem);
		}
	}
}
