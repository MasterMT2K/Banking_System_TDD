public class Account {
    private String accountId;
    private double accountApr;
    private double accountBalance;

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

    public void deposit(double depositAmount) {
        this.accountBalance += depositAmount;
    }

    public void withdrawal(double withdrawalAmount) {
        this.accountBalance -= withdrawalAmount;
        if (this.accountBalance <= 0) {
            this.accountBalance = 0;
        }
    }
}
