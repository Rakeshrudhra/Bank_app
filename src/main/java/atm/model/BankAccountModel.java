package atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;




import atm.repository.DatabaseConnector;


public class BankAccountModel {
	private Random random;
	private Connection connection;
    private DatabaseConnector databaseConnector;

    // Use the singleton instance of DatabaseConnector
    public BankAccountModel() {
    	this.random = new Random();
        this.databaseConnector = DatabaseConnector.getInstance();
        this.connection = databaseConnector.getConnection();
    }

	public void deposit(int userId, double amount) {
        UserData userData = getBankDetails(userId);

        if (userData != null) {
            userData.deposit(amount);

            try {
                String updateBalanceSql = "UPDATE balance SET balance = ? WHERE user_id = ?";
                String insertDepositSql = "INSERT INTO deposit_history (user_id, deposit_amount, deposit_date) VALUES (?, ?, ?)";

                try (PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalanceSql);
                     PreparedStatement insertDepositStatement = connection.prepareStatement(insertDepositSql)) {

                    // Update balance
                    updateBalanceStatement.setDouble(1, userData.getBalance());
                    updateBalanceStatement.setInt(2, userId);
                    updateBalanceStatement.executeUpdate();

                    // Insert deposit record
                    insertDepositStatement.setInt(1, userId);
                    insertDepositStatement.setDouble(2, amount);
                    insertDepositStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                    insertDepositStatement.executeUpdate();

                    System.out.println("\u001B[34m" + "Deposit successful.\nCurrent balance: " + userData.getBalance());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error updating balance or inserting deposit record in the database.");
            }
        } else {
            System.out.println("User not found. Unable to deposit.");
        }
    }

	public void withdraw(int accountNumber, double amount) {
		UserData userData = getBankDetails(accountNumber);

		if (userData != null) {
			if (amount <= userData.getBalance()) {

				userData.withdraw(amount);
  
				updateBalance(accountNumber, userData.getBalance(), userData.getWithdraw_amount());
				System.out.println("\u001B[34m" + "Withdrawal successful. New balance: " + userData.getBalance());
			} else {
				System.out.println("Insufficient funds");
			}
		} else {
			System.out.println("Account not found. Unable to withdraw.");
		}
	}

	private void updateBalance(int accountNumber, double newBalance, double withdraw) {
	    try {
	        // Update balance in balance table
	        String updateBalanceSql = "UPDATE balance SET balance = ? WHERE user_id = ?";
	        try (PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalanceSql)) {
	            updateBalanceStatement.setDouble(1, newBalance);
	            updateBalanceStatement.setInt(2, accountNumber);
	            updateBalanceStatement.executeUpdate();
	        }

	        // Insert withdrawal record into withdrawal_history table with the current timestamp
	        String insertWithdrawalSql = "INSERT INTO withdrawal_history (user_id, withdrawal_amount, withdrawal_date) VALUES (?, ?, ?)";
	        try (PreparedStatement insertWithdrawalStatement = connection.prepareStatement(insertWithdrawalSql)) {
	            insertWithdrawalStatement.setInt(1, accountNumber);
	            insertWithdrawalStatement.setDouble(2,withdraw);
	            insertWithdrawalStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis())); // Set current timestamp
	            insertWithdrawalStatement.executeUpdate();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error updating balance and withdrawal history in the database.");
	    }
	}



	public int generateAccountNumber() {
		return random.nextInt(100000000);
	}

//	public void addAccount(String accountHolder, String adhar_number, String phone_number, double initialDeposit,String address) {
//		if (initialDeposit < MINIMUM_DEPOSIT_AMOUNT) {
//			System.out.println("\u001B[31m"+"ACCOUNT  NOT CREATE MINIMUM  DEPOSIT AMOUNT REQUIRED : " + MINIMUM_DEPOSIT_AMOUNT);
//			return; // Reject account creation
//		}
//
//		int accountNumber = generateAccountNumber();
//		storeUserData(accountNumber, accountHolder, adhar_number, phone_number, initialDeposit,address);
////		System.out.println("\u001B[34m" + "Account created successfully!");
////		System.out.println("\u001B[34m" + "Your Account Number: " + accountNumber);
//	}
//
//	public void displayAccount(int accountNumber) {
//		UserData userData = getBankDetails(accountNumber);
//
//		if (userData != null) {
//			System.out.println("\u001B[34m" + "Account holder:"+userData.getAccountHolder());
//			System.out.println("\u001B[34m" + "Account Number:" + userData.getAccountNumber());
//			System.out.println("\u001B[34m" + "Balance: " + userData.getBalance());
//		} else {
//			System.out.println("Account not found.");
//		}
//	}
//
//	private void storeUserData(int accountNumber, String accountHolder, String adhar_number, String phone_number,
//			double initialDeposit, String address) {
//		try {
//
//			String personalDetailsSql = "INSERT INTO personal_details (userID, name, adhar_number, phone_number,address) VALUES (?, ?, ?, ?,?)";
//			try (PreparedStatement personalDetailsStatement = connection.prepareStatement(personalDetailsSql)) {
//
//				personalDetailsStatement.setInt(1, accountNumber);
//				personalDetailsStatement.setString(2, accountHolder);
//				personalDetailsStatement.setString(3, adhar_number);
//				personalDetailsStatement.setString(4, phone_number);
//				personalDetailsStatement.setString(5, );
//				personalDetailsStatement.executeUpdate();
//
//				String bankDetailsSql = "INSERT INTO balance (user_id, balance) VALUES (?, ?)";
//				try (PreparedStatement bankDetailsStatement = connection.prepareStatement(bankDetailsSql)) {
//
//					bankDetailsStatement.setInt(1, accountNumber);
//					bankDetailsStatement.setDouble(2, initialDeposit);
//					bankDetailsStatement.executeUpdate();
//
//					//System.out.println("\u001B[34m" + "Initial deposit stored in the bank_details table successfully!");
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Error storing user data in the database.");
//		}
//	}

	public UserData getBankDetails(int accountNumber) {
	    UserData userData = null;
	    try {
	        String sql = "SELECT personal_details.userID, personal_details.name, balance.balance " +
	                     "FROM personal_details " +
	                     "JOIN balance ON personal_details.userID = balance.user_id " +
	                     "WHERE personal_details.userID = ?";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, accountNumber);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                	
	                    int retrievedUserId = resultSet.getInt("userID");
	                    String name=resultSet.getString("name");// Use "userID" here
	                    double balance = resultSet.getDouble("balance");

	                    userData = new UserData(retrievedUserId,name, balance);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error retrieving bank details from the database.");
	    }
	    return userData;
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
	  public void insertTransaction(int senderAccountNumber, int receiverAccountNumber, double amount) {
	        String insertTransactionSql = "INSERT INTO transaction_history (timestamp, sender_account_number, receiver_account_number, amount) VALUES (?, ?, ?, ?)";

	        try (PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransactionSql)) {
	            insertTransactionStatement.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis())); // Set current timestamp
	            insertTransactionStatement.setInt(2, senderAccountNumber);
	            insertTransactionStatement.setInt(3, receiverAccountNumber);
	            insertTransactionStatement.setDouble(4, amount);

	            insertTransactionStatement.executeUpdate();

	            System.out.println("\u001B[34m" + "Transaction successful. Amount: $" + amount);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error inserting transaction record in the database.");
	        }
	    }
	  
	  
//	  public void signUp(String username, String email, String password) {
//	        try {
//	            // Check if the username or email already exists
//	            if (isUserExists(username) || isEmailExists(email)) {
//	                System.out.println("Username or Email already exists. Please choose a different one.");
//	            } else {
//	                String signUpSql = "INSERT INTO users_auth (username, password_hash,email) VALUES (?, ?, ?)";
//	                try (PreparedStatement signUpStatement = connection.prepareStatement(signUpSql)) {
//	                    signUpStatement.setString(1, username);
//	                    signUpStatement.setString(2,password);
//	                    signUpStatement.setString(4, email);
//	                    signUpStatement.executeUpdate();
//
//	                    System.out.println("Account created successfully for " + username);
//	                }
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	            System.out.println("Error creating user account in the database.");
//	        }
//	    }
//
//	    public boolean isUserExists(String username) {
//	        try {
//	            String sql = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
//	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//	                preparedStatement.setString(1, username);
//
//	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//	                    if (resultSet.next()) {
//	                        int count = resultSet.getInt("count");
//	                        return count > 0;
//	                    }
//	                }
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	            System.out.println("Error checking if the user already exists.");
//	        }
//	        return false;
//	    }
//
//	    public boolean isEmailExists(String email) {
//	        try {
//	            String sql = "SELECT COUNT(*) AS count FROM users WHERE email = ?";
//	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//	                preparedStatement.setString(1, email);
//
//	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//	                    if (resultSet.next()) {
//	                        int count = resultSet.getInt("count");
//	                        return count > 0;
//	                    }
//	                }
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	            System.out.println("Error checking if the email already exists.");
//	        }
//	        return false;
//	    }

	    

	

}
