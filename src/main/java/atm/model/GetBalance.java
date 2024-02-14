package atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import atm.repository.DatabaseConnector;

public class GetBalance {
	  private Connection connection;
	    private DatabaseConnector databaseConnector;

	    // Use the singleton instance of DatabaseConnector
	    public GetBalance() {
	        this.databaseConnector = DatabaseConnector.getInstance();
	        this.connection = databaseConnector.getConnection();
	    }
 public UserData getBankDetails(int userId) {
    String getUserDataSql = "SELECT * FROM balance WHERE user_id = ?";
    System.out.println("its excuted");
    try (PreparedStatement getUserDataStatement = connection.prepareStatement(getUserDataSql)) {
        getUserDataStatement.setInt(1, userId);

        try (ResultSet resultSet = getUserDataStatement.executeQuery()) {
            if (resultSet.next()) {
                UserData userData = new UserData();
                userData.setUserId(resultSet.getInt("user_id"));
                userData.setBalance(resultSet.getDouble("balance"));
                // Add other user details as needed

                return userData;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error retrieving user data from the database.");
    }

    return null; // Return null if user data is not found
}
}
