package atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import atm.repository.DatabaseConnector;

public class OnlineTransaction {
	
	private Connection connection;
    private DatabaseConnector databaseConnector;

    // Use the singleton instance of DatabaseConnector
    public OnlineTransaction() {
        this.databaseConnector = DatabaseConnector.getInstance();
        this.connection = databaseConnector.getConnection();
    }
	public boolean isValidAccount(int accountNumber) {
        try {
            String sql = "SELECT COUNT(*) AS count FROM balance WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, accountNumber);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error checking if the account is valid.");
        }
        return false;
    }
	
	
	
	
	public void insertTransaction(int senderUserId, int receiverUserId, double amount) {
	    String insertTransactionSql = "INSERT INTO transaction_details (sender_user_id, receiver_user_id, timestamp, amount) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransactionSql)) {
	        insertTransactionStatement.setInt(1, senderUserId);
	        insertTransactionStatement.setInt(2, receiverUserId);
	        insertTransactionStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis())); // Set current timestamp
	        insertTransactionStatement.setDouble(4, amount);

	        insertTransactionStatement.executeUpdate();

	        System.out.println("\u001B[34m" + "Transaction successful. Amount: $" + amount);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error inserting transaction record in the database.");
	    }
	}

}
