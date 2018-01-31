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
import res.cs.util.InputValidator;

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
		String message = "";
		
		if(request.getParameter("process") != null) {
			// Read the payment information from the form
			String ccNumber = request.getParameter("creditCard");
			String sCode = request.getParameter("secureCode");
			String zipCode = request.getParameter("zipcode");

			InputValidator v = new InputValidator();
			// If the payment information is valid we can move forward to make an order
			if(v.isValidPaymentInfo(ccNumber, sCode, zipCode)) {
				
				// Read the payment information from the form
				// creditCardNumber is a Long can not accept more than 19 digits
				Long creditCardNumber = Long.parseLong(ccNumber);
				Integer secureCode = Integer.parseInt(sCode);
				Integer zipcode = Integer.parseInt(zipCode);
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
					response.sendRedirect("Error.jsp");
				}
				
				// Upon successful creation of a payment
				if (paymentId != 0) {
					// Retrieve the necessary information from the session object for setting up the theOrder Order instance
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
						response.sendRedirect("Error.jsp");
					}
					
					// Remove the session attributes except userId
					session.removeAttribute("cartIds");
					session.removeAttribute("storeId");
					session.removeAttribute("subtotal");
					session.removeAttribute("taxAmount");
					session.removeAttribute("totalPrice");
					session.removeAttribute("cartItems");
	
					// Send back to the order receipt page with the newly created order id as a parameter
					response.sendRedirect("OrderReceiptServlet?orderId="+ orderId);
				}
				
			} else {
				message = "One of the fields is not formatted correctly!";
				// Send back to the Registration page with appropriate alert message
				request.setAttribute("message", message);
				request.getRequestDispatcher("PaymentInfo.jsp").forward(request, response);
			}
		} else {
			// Send to the error page
			response.sendRedirect("Error.jsp");
		}
	}

}
