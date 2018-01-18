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

import res.cs.bo.OrderBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Order;

/**
 * Servlet implementation class OrdersHistoryServlet
 */
@WebServlet("/OrdersHistoryServlet")
public class OrdersHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Declare the RequestDispatcher variable
	RequestDispatcher dispatcher = null;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session object from the request object
		HttpSession session = request.getSession();
		// Get the userId from the session object
		Integer userId = (Integer) session.getAttribute("userId");
		// Declare a OrderBO variable
		OrderBO orderBO = new OrderBO();
		// Declare a ordersList or type list
		List<Order> ordersList = null;
		
		if (userId != null) {
			try {
				// Get all the orders for this user
				ordersList = orderBO.getOrdersByUserId(userId);
				// Assign the ordersList to the request object as attribute
				request.setAttribute("ordersList", ordersList);
				
				// Forward the request to the OrdersHistory.jsp page to display user orders
				dispatcher = request.getRequestDispatcher("OrdersHistory.jsp");
				dispatcher.forward(request, response);
				
			} catch (RegistrationException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			// Send back to the error page
			response.sendRedirect("Error.jsp");
		}
	}
}
