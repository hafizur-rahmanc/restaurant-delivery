package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import res.cs.bo.ItemBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;

/**
 * Servlet implementation class MenuItemServlet
 */
@WebServlet("/MenuItemServlet")
public class MenuItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Declare a ServletContext object
	ServletContext context;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the ServletContext object from the request object
		context = request.getServletContext();
		// Declare an itemBO variable
		ItemBO itemBO;
		// Declare an menuItems variable to hold Item information
		List<Item> menuItems = null;
		
		// If the context object doesn't have the itemsList, then get the itemsList from database
		// and assign as a context attribute otherwise get it from it from context object
		if(context.getAttribute("menuItems") == null) {
			
			try {
				// Create a new instance of ItemBO and assign it to itemBO
				itemBO = new ItemBO();
				// Get all the available items from database
				menuItems = itemBO.getAllItems();
				// Assign the itemsList as an attribute to the context object
				context.setAttribute("menuItems", menuItems);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RegistrationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Send to the Menu Item Page
		response.sendRedirect("menu-item.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session from the request object
		HttpSession session = request.getSession();
		// Declare an itemIds set of type Integer for unique item id's in the cart
		Set<Integer> cartIds = null;
		// Declare an empty message string
		String message = "";
		// Grab the itemId from the form data
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		
		if (itemId != 0) {
			// Check whether session object has cartIds as an attribute or not
			if(session.getAttribute("cartIds") != null) {
				// Get the cartIds from the session and assign it to local cartIds list
				cartIds = (Set<Integer>) session.getAttribute("cartIds");
			} else {
				// Create a new HashSet of Integer type and assign it to local cartIds set
				cartIds = new HashSet<Integer>();
			}
			// Add the new item id to the set
			cartIds.add(itemId);
			// Assign the updated set into the session object
			session.setAttribute("cartIds", cartIds);
			// Generate the added to the cart message
			message = "Item added to the cart successfully!";
			// Assign the updated message to the request object
			request.setAttribute("message", message);
		}
		
		if (!message.isEmpty()) {
			// Send to the menu item page
			response.sendRedirect("menu-item.jsp");
			// doGet(request, response);
		}
	}
}
