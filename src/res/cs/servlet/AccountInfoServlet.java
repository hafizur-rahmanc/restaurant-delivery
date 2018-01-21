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
		
		//Read user info from the form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		Long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
		String email = request.getParameter("email");
		int result = 0;

		//Create a new User object
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
		
		// Need to check the user information validity
		//Update the user to the database
		try {
			userBO = new UserBO();
			result = userBO.updateUser(theUser);
			System.out.println("User is updated");
			if(result != 0) {
				// Send to the Account Information page
				 response.sendRedirect("AccountInfoServlet");
			}else {
				// Send back to the account information page with error message
				response.sendRedirect("AccountInfo.jsp?message=false");
				// doGet(request, response);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (RegistrationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
