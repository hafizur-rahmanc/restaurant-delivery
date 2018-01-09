package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session object from the request
		HttpSession session = request.getSession();
		// Declare an itemBO variable
		ItemBO itemBO;
		// Declare the RequestDispatcher object variable
		RequestDispatcher dispatcher;
		
		// Get the Menu Item data from the database
		@SuppressWarnings("unused")
		List<Item> itemsList = null;
		
		// If the session object doesn't have the itemsList, then get the itemsList from database
		// and assign as a session attribute otherwise get it from the session
		if(session.getAttribute("itemsList") == null){
			try {
				// Create a new instance of ItemBO and assign it to itemBO
				itemBO = new ItemBO();
				// Get all the available items from database
				itemsList = itemBO.getAllItems();
				// Assign the itemsList as an attribute to the session object
				session.setAttribute("itemsList", itemsList);
				
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
			
		} else {
			itemsList = (List<Item>) session.getAttribute("itemsList");
		}
		
		// Send to the menu page
		dispatcher = request.getRequestDispatcher("menu-item.jsp");
		dispatcher.forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
