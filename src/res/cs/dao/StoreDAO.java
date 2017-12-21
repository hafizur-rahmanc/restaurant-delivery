package res.cs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import res.cs.exception.RegistrationException;
import res.cs.model.Store;
import res.cs.util.OracleSqlQueries;

public class StoreDAO {
	//Create a new store
	public int createStore(Store store) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int storeId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String[] idColumn = {"store_id"};
		OracleConnection oracle = new OracleConnection();
			
			try {
				conn = oracle.getConnection();
				System.out.println("Connection Established!");
				stmt = conn.prepareStatement(OracleSqlQueries.CREATE_STORE, idColumn);
				//Set attribute to the SQL query string
				stmt.setString(1, store.getStoreName());
				stmt.setString(2, store.getAddress());
				stmt.setString(3, store.getCity());
				stmt.setInt(4, store.getZipcode());
				stmt.setInt(5, store.getStaffNumber());
				stmt.setString(6, store.getImage());
				// execute the prepared statement 
				stmt.executeUpdate();
				
				// retrieve any auto generated keys created as a result of executing this statement object
				result = stmt.getGeneratedKeys();
				if(result.next()) {
					storeId = result.getInt(1);
				}
				
			}catch(SQLException e) {
				throw new RegistrationException(e.getMessage());
			}catch(Exception e) {
				throw new RegistrationException(e.getMessage());
			}finally {
				try {
					stmt.close();
					conn.close();
				}catch(SQLException e) {
					
				}
			}
			
			return storeId;
		}
		// get all the  available stores
		public List<Store> getAllStores() throws RegistrationException, SQLException, ClassNotFoundException, IOException {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			List<Store> storesList = null;
			Store singleStore = null;
			OracleConnection oracle = new OracleConnection();
			
			try {
				conn = oracle.getConnection();
				System.out.println("Connection Established!");
				stmt = conn.prepareStatement(OracleSqlQueries.GET_STORES);
				// execute the Query statement 
				resultSet = stmt.executeQuery();
				storesList = new ArrayList<Store>();
				
				while(resultSet.next()) {
					singleStore = new Store();
					singleStore.setStoreId(resultSet.getInt(1));
					singleStore.setStoreName(resultSet.getString(2));
					singleStore.setAddress(resultSet.getString(3));
					singleStore.setCity(resultSet.getString(4));
					singleStore.setZipcode(resultSet.getInt(5));
					singleStore.setStaffNumber(resultSet.getInt(6));
					singleStore.setImage(resultSet.getString(7));
					
					// add to the stores list
					storesList.add(singleStore);
				}
				
			}catch(SQLException e) {
				throw new RegistrationException(e.getMessage());
			}catch(Exception e) {
				throw new RegistrationException(e.getMessage());
			}finally {
				try {
					resultSet.close();
					stmt.close();
					conn.close();
				}catch(SQLException e) {
					
				}
			}
			return storesList;
		}
		
		// admin can remove a store by using the store_id
		public void removeStore(int storeId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
			Connection conn = null;
			PreparedStatement stmt = null;
			OracleConnection oracle = new OracleConnection();
			
			try {
				conn = oracle.getConnection();
				System.out.println("Connection Established!");
				stmt = conn.prepareStatement(OracleSqlQueries.REMOVE_STORE);
				// set the corresponding parameter
				stmt.setInt(1, storeId);
				// execute the delete statement
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new RegistrationException(e.getMessage());
			}catch(Exception e) {
				throw new RegistrationException(e.getMessage());
			}finally {
				try {
					stmt.close();
					conn.close();
				}catch(SQLException e) {
					
				}
			}
		}
		
		public static void main(String[] args) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
			StoreDAO DAO = new StoreDAO();
			Store store = new Store();
			store.setStoreName("Best Food in Town");
			store.setAddress("812 Maple St");
			store.setCity("Brooklyn");
			store.setZipcode(11208);
			store.setStaffNumber(50);
			store.setImage("store.jpg");
			
			//int id = DAO.createStore(store);
			//System.out.println("Last created store id was: " + id);
			System.out.println(DAO.getAllStores());
		}

}
