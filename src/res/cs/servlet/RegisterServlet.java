package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;

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
 * Servlet implementation class UserControllerServlet
 */
@WebServlet("/UserServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session object from the request
		HttpSession session = request.getSession();
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
		int userId = 0;
		
		// Validate the user info
		// Create a new User object
		theUser = new User();
		theUser.setFirstName(firstName);
		theUser.setLastName(lastName);
		theUser.setUserName(userName);
		theUser.setPassword(password);
		theUser.setGender(gender);
		theUser.setAddress(address);
		theUser.setPhoneNumber(phoneNumber);
		theUser.setEmail(email);
			
		// Add the user to the database
		try {
			userBO = new UserBO();
			userId = userBO.createUser(theUser);
			System.out.println("Newly created userId is: " + userId);
			if(userId != 0) {
				theUser.setUserId(userId);
				// Assign the session attribute
				session.setAttribute("currentUser", theUser);
				session.setAttribute("userId", userId);
				// Send to the login page
				response.sendRedirect("Login.jsp");
			
			}else {
				// Send back to the error page (registration page)
				response.sendRedirect("Registraion.jsp");

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