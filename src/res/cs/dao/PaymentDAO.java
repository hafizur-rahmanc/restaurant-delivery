package res.cs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import res.cs.exception.RegistrationException;
import res.cs.util.OracleSqlQueries;

public class PaymentDAO {
	
	public int createPayment(int user_id, long credit_card_number, int secure_code, int zipcode) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int paymentId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		String[] columns = {"payment_id"};
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_PAYMENT, columns);
			//Fill out the '?' in the SQL query string
			stmt.setInt(1, user_id);
			stmt.setLong(2, credit_card_number);
			// assume that we already created a payment entry by this user 
			stmt.setInt(3, secure_code);
			stmt.setInt(4, zipcode);
			//For the addition of the new item
			stmt.executeUpdate();
			//Retrieve any auto generated keys created as a result of executing this statement
			result = stmt.getGeneratedKeys();
			if(result.next()) {
				paymentId = result.getInt(1);
			}
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			result.close();
			stmt.close();
			conn.close();
		}
		
		return paymentId;
	}
	
}
