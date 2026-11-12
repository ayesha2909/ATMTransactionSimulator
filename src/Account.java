
import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {

    private String accountNumber;
    private String pin;
    private double balance;
    private ArrayList<String> transactions;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited: ₹" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        transactions.add("Withdrawn: ₹" + amount);
        return true;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }
}
