package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import res.cs.dao.StoreDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Store;

public class StoreBO {
	// Instantiate the storeDAO for this instance of this BO
	final StoreDAO storeDAO = new StoreDAO();
	
	// Create a new store using the StoreDAO createStore method
	public int createStore(Store store) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Integer storeId = null;
		try {
			storeId = storeDAO.createStore(store);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return storeId;
	}
	
	// Get the Store object by store id using the StoreDAO
	public Store getItem(int ID) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Store theStore = null;
		try {
			theStore = storeDAO.getStore(ID);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return theStore;
	}
	
	// Get all the available stores using the StoreDAO
	public List<Store> getAllStores() throws RegistrationException, SQLException, ClassNotFoundException, IOException {
		List<Store> storesList = null;
		try {
			storesList = storeDAO.getAllStores();
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return storesList;
	}
	
	// Update the store using the StoreDAO
	public int updateStore(Store store) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int result = 0;
		try {
			result = storeDAO.updateStore(store);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return result;
	}
	
	// Admin can delete a store by using the store_id
	public int deleteStore(int storeId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int result = storeDAO.deleteStore(storeId);
		return result;
	}
	
}
