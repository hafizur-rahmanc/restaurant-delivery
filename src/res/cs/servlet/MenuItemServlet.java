package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// Declare a ServletContext object
		ServletContext context = config.getServletContext();
		// Declare an itemBO variable
		ItemBO itemBO;
		// Declare an itemsList variable to hold Item information
		List<Item> itemsList = null;
		
		// If the context object doesn't have the itemsList, then get the itemsList from database
		// and assign as a context attribute otherwise get it from it from context object
		if(context.getAttribute("itemsList") == null) {
		
			try {
				// Create a new instance of ItemBO and assign it to itemBO
				itemBO = new ItemBO();
				// Get all the available items from database
				itemsList = itemBO.getAllItems();
				// Assign the itemsList as an attribute to the context object
				context.setAttribute("itemsList", itemsList);
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

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Declare a ServletContext object
		ServletContext context = request.getServletContext();
		//Declare the RequestDispater object
		RequestDispatcher dispatcher = null;;
		
		// If the session object doesn't have the itemsList, then get the itemsList from database
		// and assign as a session attribute otherwise get it from the session
		if(context.getAttribute("itemsList") != null){
			// Send to the menu page
			dispatcher = request.getRequestDispatcher("menu-item.jsp");
			dispatcher.forward(request, response);
			
		} else {
			// We are not getting information either from context or database in context has empty list
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
