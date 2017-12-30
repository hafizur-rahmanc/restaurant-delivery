package res.cs.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import res.cs.exception.RegistrationException;
import res.cs.model.Store;

public class StoreDAOTest {
	private StoreDAO storeDAO;
	private Store theStore;
	private boolean isCreated;
	private boolean isUpdated;
	private int storeId;
	
	@BeforeMethod
	public void initialize() {
		storeDAO = new StoreDAO();
		theStore = null;
		isCreated = false;
		isUpdated = false;
		storeId = 0;
	}
	
	@DataProvider(name="newStore")
	public Object[][] inputData(){
		Object[][] data = {
				{"Pizza Hutt", "9 West 57th Ave", "New York", 30, 10029, "pizzahutt.jpg", true},
				{"Joanne Tratoria", "70 W 68th St", "New York", 20, 10023, "joannetrattoria.jpg", true},
				{"Wasabi Point", "7618 Woodside Ave", "Elmhurst", 20, 11373, "wasabipoint.jpg", true}
		};
		return data;	
	}
	
	@Test(dataProvider="newStore")
	public void createUserTest(String storeName, String address, String city, int staffNumber, int zipcode, String image, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		theStore = new Store(storeName, address, city, staffNumber, zipcode, image);
		storeId = storeDAO.createStore(theStore);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (storeId != 0);
		assertThat(isCreated , equalTo(expected));
		
	}
	
	@DataProvider(name="getStore")
	public Object[][] sampleData(){
		Object[][] data = {
				{21, "Pizza Hutt"},
				{22, "Joanne Tratoria"},
				{23, "Wasabi Point"}
		};
		return data;	
	}
	
	@Test(dataProvider="getStore")
	public void getStoreTest(int storeId, String expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Store actual = storeDAO.getStore(storeId);
		assertThat(actual.getStoreName(), equalTo(expected));
	}
	
	
	@DataProvider(name="getAllStores")
	public Object[][] Data(){
		Object[][] data = {
				{"Pizza Hutt", true},
				{"Joanne Tratoria", true},
				{"Wasabi Point", true},
				{"Dominos", false}
		};
		return data;	
	}
	
	@Test(dataProvider="getAllStores")
	public void getAllStoresTest(String storeName, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		List<Store> storesList = storeDAO.getAllStores();
		boolean actual = false;
		for(Store theStore : storesList) {
			if(storeName.equals(theStore.getStoreName())) {
				actual = true;
				break;
			}
		}
		
		assertThat(actual, equalTo(expected));	
	}
	
	@DataProvider(name="updateStore")
	public Object[][] storeData(){
		Object[][] data = {
				{21, "Pizza Hutt", "9 West 57th Ave", "New York", 30, 10029, "pizzaHutt.jpg", 1},
				{22, "Joanne Tratoria", "70 W 68th St", "New York", 20, 10023, "joanneTratoria.jpg", 1},
				{23, "Wasabi Point", "7618 Woodside Ave", "Elmhurst", 20, 11373, "wasabiPoint.jpg", 1}
		};
		return data;	
	}
	
	@Test(dataProvider="updateStore")
	public void updateStoreTest(int storeId, String storeName, String address, String city, int staffNumber, int zipcode, String image, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		// preserve the original store
		theStore = storeDAO.getStore(storeId);
		// create a new store
		Store newStore = new Store(storeId, storeName, address, city, staffNumber, zipcode, image);
		
		int actual = storeDAO.updateStore(newStore);
		isUpdated = (actual != 0);
		
		assertThat(actual, equalTo(expected));
		
	}
	
	@DataProvider(name="deleteStore")
	public Object[][] createData(){
		Object[][] data = {
				{81, 0}, // not available case
				{87, 0} // not available case
		};
		return data;	
	}
	
	@Test(dataProvider="deleteStore")
	public void deleteStoreTest(int storeId, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = storeDAO.deleteStore(storeId);;
		assertThat(actual, equalTo(expected));	
	}
	
	
	@AfterMethod
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			storeDAO.deleteStore(storeId);
		}
		if(isUpdated) {
			storeDAO.updateStore(theStore);
		}
	}
}
