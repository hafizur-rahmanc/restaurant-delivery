package res.cs.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session from the request object
		HttpSession session = request.getSession();
		// Declare an itemIds list of type Integer
		List<Integer> cartIds = null;
		// Grab the itemId from the form data
		Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		
		if (itemId != 0) {
			// Check whether session object has cartIds as an attribute or not
			if(session.getAttribute("cartIds") != null) {
				// Get the cartIds from the session and assign it to local cartIds list
				cartIds = (List<Integer>) session.getAttribute("cartIds");
			} else {
				// Create a new ArrayList of Integer type and assign it to local cartIds
				cartIds = new ArrayList<Integer>();
			}
			// Add the cart itemId into the list
			cartIds.add(itemId);
			// Assign the updated list into the session object
			session.setAttribute("cartIds", cartIds);
			// Send back to the menu page
			response.sendRedirect("MenuItemServlet");
			System.out.println("The added item ID is: " + itemId);
		} else {
			response.sendRedirect("MenuItemServlet");
		}
	}
}
