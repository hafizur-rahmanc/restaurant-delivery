package res.cs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import res.cs.exception.RegistrationException;
import res.cs.model.Payment;
import res.cs.util.OracleSqlQueries;

public class PaymentDAO {
	
	public int createPayment(Payment thePayment) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int paymentId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		String[] columns = {"payment_id"};
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_PAYMENT, columns);
			//Fill out the '?' in the SQL query string
			stmt.setLong(1, thePayment.getCreditCardNumber());
			// assume that we already created a payment entry by this user 
			stmt.setInt(2, thePayment.getSecureCode());
			stmt.setInt(3, thePayment.getZipcode());
			//For the addition of the new item
			stmt.executeUpdate();
			//Retrieve any auto generated keys created as a result of executing this statement
			resultSet = stmt.getGeneratedKeys();
			if(resultSet.next()) {
				paymentId = resultSet.getInt(1);
			}
			
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(resultSet, stmt, conn);
		}
		
		return paymentId;
	}
	
	//Delete any payment by the payment id
	public int deletePayment(int paymentId) throws RegistrationException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		OracleConnection oracle = new OracleConnection();
		int result = 0;
		
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.DELETE_PAYMENT);
			// set the corresponding parameter
			stmt.setInt(1, paymentId);
			// execute the delete statement
			result = stmt.executeUpdate();
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}catch(Exception e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			close(null, stmt, conn);
		}
		return result;
	}
	
	//Close all the open connections
	private void close(ResultSet resultSet, PreparedStatement stmt, Connection conn) throws SQLException {
		if(resultSet != null) {
			resultSet.close();
		}
		if(stmt != null) {
			stmt.close();
		}
		if(conn != null) {
			conn.close();
		}
	}	
}
