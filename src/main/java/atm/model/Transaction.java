package atm.model;

import java.sql.Timestamp;

public class Transaction {
	private int transactionId;
    private Timestamp timestamp;
    private String type;
    private double amount;
    private double balance;

    public Transaction(int transactionId, Timestamp timestamp, String type, double amount, double balance) {
        this.transactionId = transactionId;
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

}
