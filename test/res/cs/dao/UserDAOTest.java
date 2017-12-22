package res.cs.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

@RunWith(Parameterized.class)
public class UserDAOTest {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String gender;
	private String address;
	private Long phoneNumber;
	private String email;
	private UserDAO dude;
	
	public UserDAOTest(String firstName, String lastName, String userName, String password, String gender,
			String address, Long phoneNumber, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	
	@Before
	public void initialize() {
		dude = new UserDAO();
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> inputData(){
		Object[][] data = {
				{"Mohammed", "Rahman", "hafiz", "hafiz", "M", "6107 Wooside Ave", 3475278509L, "hafiz@restaurant.org"},
				{"Abdur", "Rahman", "hafizur", "hafizur", "M", "6117 Wooside Ave", 6465278509L, "rahman@restaurant.org"},
				{"Omar", "Khait", "samy", "samy", "M", "6127 Wooside Ave", 3475278519L, "samy@restaurant.org"}
		};
		return Arrays.asList(data);
		
	}
	
	@Test
	public void registrationTest() {
		System.out.println("First Name: " + this.firstName + " Last Name: " + this.lastName + " User Name: " + this.userName );
	}
	

}
