package res.cs.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import res.cs.bo.ItemBO;
import res.cs.bo.OrderBO;
import res.cs.bo.ReviewBO;
import res.cs.bo.StoreBO;
import res.cs.bo.UserBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;
import res.cs.model.Order;
import res.cs.model.Review;
import res.cs.model.Store;
import res.cs.model.User;
import res.cs.util.InputValidator;

@Controller
public class AdminController {
	// Admin home page
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView adminHome() {
		ModelAndView model = new ModelAndView("AdminHome");
		return model;
	}
	
	// Admin account information
	@RequestMapping(value="/AdminAccountInfo", method=RequestMethod.GET)
	public ModelAndView adminAccount() {
		ModelAndView model = new ModelAndView("AdminAccountInfo");
		return model;
	}
	
	// Update Admin account information
	@RequestMapping(value="/AdminAccountInfo", method=RequestMethod.POST)
	public ModelAndView adminAccountUpdate(HttpServletRequest request) throws IOException {
		// Create a userBO variable
		UserBO userBO = new UserBO();
		ModelAndView model = new ModelAndView("AdminAccountInfo");
		// Get the session object from the request object
		HttpSession session = request.getSession();
		// Get the current user from the session
		User currentUser = (User) session.getAttribute("currentUser");
		// Get the user id from the session object
		int userId = (int) session.getAttribute("userId");
		
		if(currentUser != null) {
			// Read user info from the form data
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String repassword = request.getParameter("rePassword");
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			Long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
			String email = request.getParameter("email");
			InputValidator v = new InputValidator();
			// Validate the admin account information
			if(v.isValidRegistartion(firstName, lastName, userName, password, repassword, address, gender, request.getParameter("phoneNumber"), email)) {
					// Validate the password and re-enter password
				if(password.equals(repassword)) {
					// Populate the current user with updated data
					currentUser.setUserId(userId);
					currentUser.setFirstName(firstName);
					currentUser.setLastName(lastName);
					currentUser.setUserName(userName);
					currentUser.setPassword(password);
					currentUser.setRepassword(repassword);
					currentUser.setGender(gender);
					currentUser.setAddress(address);
					currentUser.setPhoneNumber(phoneNumber);
					currentUser.setEmail(email);
					
					// Update the user to the database
					try {
						userBO = new UserBO();
						userBO.updateUser(currentUser);
						model.addObject("message", "User information updated successfully!");
						// Update the session object as well to see the updated data in the view
						session.setAttribute("currentUser", currentUser);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (RegistrationException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
						model = new ModelAndView("AdminError");
					}
				} else {
					model.addObject("message", "Password does not match!");
				}
			}else {
				model.addObject("message", "One of the fields is not formatted correctly!");
			}
		} else {
			model = new ModelAndView("AdminError");
		}
		return model;
	}
	
	// Admin navigation page
	@RequestMapping(value="/AdminNavigate", method=RequestMethod.GET)
	public ModelAndView adminNavigate() {
		ModelAndView model = new ModelAndView("AdminNavigate");
		return model;
	}
	
	// Get the list of users
	@RequestMapping(value="/AdminUsersList", method=RequestMethod.GET)
	public ModelAndView adminUsersList() throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		// Declare UserBO variable
		UserBO userBO = new UserBO();
		ModelAndView model = new ModelAndView("AdminUsersList");
		// Get all the regular users from the database
		List<User> usersList = userBO.getAllUsers();
		// Add all the regular users list to the model
		model.addObject("usersList", usersList);
		// Return the view
		return model;
	}
	
	// Get an individual user information
	@RequestMapping(value="/AdminGetUser", method=RequestMethod.GET)
	public ModelAndView adminGetUser(
			@RequestParam(value="userId", required=true) Integer userId) throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		// Declare a ModelAndView variable
		ModelAndView model;
		// Declare a UserBO variable
		UserBO userBO = new UserBO();
		// Declare a ReviewBO variable
		ReviewBO reviewBO = new ReviewBO();
		if(userId != null) {
			User theUser = userBO.getUserById(userId);
			theUser.setRepassword(theUser.getPassword());
			// Add the user information to the model
			// Here "command" is a reserved request attribute which is used to display object data into form
			model = new ModelAndView("AdminGetUser", "command", theUser);
			// Get all the reviews by this user
			List<Review> reviewsList = reviewBO.getReviewsByUser(userId);
			// Add the reviews list to the model
			model.addObject("reviewsList", reviewsList);
		} else {
			model = new ModelAndView("AdminError");
		}
		
		// Return the view
		return model;
	}
	
	// Update an individual user information
	@RequestMapping(value="/AdminUpdateUser", method=RequestMethod.POST)
	public ModelAndView adminUpdateUser(@ModelAttribute("user") User user) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		// Declare ModelAndView variable
		ModelAndView model;
		// Declare a UserBO variable
		UserBO userBO = new UserBO();
		
		// Read the updated user information for validation check
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String userName = user.getUserName();
		String password = user.getPassword();
		String repassword = user.getRepassword();
		String gender = user.getGender();
		String address = user.getAddress();
		String email = user.getEmail();
		Long phoneNumber = user.getPhoneNumber();
		
		// Render the view with updated user information
		model = adminGetUser(user.getUserId());
		
		// Check the validation for the new user information
		InputValidator v = new InputValidator();
		// Validate the admin account information
		if(v.isValidUpdate(firstName, lastName, userName, password, repassword, address, gender, phoneNumber.toString(), email)) {
			// when password and re-password matches
			if (password.equals(repassword)) {
				// User updated successfully
				userBO.updateUser(user);
				// Render the view with updated user information
				model = adminGetUser(user.getUserId());
				// Display the form with the user data
				model.addObject("message", "User information updated successfully!");
			} else {
				model.addObject("message", "Password does not match!");
			}
		} else {
			model.addObject("message", "One of the fields is not formatted correctly!");
		}
		// Return the view
		return model;
	}
	
	// Delete an individual user information
	@RequestMapping(value="/AdminDeleteUser", method=RequestMethod.GET)
	public ModelAndView adminDeleteUser(
			@RequestParam(value="userId", required=true) Integer userId) throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		// Declare a ModelAndView variable
		ModelAndView model;
		// Declare a UserBO variable
		UserBO userBO = new UserBO();
		
		if(userId != null) {
			int result = userBO.deleteUser(userId);
			// Upon successful user deletion
			if (result != 0) {
				// Call the adminUsersList to get the updated model and view
				model = adminUsersList();
				model.addObject("message", "The User Deleted Successfully!");
				
			} else {
				// Display error message
				model = new ModelAndView("AdminError");
			}
			// Add the user information to the model

		} else {
			model = new ModelAndView("AdminError");
		}
		
		// Return the view
		return model;
	}
	
	// Delete a review and call the adminGetUser
	@RequestMapping(value="/AdminDeleteReview", method=RequestMethod.POST)
	public ModelAndView adminDeleteReview(
			@RequestParam(value="reviewId", required=true) Integer reviewId,
			@RequestParam(value="delete", required=true) Integer userId) throws ClassNotFoundException, RegistrationException, SQLException, IOException{
		ModelAndView model;
		// Declare a ReviewBO
		ReviewBO reviewBO = new ReviewBO();
		if (reviewId != null) {
			int result = reviewBO.deleteReview(reviewId);
			// When review deleted successfully
			if (result != 0) {
				// Call the adminItemsList() to get the updated model
				model = adminGetUser(userId);
				// Add the updated message to the model
				model.addObject("message", "The review deleted successfully!");
			} else {
				// Display error message
				model = new ModelAndView("AdminError");
			}
		} else {
			model = new ModelAndView("AdminError");
		}
		// Return the view
		return model;
	}
	
	
	// Get the list of items
	@RequestMapping(value="/AdminItemsList", method=RequestMethod.GET)
	public ModelAndView adminItemsList() throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		// Declare an ItemBO variable
		ItemBO itemBO = new ItemBO();
		ModelAndView model = new ModelAndView("AdminItemsList");
		Item theItem = new Item();
		model.addObject("item", theItem);
		
		// Get all the items from the database
		List<Item> itemsList = itemBO.getAllItems();
		// Add all the items list to the model
		model.addObject("itemsList", itemsList);
		
		// Return the view
		return model;
	}
	
	// Update an item based on the request parameter
	@RequestMapping(value="/UpdateItem", method=RequestMethod.POST)
	public ModelAndView adminUpdateItem(
			@RequestParam(value="name", required=true) String itemName,
			@RequestParam(value="price", required=true) Double itemPrice,
			@RequestParam(value="description", required=true) String itemDescription,
			@RequestParam(value="image", required=true) String itemImage,
			@RequestParam(value="active", required=true) Integer itemActive,
			@RequestParam(value="category", required=true) String itemCategory,
			@RequestParam(value="update", required=false) Integer itemUpdateId,
			@RequestParam(value="delete", required=false) Integer itemDeleteId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		
		// Declare a model and view variable
		ModelAndView model = adminItemsList();
		// Declare an ItemBO variable
		ItemBO itemBO = new ItemBO();
		InputValidator v = new InputValidator();
		// For item update/delete operation
		if (itemUpdateId != null) {
			if(v.isValidItem(itemName, itemPrice.toString(), itemDescription, itemImage, itemActive.toString(), itemCategory)) {
				// Declare an Item variable
				Item theItem = new Item();
				theItem.setItemId(itemUpdateId);
				theItem.setItemName(itemName);
				theItem.setItemPrice(itemPrice);
				theItem.setItemDescription(itemDescription);
				theItem.setImage(itemImage);
				theItem.setActive(itemActive);
				theItem.setCategory(itemCategory);
				
				// Now update the item in the database
				int result = itemBO.updateItem(theItem);
				
				// Call the adminItemsList() to get the updated model
				model = adminItemsList();
				if (result != 0) {
					// Add the updated message to the model
					model.addObject("message", "Item updated successfully!");
				}
			} else {
				model.addObject("message", "One of the fields is not formatted correctly!");
			}
		} else if (itemDeleteId != null) {
			// Now delete the item
			int result = itemBO.deleteItem(itemDeleteId);
			// Call the adminItemsList() to get the updated model
			model = adminItemsList();
			if(result != 0) {
				// Add the deleted message to the model
				model.addObject("message", "Item deleted successfully!");
			}
		} else {
			// Display the error
			model = new ModelAndView("/AdminError");
		}
		
		// Return the view
		return model;
	}
	
    //The @ModelAttribute puts request data into model object.  
    @RequestMapping(value="/AdminAddItem",method = RequestMethod.POST)  
    public ModelAndView adminCreateItem(@ModelAttribute("item") Item item) throws ClassNotFoundException, RegistrationException, IOException, SQLException{  
        // Declare an ItemBO variable
    	ItemBO itemBO = new ItemBO();
    	ModelAndView model = null;
    	// Create the new item
    	int result = itemBO.createItem(item);
    	if (result != 0) {
			// Call the adminItemsList() to get the updated model
    		model = adminItemsList();
    		// Add the created message to the model
    		model.addObject("message", "Item craeted successfully!");
    	} else{
    		// Display the error
    		model =  new ModelAndView("AdminError");	
    	}
    	// Return the view
    	return model;
    } 
    
    // Get the list of orders
    @RequestMapping(value="/AdminOrdersList", method=RequestMethod.GET)
    public ModelAndView adminOrdersList() throws RegistrationException, SQLException {
    	// Declare a ModelAndView variable
    	ModelAndView model = new ModelAndView("AdminOrdersList");
    	// Declare an OrederBO variable
    	OrderBO orderBO = new OrderBO();
    	// Get all the orders from database
    	List<Order> ordersList = orderBO.getAllOrders();
    	// Add ordersList to the model view object
    	model.addObject("ordersList", ordersList);
    	
    	// Return the view
    	return model;
    }
    
    // Delete an order the display the orders list
    @RequestMapping(value="/AdminDeleteOrder", method=RequestMethod.POST)
    public ModelAndView adminOrderDelete(
    		@RequestParam(value="orderId", required=true) Integer orderId) throws RegistrationException, SQLException, ClassNotFoundException, IOException {
    	// Declare a ModelAndView variable
    	ModelAndView model;
    	// Declare an OrederBO variable
    	OrderBO orderBO = new OrderBO();
    	if (orderId != null) {
    		// Delete the order from the database
    		int result = orderBO.deleteOrder(orderId);
    		if(result != 0) {
    	    	// Call the adminOrdersList to get the updated order's list
    			 model = adminOrdersList();
    			// Add the success message to the model
    			model.addObject("message", "Order deleted successfully!");
    		} else {
    			// Display error message
    			model = new ModelAndView("AdminError");
    		}
    	}else {
    		// Display error message
			model = new ModelAndView("AdminError");
    	}
    	// Return the view
    	return model;
    }
    
    // Get all the locations list
    @RequestMapping(value="/AdminLocationsList", method=RequestMethod.GET)
    public ModelAndView adminLocationsList() throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		// Declare a StoreBO variable
		StoreBO storeBO = new StoreBO();
		ModelAndView model = new ModelAndView("AdminLocationsList");
		Store theStore = new Store();
		model.addObject("store", theStore);
		
		// Get all the stores from the database
		List<Store> storesList = storeBO.getAllStores();
		// Add all the stores list to the model
		model.addObject("storesList", storesList);
		
		// Return the view
		return model;
    }
    
    // Update any specific location
    @RequestMapping(value="/AdminUpdateLocation", method=RequestMethod.POST)
    public ModelAndView adminUpdateLocation(
			@RequestParam(value="storeName", required=true) String storeName,
			@RequestParam(value="address", required=true) String address,
			@RequestParam(value="city", required=true) String city,
			@RequestParam(value="staffNumber", required=true) Integer staffNumber,
			@RequestParam(value="zipcode", required=true) Integer zipcode,
			@RequestParam(value="image", required=true) String image,
			@RequestParam(value="update", required=false) Integer storeUpdateId,
			@RequestParam(value="delete", required=false) Integer storeDeleteId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
    	
    	// Declare a ModelAndView variable
    	ModelAndView model;
    	// Declare a StoreBO variable
    	StoreBO storeBO = new StoreBO();
    	// Declare a Store variable
    	Store theStore = new Store();
    	
    	// Populate the store instance
    	theStore.setStoreName(storeName);
    	theStore.setAddress(address);
    	theStore.setCity(city);
    	theStore.setStaffNumber(staffNumber);
    	theStore.setZipcode(zipcode);
    	theStore.setImage(image);
    	
    	// Update operation for any specific store
    	if(storeUpdateId != null) {
    		theStore.setStoreId(storeUpdateId);
    		// Update the Store in the database
    		int result = storeBO.updateStore(theStore);
    		// Upon successful store update
    		if(result != 0) {
    			// Call the adminLocationsList to get the updated model and view
    			model = adminLocationsList();
    			// Add success message to the model
    			model.addObject("message", "The Location Updated Successfully!");
    		} else {
    			// Display error message
    			model = new ModelAndView("AdminError");
    		}
    	// Delete operation for any specific store
    	} else if(storeDeleteId != null) {
    		int result = storeBO.deleteStore(storeDeleteId);
    		// Upon successful store delete
    		if (result != 0) {
    			// Call the adminLocationsList to get the Updated model and view
    			model = adminLocationsList();
    			// Add success message to the model
    			model.addObject("message", "The Location Deleted Successfully!");
    		} else {
    			// Display error message
    			model = new ModelAndView("AdminError");
    		}
    	}
    	else {
			// Display error message
			model = new ModelAndView("AdminError");
    	}
    	// Return the view
    	return model;
    }
    
    // Add a new Location to the location list
    // The @ModelAttribute puts request data into model object.  
    @RequestMapping(value="/AdminAddLocation",method = RequestMethod.POST)  
    public ModelAndView adminAddLocation(@ModelAttribute("store") Store store) throws ClassNotFoundException, RegistrationException, IOException, SQLException{  
        // Declare an StoreBO variable
    	StoreBO storeBO = new StoreBO();
    	ModelAndView model;
    	// Add the new location to the location list
    	int result = storeBO.createStore(store);
    	if (result != 0) {
			// Call the adminLocationsList() to get the updated model
    		model = adminLocationsList();
    		// Add the success message to the model
    		model.addObject("message", "Location Craeted Successfully");
    	} else{
    		// Display the error
    		model =  new ModelAndView("AdminError");	
    	}
    	// Return the view
    	return model;
    }
}
