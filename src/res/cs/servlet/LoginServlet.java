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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Declare and initialize the userBO object variable
		UserBO userBO = new UserBO();
		// Declare and initialize the user object variable
		User theUser = new User();
		// Get the session from the request object
		HttpSession session = request.getSession();
		
		// Get the parameter from the form data
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if(request.getParameter("login") != null) {
			// Authenticate the login Process
			try {
				theUser = userBO.loginUser(userName, password);
				if(theUser != null) {
					// Check whether the user is an admin or regular user and redirect to the page accordingly
					// Assign the user as session attribute
					session.setAttribute("currentUser", theUser);
					session.setAttribute("userId", theUser.getUserId());
					System.out.println("Login Authenticated");
					
					// If the user is an admin redirect to the admin controller's AdminAccountInfo request
					if(userBO.isAdmin(theUser)) {
						System.out.println("User is Admin");
						// Assign isAdmin to true to the session object
						session.setAttribute("isAdmin", true);
						// Send back to the admin home page
						response.sendRedirect("admin/");
					} else {
						// Send to the menu item page
						response.sendRedirect("MenuItemServlet");
					}
	
				} else {
					// Navigate to the login page
					response.sendRedirect("login.jsp");
				}
	
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
		}else {
			// Send to the login page with error message
		}
		if(request.getParameter("register") != null) {
			// Send to the registration page
			response.sendRedirect("Registration.jsp");
		}
	}

}
