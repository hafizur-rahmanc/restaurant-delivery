package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import res.cs.bo.ItemBO;
import res.cs.bo.ReviewBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/ItemServlet")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestDispatcher dispatcher;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Read the itemId from the request parameter
		int itemId = 0;
		ItemBO itemBO = new ItemBO();
		ReviewBO reviewBO = new ReviewBO();
		Item theItem = null;
		itemId = Integer.parseInt(request.getParameter("itemId"));
		if(itemId != 0) {
			try {
				// Get the item details from the database
				theItem = itemBO.getItem(itemId);
				// Get all the past reviews for this item and assign it as attribute
				theItem.setItemReviews(reviewBO.getAllReviews(itemId));
				
				// Assign the item as a attribute to the request object
				request.setAttribute("item", theItem);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (RegistrationException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// Forward this request to the ItemReview.jsp page for displaying item details and review form with past reviews
			dispatcher = request.getRequestDispatcher("ItemReview.jsp");
			dispatcher.forward(request, response);
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
