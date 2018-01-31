package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import res.cs.bo.UserBO;
import res.cs.exception.RegistrationException;
import res.cs.model.User;
import res.cs.util.InputValidator;

/**
 * Servlet implementation class AccountInfoServlet
 */
@WebServlet("/AccountInfoServlet")
public class AccountInfoServlet extends HttpServlet {
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
		// Declare a UserBO variable
		UserBO userBO = new UserBO();
		
		if(userId != null) {
			try {
				// Get the user information from the database
				User theUser = userBO.getUserById(userId);
				// Assign theUser to the request object as attribute
				request.setAttribute("currentUser", theUser);
				
				// Forward this request to the AccountInfo page to display the user information
				dispatcher = request.getRequestDispatcher("AccountInfo.jsp");
				dispatcher.forward(request, response);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (RegistrationException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendRedirect("Error.jsp");
			}
		} else {
			response.sendRedirect("Error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session object from the request object
		HttpSession session = request.getSession();
		// Get the userId from the session object
		Integer userId = (Integer) session.getAttribute("userId");
		// Declare an userBO variable
		UserBO userBO;
		// Declare an user model variable
		User theUser;
		String message="";
		
		//Read user info from the form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		Long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
		String email = request.getParameter("email");

		// Validate the user info
		InputValidator v = new InputValidator();
		if (v.isValidRegistartion(firstName, lastName, userName, password, repassword, address, gender, request.getParameter("phoneNumber"), email)) {
			// Validate the password and re-password
			if(password.equals(repassword)) {
				// Create a new User object
				theUser = new User();
				theUser.setUserId(userId);
				theUser.setFirstName(firstName);
				theUser.setLastName(lastName);
				theUser.setUserName(userName);
				theUser.setPassword(password);
				theUser.setGender(gender);
				theUser.setAddress(address);
				theUser.setPhoneNumber(phoneNumber);
				theUser.setEmail(email);
				
				// Update the user to the database
				try {
					userBO = new UserBO();
					// Update the user now
					userBO.updateUser(theUser);
					// Send to the Account Information page
					message = "User information updated successfully!";
					// Go back to the doGet() method with appropriate alert message
					request.setAttribute("message", message);
					doGet(request, response);
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (RegistrationException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
					response.sendRedirect("Error.jsp");
				}
			} else {
				message = "Password does not match!";
				// Go back to the doGet() method with appropriate alert message
				request.setAttribute("message", message);
				doGet(request, response);
			}
		} else {
			message = "One of the fields is not formatted correctly!";
			// Go back to the doGet() method with appropriate alert message
			request.setAttribute("message", message);
			doGet(request, response);
		}
	}
}
