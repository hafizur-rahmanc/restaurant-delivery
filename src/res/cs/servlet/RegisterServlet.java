package res.cs.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import res.cs.bo.UserBO;
import res.cs.exception.RegistrationException;
import res.cs.model.User;

/**
 * Servlet implementation class UserControllerServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Declare an userBO variable
		UserBO userBO;
		// Declare an user model variable
		User theUser;
		// Send back to the login page
		if (request.getParameter("login") != null) {
			response.sendRedirect("Login.jsp");
		}
		
		// Registration process
		if(request.getParameter("register") != null) {
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
			String message="";
			
			// Validate the user info
			// Validate the password and repassword
			if(password.equals(repassword)) {
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
					// Check user name is available or not 
					if(userBO.isUserNameAvailable(userName)) {
						// Create the user
						userBO.createUser(theUser);
						// Send to the login page
						response.sendRedirect("Login.jsp");
					}else {
						message = "User name is already exists!";
					}
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
			  }
			// Send back to the Registration page with appropriate alert message
			request.setAttribute("message", message);
			request.getRequestDispatcher("Registration.jsp").forward(request, response);
			}
		}
	}