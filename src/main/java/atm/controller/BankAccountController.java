package atm.controller;

import java.util.List;

import atm.model.BankAccountModel;
import atm.model.UserData;
import atm.model.Withdrawal;
import atm.model.UserAuth;
import atm.model.UserDashboard_data;
import atm.model.SignUp;
import atm.model.Transaction;
import atm.model.CreateAccount;
import atm.model.Deposite;
import atm.model.GetBalance;
import atm.model.OnlineTransaction;

public class BankAccountController {
	private BankAccountModel model;
	private UserAuth auth;
	private SignUp signup;
	private UserDashboard_data userDash;
	private CreateAccount addAccount;
	private Deposite deposite;
	private Withdrawal withdrawal;
	private UserData userdata;
	private OnlineTransaction onlineTrans;
	private GetBalance balance;

//    public BankAccountController(BankAccountModel model) {
//        this.model = model;
////        this.view = view;
//    }

	public BankAccountController(BankAccountModel model, SignUp signup) {
		this.model = model;
		this.signup = signup;
	}

	public BankAccountController(BankAccountModel model, UserAuth auth) {
		this.model = model;
		this.auth = auth;

	}

	public BankAccountController(UserDashboard_data userDash) {
		this.userDash = userDash;
	}

	public BankAccountController(CreateAccount addAccount) {
		this.addAccount = addAccount;
	}

	public BankAccountController(Deposite deposite) {
		this.deposite = deposite;
	}

	public BankAccountController(Withdrawal withdrawal) {
		this.withdrawal = withdrawal;
	}

	public BankAccountController(OnlineTransaction onlineTrans) {
		this.onlineTrans = onlineTrans;
	}
	 public BankAccountController(OnlineTransaction onlineTrans,GetBalance balance,Deposite deposite,Withdrawal withdrawal) {
	        this.balance = balance;
	        this.onlineTrans=onlineTrans;
	        this.withdrawal=withdrawal;
	        this.deposite=deposite;
	    }
//
//	public void checkBalance(int accountNumber) {
//        model.displayAccount(accountNumber);
//    }

//	public void addAccount(String name, String adhar_number, String phone_number, double initialDeposit,String address) {
//    	addAccount.addAccount( name, adhar_number, phone_number, initialDeposit,address);
////        view.displayMessage("Account created successfully! Account Number: " + model.generateAccountNumber());
//    }
//    
//    public void displayAccount(int accountNumber) {
//        model.displayAccount(accountNumber);
//    }

	public int deposit(int accountNumber, double amount) {
		return deposite.deposit(accountNumber, amount);
	}

	public void withdraw(int accountNumber, double amount) {
		withdrawal.withdraw(accountNumber, amount);
//        view.displayMessage(amount + " Withdrawal successful");
	}

	public void sendMoney(int senderAccountNumber, int receiverAccountNumber, double amount) {
		if (onlineTrans.isValidAccount(senderAccountNumber) && onlineTrans.isValidAccount(receiverAccountNumber)) {
//        	 model.insertTransaction(senderAccountNumber, receiverAccountNumber, amount);
			userdata = balance.getBankDetails(senderAccountNumber);
			if (userdata.getBalance() >= amount) {
				withdrawal.withdraw(senderAccountNumber, amount);
				deposite.deposit(receiverAccountNumber, amount);
//                view.displayMessage(amount + " Sent successfully from Account " + senderAccountNumber +
//                        " to Account " + receiverAccountNumber);
			} else {
//                view.displayMessage("Insufficient funds in Account " + senderAccountNumber);
			}
		} else {
//            view.displayMessage("Invalid account numbers");
		}
	}

	public boolean signUp(String username, String email, String password) {
		// Check if the username or email already exists
		if (signup.isUserExists(username, email)) {
			// Display a message or handle the case where the user already exists
			System.out.println("Username or Email already exists. Please choose a different one.");
			return false;
		} else {
			// Create a new account for the user
			signup.signUp(username, email, password);
			System.out.println("Account created successfully for " + username);
			return true;
		}
	}

	public int authenticateUser(String username, String password) {
		int v = auth.authenticateUser1(username, password);
		System.out.println(v);
		return v;
	}

	public UserData getBankDetailsWithTransactions(int userId) {
		UserData userData = userDash.getBankDetailsWithTransactions(userId);

		if (userData != null) {
			// Display user details
			System.out.println("User Details:");
			System.out.println("User ID: " + userData.getUserId());
			System.out.println("Name: " + userData.getName());
			System.out.println("Adhar Number: " + userData.getAdhar_number());
			System.out.println("Phone Number: " + userData.getPhone_number());
			System.out.println("Balance: " + userData.getBalance());
			System.out.println("Address" + userData.getAddress());

			// Display transaction history
			List<Transaction> transactions = userData.getTransactionHistory();
			if (transactions.size() > 0) {
				System.out.println("\nTransaction History:");
				for (Transaction transaction : transactions) {
					System.out.println("Transaction ID: " + transaction.getTransactionId());
					System.out.println("Timestamp: " + transaction.getTimestamp());
					System.out.println("Type: " + transaction.getType());
					System.out.println("Amount: " + transaction.getAmount());
					System.out.println("Balance: " + transaction.getBalance());
					System.out.println("-----------------------------");
				}
			} else {
				System.out.println("No transactions available for this account.");
			}
		} else {
			System.out.println("User not found.");
		}
		return userData;

	}

	public UserData addAccount(String name, String adharNumber, String phone, String address, double initialDeposit) {
		UserData userData = addAccount.addAccount(name, adharNumber, phone, address, initialDeposit);
		return userData;
	}

}
