package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import res.cs.bo.OrderBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;
import res.cs.model.Order;

/**
 * Servlet implementation class OrderReceiptServlet
 */
@WebServlet("/OrderReceiptServlet")
public class OrderReceiptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Declare an object of RequestDispatcher
		RequestDispatcher dispatcher = null;
		// Declare a orderItems list to hold the order items 
		List<Item> orderItems = new ArrayList<Item>();
		// Declare a OrderBO variable to retrieve the order items from the database
		OrderBO orderBO = new OrderBO();
		
		// Declare a Order variable to get the receipt summary information
		Order receiptSummary = null;
		
		// Read the order id from the request parameter
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		
		if (orderId != 0) {
			try {
				// Get all the order items for this order
				orderItems = orderBO.getOrderItemsByOrderId(orderId);
				request.setAttribute("orderItems", orderItems);
			} catch (RegistrationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Get the ReceiptSummary(Order model) by using the orderId
			try {
				receiptSummary = orderBO.getReceiptSummary(orderId);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Assign the receiptSummary and orderItems to the request object 
		request.setAttribute("orderItems", orderItems);
		request.setAttribute("receiptSummary", receiptSummary);
		
		// Obtain the request dispatcher target resource, in this case OrderReceipt.jsp
		dispatcher = request.getRequestDispatcher("OrderReceipt.jsp");
		// Forward the request to the order receipt page
		dispatcher.forward(request, response);
	}
}
