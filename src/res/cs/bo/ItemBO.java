package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import res.cs.dao.ItemDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;

public class ItemBO {
	//Create a new item and save it to the database using ItemDAO
	public int createItem(Item item) throws RegistrationException, ClassNotFoundException, IOException, SQLException {
		final ItemDAO itemDAO = new ItemDAO();
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
		final ItemDAO itemDAO = new ItemDAO();
		Item item = null;
		try {
			item = itemDAO.getItem(ID);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return item;
	}
	
	//Get all the items list using ItemDAO
	public List<Item> getAllItems() throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		final ItemDAO itemDAO = new ItemDAO();
		List<Item> itemsList = null;
		try {
			itemsList = itemDAO.getAllItems();
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return itemsList;	
	}
	
	//Update the item using the ItemDAO
	public void updateItem(Item item) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		final ItemDAO itemDAO = new ItemDAO();
		try {
			itemDAO.updateItem(item);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
	}
	
	//Calculate the total price from the cart item Ids
	public List<Double> getTotals(List<Integer> itemIds){
		ItemDAO itemDAO = new ItemDAO();
		List<Double> totals = new ArrayList<Double>();
		
		//Pre tax amount as subtotal
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
			
			//Return the totals Array List
			return totals;
			
		}catch(Exception e) {
			e.getMessage();
		}
		
		return totals;
		
	}
}
