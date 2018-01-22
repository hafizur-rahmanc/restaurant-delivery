package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import res.cs.dao.ItemDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;

public class ItemBO {
	//Instantiate the itemDAO for this instance of this BO
	final ItemDAO itemDAO = new ItemDAO();
	
	//Create a new item and save it to the database using ItemDAO
	public int createItem(Item item) throws RegistrationException, ClassNotFoundException, IOException, SQLException {
		Integer itemId = null;
		try {
			itemId = itemDAO.createItem(item);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return itemId;
	}
	
	//Get the item object by item id using the ItemDAO
	public Item getItem(int ID) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Item item = null;
		try {
			item = itemDAO.getItem(ID);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return item;
	}
	
	// Get items list by cartIds set using ItemDAO
	public List<Item> getCartItems(Set<Integer> cartIds) throws RegistrationException, ClassNotFoundException, SQLException, IOException {
		List<Item> itemsList = null;
		try {
			itemsList = itemDAO.getCartItems(cartIds);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return itemsList;
	}
	
	//Get all the items list using ItemDAO
	public List<Item> getAllItems() throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		List<Item> itemsList = null;
		try {
			itemsList = itemDAO.getAllItems();
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return itemsList;	
	}
	
	//Update the item using the ItemDAO
	public int updateItem(Item item) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int result = 0;
		try {
			result = itemDAO.updateItem(item);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return result;
	}
	
	// Admin can delete an item by using the item_id
	public int deleteItem(int itemId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int result = itemDAO.deleteItem(itemId);
		return result;
	}
	
	//Calculate the total price from the cart item Ids
	public List<Double> getTotals(Set<Integer> itemIds){
		List<Double> totals = new ArrayList<Double>();
		
		// Pre-tax amount as sub-total
		double subtotal = 0.00;
		double taxAmount = 0.00;
		double totalPrice = 0.00;
		try {
			//Loop through all item id's in the cart
			for(int id : itemIds) {
				//Can be optimized to separate query to get the price only
				double price = itemDAO.getItem(id).getItemPrice();
				subtotal += price;
			}
			
			//Get the tax amount and grand total
			taxAmount = Math.round(subtotal * 8.875) / 100.0;
			totalPrice = subtotal + taxAmount;
			
			//Add to the list
			totals.add(subtotal);
			totals.add(taxAmount);
			totals.add(totalPrice);
			
			//Return the totals ArrayList
			return totals;
			
		}catch(Exception e) {
			e.getMessage();
		}
		
		return totals;
		
	}

}
