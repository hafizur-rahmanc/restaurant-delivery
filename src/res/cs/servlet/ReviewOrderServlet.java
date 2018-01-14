package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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
 * Servlet implementation class ReviewOrderServlet
 */
@WebServlet("/ReviewOrderServlet")
public class ReviewOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session object from the request object
		HttpSession session = request.getSession();
		// Declare a itemBO variable of type ItemBO
		ItemBO itemBO = new ItemBO();
		// Read the cartIds from the session object
		@SuppressWarnings("unchecked")
		Set<Integer> cartIds = (Set<Integer>) session.getAttribute("cartIds");
		// Declare cartItems list to hold items in the cart
		List<Item> cartItems = null;
		// Declare a total price summary list based on the cartIds
		List<Double> priceSummary = null;
		
		// If cartIds are not empty then find them from the database and store them in the session
		// Calculate the order summary for those selected item as well
		if(cartIds != null) {
			try {
				// Get the cart items from the database
				cartItems = itemBO.getCartItems(cartIds);
				// Assign cartItems as a session attribute
				session.setAttribute("cartItems", cartItems);
				
				// Calculate the total price summary list by using the cartIds
				priceSummary = itemBO.getTotals(cartIds);
				// Assign the subtotal, taxAmount, and totalPrice as session attributes
				session.setAttribute("subtotal", priceSummary.get(0));
				session.setAttribute("taxAmount", priceSummary.get(1));
				session.setAttribute("totalPrice", priceSummary.get(2));
				
				// Navigate to the Review Order page
				response.sendRedirect("ReviewOrder.jsp");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RegistrationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			// Send back to the menu item page
			response.sendRedirect("MenuItem.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session object from the request object
		HttpSession session = request.getSession();
		
		// In the case of canceling an order, remove the cartIds from the session and send back to the menu items page
		if(request.getParameter("cancel") != null) {
			// Remove the cartIds and cartItems from the session object 
			session.removeAttribute("cartIds");
			session.removeAttribute("cartItems");
			// Send back to the menu items page
			response.sendRedirect("MenuItem.jsp");
		}
		
		// Remove an item from the cart then send back to the ReviewOrderServlet
		if(request.getParameter("remove") != null) {
			// Read the itemId from the request objects parameter
			Integer itemId = Integer.parseInt(request.getParameter("remove"));
			// Retrieve the cartIds from the session object
			Set<Integer> cartIds = (Set<Integer>) session.getAttribute("cartIds");
			// Remove the item from the cartIds
			cartIds.remove(itemId);
			// When cartIds is empty, send back to the menu page
			if(cartIds.isEmpty()) {
				response.sendRedirect("MenuItem.jsp");
			}else {
				// Go back to the ReviewOrderServlets get method to see the updated cartItems
				doGet(request, response);	
			}

		}
		if(request.getParameter("process") != null && session.getAttribute("cartItems") != null) {
			// Send back to the Payment Information Page to get payment information
			response.sendRedirect("PaymentInfo.jsp");
		}
	}

}
