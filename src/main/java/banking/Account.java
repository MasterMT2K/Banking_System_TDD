package banking;

public abstract class Account {
    private final double MIN_DEPOSIT = 0;
    private final double MAX_DEPOSIT = 0;
    private final double MIN_WITHDRAWAL = 0;
    private final double MAX_WITHDRAWAL = 0;
    private final String accountType = "Account";
    protected int monthsPassed = 0;
    protected double accountApr;
    protected double accountBalance;
    private String accountId;
    private boolean hasWithdrewThisMonth = false;

    public Account(String accountId, double accountApr, double accountBalance) {
        this.accountId = accountId;
        this.accountApr = accountApr;
        this.accountBalance = accountBalance;
    }

    public String getAccountType() {
        return accountType;
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

    public boolean getHasWithdrewThisMonth() {
        return hasWithdrewThisMonth;
    }

    public void setHasWithdrewThisMonth(boolean value) {
        hasWithdrewThisMonth = value;
    }

    public abstract void deposit(double depositAmount);

    public abstract void withdrawal(double withdrawalAmount);

    public abstract boolean checkDepositBounds(double depositAmount);

    public abstract boolean checkWithdrawalBounds(double withdrawalAmount);

    public boolean canWithdrawThisMonth() {
        return true;
    }

    public boolean checkCreateBounds(double createAmount) {
        return (createAmount == 0);
    }

    public void calculateAPR() {
        double accountInterest = this.accountApr / 1200;
        this.accountBalance += (this.accountBalance * accountInterest);
    }


}
