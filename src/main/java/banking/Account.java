package banking;

public abstract class Account {
    protected int monthsPassed = 0;
    protected double accountApr;
    protected double accountBalance;
    private String accountId;
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

    public int getMonthsPassed() {
        return monthsPassed;
    }

    public void addMonthsPassed(int monthsToPass) {
        monthsPassed += monthsToPass;
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

    public void calculateAPR() {
        double accountInterest = this.accountApr / 1200;
        this.accountBalance += (this.accountBalance * accountInterest);
    }


}
