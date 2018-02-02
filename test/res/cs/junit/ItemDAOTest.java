package res.cs.junit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.core.Every;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import res.cs.dao.ItemDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;

public class ItemDAOTest {
	private ItemDAO itemDAO;
	private Item theItem;
	private boolean isCreated;
	private boolean isUpdated;
	private int itemId;
	
	@Before
	public void initialize() {
		itemDAO = new ItemDAO();
		theItem = null;
		isCreated = false;
		isUpdated = false;
		itemId = 0;
	}
	
	// Verify the createItem process
	@Test
	public void createItemTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		// itemName, itemPrice, itemDescription, image, active, category
		theItem = new Item("Salads", 12.99, "Salad appeals to those customers looking to eat healthier.", "salads.jpg", 1, "Salad");
		itemId = itemDAO.createItem(theItem);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (itemId != 0);
		assertThat(isCreated , equalTo(true));
	}
	
	// Verify the update an item process
	@Test
	public void updateItemTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		//Preserve the original item
		theItem = itemDAO.getItem(41);
		//Create a brand new item with the updated info
		Item newItem = new Item(41, "Salads", 10.99, "Salad appeals to those customers looking to eat healthier.", "salads.jpg", 1, "Salad");
		//Update the item with new information
		int actual = itemDAO.updateItem(newItem);
		//Equivalent to isUpdated = (actual != 0) ? true : false
		isUpdated = (actual != 0);
		
		assertThat(actual , equalTo(1));
	}
	
	@Test
	public void getItemTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Item actual = itemDAO.getItem(41);
		// Verify the correct class type
		assertThat(actual, IsInstanceOf.instanceOf(Item.class));
		assertThat(actual.getItemName(), equalTo("Greek Salad"));
	}
	
	// Verify that Admin can get all the available items
	@Test
	public void getAllItemsTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Item> itemsList = itemDAO.getAllItems();
		// Very the list size
		assertThat(itemsList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(itemsList, Every.everyItem(IsInstanceOf.instanceOf(Item.class)));
		// Verify that it has the itemName as a property 
		assertThat(itemsList, Every.everyItem(HasProperty.hasProperty("itemName")));
		// Check the specific first name in the users list
		assertThat(itemsList, hasItem(Matchers.hasProperty("itemName", equalTo("Greek Salad"))));
		assertThat(itemsList, hasItem(Matchers.hasProperty("itemPrice", equalTo(12.99))));
	}
	
	// Verify that invalid data returns expected result
	@Test
	public void deleteItemTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = itemDAO.deleteItem(87);
		assertThat(actual, equalTo(0));	
	}
	
	@After
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			itemDAO.deleteItem(itemId);
		}
		if(isUpdated) {
			itemDAO.updateItem(theItem);
		}
	}	
}
