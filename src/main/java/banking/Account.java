package banking;

public abstract class Account {
    private String accountId;
    private double accountApr;
    private double accountBalance;
    private double MIN_DEPOSIT = 0;
    private double MAX_DEPOSIT = 0;

    public Account(String accountId, double accountApr, double accountBalance) {
        this.accountId = accountId;
        this.accountApr = accountApr;
        this.accountBalance = accountBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAccountApr() {
        return accountApr;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double newAccountBalance) {
        this.accountBalance = newAccountBalance;
    }

    public void deposit(double depositAmount) {
        this.accountBalance += depositAmount;
    }

    public void withdrawal(double withdrawalAmount) {
        this.accountBalance -= withdrawalAmount;
        if (this.accountBalance <= 0) {
            this.accountBalance = 0;
        }
    }

    public boolean checkDepositBounds(double depositAmount) {
        return depositAmount >= MIN_DEPOSIT && depositAmount <= MAX_DEPOSIT;
    }

    public boolean checkCreateBounds(double createAmount) {
        return (createAmount == 0);
    }
}
