package res.cs.junit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import res.cs.dao.PaymentDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Payment;

public class PaymentDAOTest {
	private PaymentDAO paymentDAO;
	private Payment thePayment;
	private boolean isCreated;
	private int paymentId;
	
	@Before
	public void initialize() {
		paymentDAO = new PaymentDAO();
		thePayment = null;
		isCreated = false;
		paymentId = 0;
	}
	
	//Verify the new payment process
	@Test
	public void createPaymentTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		thePayment = new Payment(111111111111111111L, 769, 10029);
		paymentId = paymentDAO.createPayment(thePayment);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (paymentId != 0);
		assertThat(isCreated , equalTo(true));
	}
	
	// Verify that invalid data returns expected result
	@Test
	public void deletePaymentTest() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = paymentDAO.deletePayment(87);;
		assertThat(actual, equalTo(0));	
	}
	
	@After
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			paymentDAO.deletePayment(paymentId);
		}
	}
}
