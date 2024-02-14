package atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import atm.repository.DatabaseConnector;

public class CreateAccount {
	private Random random;
	private static final double MINIMUM_DEPOSIT_AMOUNT = 500.0;
	private Connection connection;
	private DatabaseConnector databaseConnector;

	// Use the singleton instance of DatabaseConnector
	public CreateAccount() {
		this.databaseConnector = DatabaseConnector.getInstance();
		this.connection = databaseConnector.getConnection();
		this.random = new Random();
	}

	public UserData addAccount(String accountHolder, String adhar_number, String phone_number, String address,
			double initialDeposit) {
		if (initialDeposit < MINIMUM_DEPOSIT_AMOUNT) {
			System.out.println(
					"\u001B[31m" + "ACCOUNT NOT CREATE MINIMUM DEPOSIT AMOUNT REQUIRED: " + MINIMUM_DEPOSIT_AMOUNT);
			return null; // Reject account creation
		}

		int accountNumber = generateAccountNumber();
		System.out.println("\u001B[34m" + "Account created successfully!");
		System.out.println("\u001B[34m" + "Your Account Number: " + accountNumber);
		System.out.println(accountHolder + "//////////////");

		UserData userData = createAccount_userData(accountNumber, accountHolder, adhar_number, phone_number, address,
				initialDeposit);

		return userData;
	}

	public int generateAccountNumber() {
		return random.nextInt(100000000);
	}

	public UserData createAccount_userData(int accountNumber, String name, String adhar_number, String phone_number,
			String address, double initialDeposit) {
		System.out.println(accountNumber);
		System.out.println(name);
		System.out.println(adhar_number);
		System.out.println(phone_number);
		System.out.println(initialDeposit);
		System.out.println(address);

		try {

			String personalDetailsSql = "INSERT INTO personal_details (userID, name, adhar_number, phone_number,address) VALUES (?, ?, ?, ?,?)";
			try (PreparedStatement personalDetailsStatement = connection.prepareStatement(personalDetailsSql)) {

				personalDetailsStatement.setInt(1, accountNumber);
				personalDetailsStatement.setString(2, name);
				personalDetailsStatement.setString(3, adhar_number);
				personalDetailsStatement.setString(4, phone_number);
				personalDetailsStatement.setString(5, address);
				personalDetailsStatement.executeUpdate();

				String bankDetailsSql = "INSERT INTO balance (user_id, balance) VALUES (?, ?)";
				try (PreparedStatement bankDetailsStatement = connection.prepareStatement(bankDetailsSql)) {

					bankDetailsStatement.setInt(1, accountNumber);
					bankDetailsStatement.setDouble(2, initialDeposit);
					bankDetailsStatement.executeUpdate();

					return new UserData(accountNumber, name, adhar_number, phone_number, address, initialDeposit);

					// System.out.println("\u001B[34m" + "Initial deposit stored in the bank_details
					// table successfully!");
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error storing user data in the database.");
		}
		return null;
	}
}
