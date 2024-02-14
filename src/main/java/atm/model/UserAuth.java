package atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import atm.repository.DatabaseConnector;

public class UserAuth {
	private Connection connection;
    private DatabaseConnector databaseConnector;

    // Use the singleton instance of DatabaseConnector
    public UserAuth() {
        this.databaseConnector = DatabaseConnector.getInstance();
        this.connection = databaseConnector.getConnection();
    }

	public int authenticateUser1(String username, String password) {
	    System.out.println(username);
	    System.out.println(password);
	    try {
	        // Use a prepared statement to avoid SQL injection
	        String query = "SELECT user_id FROM users_auth WHERE username = ? AND password_hash = ? ";
	        System.out.println("Executing query: " + query);
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            // Set parameters in the prepared statement
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, password);

	            // Execute the query and obtain the result set
	            ResultSet resultSet = preparedStatement.executeQuery();

	            // Check if there is at least one row in the result set
	            if (resultSet.next()) {
	                System.out.println("Authentication successful");
	                // Retrieve the account number from the result set
	                int account_number = resultSet.getInt("user_id");
	                System.out.println("Stored Account Number: " + account_number);
	                return account_number;
	            }
	        }
	    } catch (SQLException e) {
	        // Handle SQL exceptions (e.g., log or throw a custom exception)
	        e.printStackTrace();
	    }

	    // Return -1 if authentication failed
	    return -1;
	}
}