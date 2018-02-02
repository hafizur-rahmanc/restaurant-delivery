package res.cs.testng;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import res.cs.dao.PaymentDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Payment;

public class PaymentDAOTest {
	private PaymentDAO paymentDAO;
	private Payment thePayment;
	private boolean isCreated;
	private int paymentId;
	
	@BeforeMethod
	public void initialize() {
		paymentDAO = new PaymentDAO();
		thePayment = null;
		isCreated = false;
		paymentId = 0;
	}
	
	@DataProvider(name="newPayment")
	public Object[][] inputData(){
		Object[][] data = {
				{111111111111111111L, 769, 10029, true},
				{222222222222222222L, 456, 10023, true},
				{333333333333333333L, 369, 11373, true}
		};
		return data;	
	}
	
	//Verify the new payment process
	@Test(dataProvider="newPayment")
	public void createPaymentTest(Long creditCardNumber, int secureCode, int zipcode, boolean expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		thePayment = new Payment(creditCardNumber, secureCode, zipcode);
		paymentId = paymentDAO.createPayment(thePayment);
		// equivalent to isCreated = (actual != 0) ? true : false
		isCreated = (paymentId != 0);
		assertThat(isCreated , equalTo(expected));
		
	}
	
	@DataProvider(name="deletePayment")
	public Object[][] createData(){
		Object[][] data = {
				{81, 0}, // not available case
				{87, 0} // not available case
		};
		return data;	
	}
	
	// Verify that invalid data returns expected result
	@Test(dataProvider="deletePayment")
	public void deletePaymentTest(int paymentId, int expected) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int actual = paymentDAO.deletePayment(paymentId);;
		assertThat(actual, equalTo(expected));	
	}
	
	@AfterMethod
	public void finalize() throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		if(isCreated) {
			paymentDAO.deletePayment(paymentId);
		}
	}
}
