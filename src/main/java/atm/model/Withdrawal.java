package atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import atm.repository.DatabaseConnector;

public class Withdrawal {

    private Connection connection;
    private DatabaseConnector databaseConnector;

    // Use the singleton instance of DatabaseConnector
    public Withdrawal() {
        this.databaseConnector = DatabaseConnector.getInstance();
        this.connection = databaseConnector.getConnection();
    }

    public void withdraw(int userId, double amount) {
        System.out.println(userId + " " + amount);
        UserData userData = new UserData();
        GetBalance getbalance = new GetBalance();
        userData = getbalance.getBankDetails(userId);

        if (userData != null) {
            if (amount <= userData.getBalance()) {
                double currentBalance = userData.getBalance() - amount;

                try {
                    String insertWithdrawSql = "INSERT INTO transaction_summary (user_id, timestamp, type, amount, balance) VALUES (?, ?, ?, ?, ?)";
                    String updateBalanceSql = "UPDATE balance SET balance = ? WHERE user_id = ?";

                    try (PreparedStatement insertWithdrawStatement = connection
                            .prepareStatement(insertWithdrawSql, Statement.RETURN_GENERATED_KEYS);
                            PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalanceSql)) {

                        // Insert withdrawal record
                        insertWithdrawStatement.setInt(1, userId);
                        insertWithdrawStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                        insertWithdrawStatement.setString(3, "debit"); // Assuming "debit" for withdrawal
                        insertWithdrawStatement.setDouble(4, amount);
                        insertWithdrawStatement.setDouble(5, currentBalance);
                        insertWithdrawStatement.executeUpdate();

                        // Get the generated withdrawal ID
                        ResultSet generatedKeys = insertWithdrawStatement.getGeneratedKeys();
                        int withdrawalId = -1;
                        if (generatedKeys.next()) {
                            withdrawalId = generatedKeys.getInt(1);
                        }

                        // Update balance
                        updateBalanceStatement.setDouble(1, currentBalance);
                        updateBalanceStatement.setInt(2, userId);
                        updateBalanceStatement.executeUpdate();

                        System.out.println("\u001B[34m" + "Withdrawal successful. New balance: " + currentBalance);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error updating balance or inserting withdrawal record in the database.");
                }
            } else {
                System.out.println("Insufficient funds for withdrawal");
            }
        } else {
            System.out.println("Account not found. Unable to withdraw.");
        }
    }
}
