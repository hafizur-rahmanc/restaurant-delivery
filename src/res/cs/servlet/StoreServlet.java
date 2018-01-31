package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import res.cs.bo.StoreBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Store;

/**
 * Servlet implementation class StoreServlet
 */
@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Declare a ServletContext object
	ServletContext context;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the ServletContext object from the request object
		context = request.getServletContext();
		// Create a new instance of StoreBO and assign it to storeBO
		StoreBO storeBO = new StoreBO();
		// Declare a storesList variable to hold store information
		List<Store> storesList = null;
		
		// If the context object doesn't have the storesList, then get the storesList from database
		// Then assign it as a context attribute
		if(context.getAttribute("storesList") == null){

			try {
				// Get all the available stores from database
				storesList = storeBO.getAllStores();
				// Assign the storesList as a context object attribute
				context.setAttribute("storesList", storesList);
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
		}
			// Send to the store selection page
			response.sendRedirect("Stores.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Read the action from the URL
		String action = request.getParameter("action");
		// Get the session from the request object
		HttpSession session = request.getSession();
		
		if(action.equals("cancel")) {
			// Remove the session attribute and send back to the menu page
			session.removeAttribute("cartIds");
			response.sendRedirect("MenuItemServlet");
		} else if(action.equals("reviewOrder")) {
			// Read the store id and assign it to the session object
			int storeId = Integer.parseInt(request.getParameter("store"));
			if (storeId != 0) {
				// Store the store id as a session attribute
				session.setAttribute("storeId", storeId);
				// Navigate to ReviewOrder Servlet
				response.sendRedirect("ReviewOrderServlet");
			}
		
		}

	}

}
