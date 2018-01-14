package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import res.cs.bo.OrderBO;
import res.cs.bo.PaymentBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Order;
import res.cs.model.Payment;

/**
 * Servlet implementation class PaymentInfoServlet
 */
@WebServlet("/PaymentInfoServlet")
public class PaymentInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session object from the request object
		HttpSession session = request.getSession();
		// Create a new instance of Payment
		Payment thePayment = new Payment();
		// Create a new instance of PaymentBO
		PaymentBO paymentBO = new PaymentBO();
		// Create a new instance of Order
		Order theOrder = new Order();
		// Create a new instance of OrderBO
		OrderBO orderBO = new OrderBO();
		
		// Read the payment information from the form
		Long creditCardNumber = Long.parseLong(request.getParameter("creditCard"));
		Integer secureCode = Integer.parseInt(request.getParameter("secureCode"));
		Integer zipcode = Integer.parseInt(request.getParameter("zipcode"));
		
		// If the payment information is valid we can move forward to make an order
		if(creditCardNumber != null && secureCode != null && zipcode != null) {
			int paymentId = 0;
			// Populate the payment model with its attributes 
			thePayment.setCreditCardNumber(creditCardNumber);
			thePayment.setSecureCode(secureCode);
			thePayment.setZipcode(zipcode);
			
			// Create the new payment into the database
			try {
				paymentId = paymentBO.createPayment(thePayment);
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
			
			// Upon successful creation of a payment
			if (paymentId != 0) {
				// Retrieve the necessary information from the session object for setting up the theOrder Order instance
				System.out.println(session.getAttribute("userId").getClass().getName());
				System.out.println(session.getAttribute("storeId").getClass().getName());
				
				Integer userId = (Integer) (session.getAttribute("userId"));
				Integer storeId = (Integer) (session.getAttribute("storeId"));
				int orderId = 0;
				
				// Retrieve the subtotal, taxtAmount and totalPrice from the session object
				double subtotal = (double) session.getAttribute("subtotal");
				double taxAmount = (double) session.getAttribute("taxAmount");
				double totalPrice = (double) session.getAttribute("totalPrice");
				
				// Populate the theOrder instance with it's attributes
				theOrder.setUserId(userId);
				theOrder.setStoreId(storeId);
				theOrder.setPaymentId(paymentId);
				theOrder.setSubtotal(subtotal);
				theOrder.setTaxAmount(taxAmount);
				theOrder.setTotalPrice(totalPrice);
				
				// Retrieve the cartIds from the session object
				Set<Integer> cartIds = (Set<Integer>) session.getAttribute("cartIds");
				
				// Create the order
				try {
					orderId = orderBO.createOrder(theOrder, cartIds);
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
				
				// Remove the session attributes except userId
				session.removeAttribute("cartIds");
				session.removeAttribute("storeId");
				session.removeAttribute("subtotal");
				session.removeAttribute("taxAmount");
				session.removeAttribute("totalPrice");
				
				// Send back to the order receipt page with the newly created order id as a parameter
				response.sendRedirect("OrderReceiptServlet?orderId=" + orderId);
			}
			
		}
	}

}
