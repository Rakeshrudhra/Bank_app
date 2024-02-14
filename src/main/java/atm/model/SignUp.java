package atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import atm.repository.DatabaseConnector;


public class SignUp {
	private Connection connection;
    private DatabaseConnector databaseConnector;

    // Use the singleton instance of DatabaseConnector
    public SignUp() {
        this.databaseConnector = DatabaseConnector.getInstance();
        this.connection = databaseConnector.getConnection();
    }

	  public void signUp(String username, String email, String password) {
	        try {
	            // Check if the username or email already exists
	            if (isUserExists(username,email)){
	                System.out.println("Username or Email already exists. Please choose a different one.");
	            } else {
	                String signUpSql = "INSERT INTO users_auth (username, password_hash,email) VALUES (?, ?, ?)";
	                try (PreparedStatement signUpStatement = connection.prepareStatement(signUpSql)) {
	                    signUpStatement.setString(1, username);
	                    signUpStatement.setString(2,password);
	                    signUpStatement.setString(3, email);
	                    signUpStatement.executeUpdate();

	                    System.out.println("Account created successfully for " + username);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error creating user account in the database.");
	        }
	    }
	  
	  public boolean isUserExists(String username, String email) {
		    try {
		        String sql = "SELECT COUNT(*) AS count FROM users_auth WHERE username = ? OR email = ?";
		        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
		            preparedStatement.setString(1, username);
		            preparedStatement.setString(2, email);

		            try (ResultSet resultSet = preparedStatement.executeQuery()) {
		                if (resultSet.next()) {
		                    int count = resultSet.getInt("count");
		                    return count > 0;
		                }
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println("Error checking if the user or email already exists.");
		    }
		    return false;
		}

}
