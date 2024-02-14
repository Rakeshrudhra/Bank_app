package atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import atm.repository.DatabaseConnector;

public class UserDashboard_data {
	
	private Connection connection;
    private DatabaseConnector databaseConnector;

    // Use the singleton instance of DatabaseConnector
    public  UserDashboard_data () {
        this.databaseConnector = DatabaseConnector.getInstance();
        this.connection = databaseConnector.getConnection();
    }
    public UserData getBankDetailsWithTransactions(int userId) {
        this.connection = databaseConnector.getConnection();
        UserData userData = null;

        try {
            String sql = "SELECT " +
                    "pd.userID, " +
                    "pd.name, " +
                    "pd.adhar_number, " +
                    "pd.phone_number, " +
                    "pd.address, " +
                    "ts.transaction_id, " +
                    "ts.timestamp, " +
                    "ts.type, " +
                    "ts.amount, " +
                    "ts.balance, " +
                    "b.balance AS user_balance " + // Include balance from the balance table
                    "FROM " +
                    "personal_details pd " +
                    "JOIN " +
                    "transaction_summary ts ON pd.userID = ts.user_id " +
                    "JOIN " +
                    "balance b ON pd.userID = b.user_id " +  // Join with the balance table
                    "WHERE " +
                    "pd.userID = ? " +
                    "ORDER BY ts.timestamp DESC " +  // Order by timestamp in descending order
                    "LIMIT 5";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userData = new UserData();
                        userData.setUserId(resultSet.getInt("userID"));
                        userData.setName(resultSet.getString("name"));
                        userData.setAdhar_number(resultSet.getString("adhar_number"));
                        userData.setPhone_number(resultSet.getString("phone_number"));
                        userData.setAddress(resultSet.getString("address"));

                        // Set user balance
                        double userBalance = resultSet.getDouble("user_balance");
                        userData.setBalance(userBalance);

                        do {
                            int transactionId = resultSet.getInt("transaction_id");
                            java.sql.Timestamp timestamp = resultSet.getTimestamp("timestamp");
                            String type = resultSet.getString("type");
                            double amount = resultSet.getDouble("amount");
                            double transactionBalance = resultSet.getDouble("balance");

                            // Use the extracted balance from the transaction_summary table
                            userData.addTransaction(new Transaction(transactionId, timestamp, type, amount, transactionBalance));
                        } while (resultSet.next());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving bank details with transactions from the database.");
        }

        return userData;
    }

}
