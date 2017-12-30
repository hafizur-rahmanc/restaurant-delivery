package res.cs.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
				{41, "Salads"},
				{42, "Simple Pie"},
				{43, "Egg Dishes"}
		};
		return data;	
	}
	
	@Test(dataProvider="getItem")
	public void getItemTest(int itemId, String expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Item actual = itemDAO.getItem(itemId);
		assertThat(actual.getItemName(), equalTo(expected));
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
