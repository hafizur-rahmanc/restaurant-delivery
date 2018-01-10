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

import res.cs.bo.StoreBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Store;

/**
 * Servlet implementation class StoreServlet
 */
@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// Declare a ServletContext object
		ServletContext context = config.getServletContext();
		// Declare a storeBO variable
		StoreBO storeBO;
		// Declare a storesList variable to hold store information
		List<Store> storesList = null;
		
		// If the context object doesn't have the storesList, then get the stores list from database
		// Then assign storesList as a context attribute otherwise get it from it from context object
		if(context.getAttribute("storesList") == null) {
		
			try {
				// Create a new instance of StoreBO and assign it to storeBO
				storeBO = new StoreBO();
				// Get all the available stores from database
				storesList = storeBO.getAllStores();
				// Assign the storesList as an attribute to the context object
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Declare a ServletContext object
		ServletContext context = request.getServletContext();
		//Declare the RequestDispater object
		RequestDispatcher dispatcher = null;;
		
		// If the context object doesn't have the storesList, then get the storesList from database
		// Then assign it as a context attribute otherwise get it from the context object
		if(context.getAttribute("storesList") != null){
			// Send to the store selection page
			dispatcher = request.getRequestDispatcher("store.jsp");
			dispatcher.forward(request, response);
			
		} else {
			// We are not getting information either from context or database
			// Display the error message since the store location is empty
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
	}

}
