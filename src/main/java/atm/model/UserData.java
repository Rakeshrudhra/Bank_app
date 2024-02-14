package atm.model;



import java.util.ArrayList;
import java.util.List;

public class UserData {
    private int accountNumber;
    private String name;
    private String adhar_number;
    private String phone_number;
    private double balance;
   

	private double withdraw_amount;
    private List<Transaction> transactionHistory;  
    private int userId; 
    private String address;
     double initialDeposit;
    public UserData() {
    	
        this.transactionHistory = new ArrayList<>();
    }

    public UserData(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public UserData(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public UserData(int accountNumber, String name, String adhar_number, String phone_number) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.adhar_number = adhar_number;
        this.phone_number = phone_number;
        this.transactionHistory = new ArrayList<>();
    }

    public UserData(String name, String adhar_number, String phone_number) {
        this.name = name;
        this.adhar_number = adhar_number;
        this.phone_number = phone_number;
        this.transactionHistory = new ArrayList<>();
    }
    public UserData(int accountNumber, String name, String adhar_number, String phone_number,String address ,double initialDeposit ) {
    	this.accountNumber = accountNumber;
        this.name = name;
        this.adhar_number = adhar_number;
        this.phone_number = phone_number;
        this.address=address;
        this.initialDeposit=initialDeposit;
    }

    public String getAccountHolder() {
        return name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            withdraw_amount = amount;
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public String getAdhar_number() {
        return adhar_number;
    }

    public void setAdhar_number(String adhar_number) {
        this.adhar_number = adhar_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public double getWithdraw_amount() {
        return withdraw_amount;
    }

    public void setWithdraw_amount(double withdraw_amount) {
        this.withdraw_amount = withdraw_amount;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // New getter and setter methods for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

		
}
