package atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import atm.repository.DatabaseConnector;

public class Deposite {
	  private Connection connection;
	    private DatabaseConnector databaseConnector;

	    // Use the singleton instance of DatabaseConnector
	    public Deposite() {
	        this.databaseConnector = DatabaseConnector.getInstance();
	        this.connection = databaseConnector.getConnection();
	    }
	public int  deposit(int userId, double amount) {
	    System.out.println(userId + " " + amount);
	    UserData userData = new UserData();
	    GetBalance getbalance = new GetBalance();
	    userData = getbalance.getBankDetails(userId);
	    double balance = userData.getBalance();
	    double currentBalance = balance + amount;
	    try {
	        String insertDepositSql = "INSERT INTO transaction_summary (user_id, timestamp, type, amount, balance) VALUES (?, ?, ?, ?, ?)";
	        String updateBalanceSql = "UPDATE balance SET balance = ? WHERE user_id = ?";

	        try (PreparedStatement insertDepositStatement = connection.prepareStatement(insertDepositSql, Statement.RETURN_GENERATED_KEYS);
	             PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalanceSql)) {

	            // Insert deposit record
	            insertDepositStatement.setInt(1, userId);
	            insertDepositStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
	            insertDepositStatement.setString(3, "credit"); // Assuming "credit" for deposit
	            insertDepositStatement.setDouble(4, amount);
	            insertDepositStatement.setDouble(5, currentBalance);
	            insertDepositStatement.executeUpdate();

	            // Get the generated deposit ID
	            ResultSet generatedKeys = insertDepositStatement.getGeneratedKeys();
	            int depositId = -1;
	            if (generatedKeys.next()) {
	                depositId = generatedKeys.getInt(1);
	            }

	            // Update balance
	            updateBalanceStatement.setDouble(1, currentBalance);
	            updateBalanceStatement.setInt(2, userId);
	            updateBalanceStatement.executeUpdate();

	            System.out.println("\u001B[34m" + "Deposit successful.\nCurrent balance: " + currentBalance);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error updating balance or inserting deposit record in the database.");
	    }
		return userId;
	}
}
