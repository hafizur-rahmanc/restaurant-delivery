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

import res.cs.dao.StoreDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Store;

public class StoreDAOTest {
	private StoreDAO storeDAO;
	private Store theStore;
	private boolean isCreated;
	private boolean isUpdated;
	private int storeId;
	
	@Before
	public void initialize() {
		storeDAO = new StoreDAO();
		theStore = null;
		isCreated = false;
		isUpdated = false;
		storeId = 0;
	}
	
	// Verify that Admin can create new locations
	@Test
	public void createUserTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		// storeName, address, city, staffNumber, zipcode, image
		theStore = new Store("Pizza Hutt", "9 West 57th St", "New York", 50, 10020, "pizzahutt.jpg");
		storeId = storeDAO.createStore(theStore);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (storeId != 0);
		assertThat(isCreated , equalTo(true));
	}
	
	// Verify that retrieving a location instance by id is valid 
	@Test
	public void getStoreTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Store actual = storeDAO.getStore(1);
		// Verify the correct class type
		assertThat(actual, IsInstanceOf.instanceOf(Store.class));
		assertThat(actual.getStoreName(), equalTo("Best Food in Town"));
	}
	
	// Verify that we can get all the available stores
	@Test
	public void getAllStoresTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Store> storesList = storeDAO.getAllStores();
		// Very the list size
		assertThat(storesList.size(), Matchers.greaterThan(0));
		// Verify the correct class type
		assertThat(storesList, Every.everyItem(IsInstanceOf.instanceOf(Store.class)));
		// Verify that it has the storeName as a property 
		assertThat(storesList, Every.everyItem(HasProperty.hasProperty("storeName")));
		// Check the specific store name in the stores list
		assertThat(storesList, hasItem(Matchers.hasProperty("storeName", equalTo("Pizza Hutt"))));	
	}
	
	// Verify the update store process 
	@Test
	public void updateStoreTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		// preserve the original store
		theStore = storeDAO.getStore(69);
		// Create a new store
		// new Store(storeId, storeName, address, city, staffNumber, zipcode, image);
		Store newStore = new Store(69, "Pizza Hutt", "9 West 57th ST", "New York", 30, 10029, "pizzaHutt.jpg");
		System.out.println(newStore.getStoreId() + " " + newStore.getAddress()  + " " + newStore.getStaffNumber());
		int actual = storeDAO.updateStore(newStore);
		isUpdated = (actual != 0);
		
		assertThat(actual, equalTo(1));
	}
	
	// Verify that invalid data returns expected result
	@Test
	public void deleteStoreTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = storeDAO.deleteStore(87);;
		assertThat(actual, equalTo(0));	
	}
	
	@After
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			storeDAO.deleteStore(storeId);
		}
		if(isUpdated) {
			storeDAO.updateStore(theStore);
		}
	}
}
