package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;

import res.cs.dao.PaymentDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.Payment;

public class PaymentBO {
	//Create payment by using PaymentDAO
	public int createPayment(Payment thePayment) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		final PaymentDAO paymentDAO = new PaymentDAO();
		int result = 0;
		result = paymentDAO.createPayment(thePayment);
		return result;
	}
	
	//Delete payment by the payment_id
	public int deletePayment(int paymentId) throws RegistrationException, SQLException {
		final PaymentDAO paymentDAO = new PaymentDAO();
		int result = 0;
		result = paymentDAO.deletePayment(paymentId);
		return result;	
	}
}
