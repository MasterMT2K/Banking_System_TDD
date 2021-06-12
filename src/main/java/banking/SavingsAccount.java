package banking;

public class SavingsAccount extends Account {

    private final double MIN_DEPOSIT = 0;
    private final double MAX_DEPOSIT = 2500;
    private final double MIN_WITHDRAWAL = 0;
    private final double MAX_WITHDRAWAL = 1000;
    private boolean hasWithdrewThisMonth = false;

    public SavingsAccount(String accountId, double accountApr, double accountBalance) {
        super(accountId, accountApr, accountBalance);
    }

    @Override
    public String getAccountId() {
        return super.getAccountId();
    }

    @Override
    public double getAccountApr() {
        return super.getAccountApr();
    }

    @Override
    public double getAccountBalance() {
        return super.getAccountBalance();
    }

    @Override
    public void deposit(double depositAmount) {
        super.deposit(depositAmount);
    }

    @Override
    public void withdrawal(double withdrawalAmount) {
        this.accountBalance -= withdrawalAmount;
        if (this.accountBalance <= 0) {
            this.accountBalance = 0;
        }
        hasWithdrewThisMonth = true;
    }

    @Override
    public boolean checkDepositBounds(double depositAmount) {
        return depositAmount >= MIN_DEPOSIT && depositAmount <= MAX_DEPOSIT;
    }

    @Override
    public boolean checkWithdrawalBounds(double withdrawalAmount) {
        return withdrawalAmount >= MIN_WITHDRAWAL && withdrawalAmount <= MAX_WITHDRAWAL;
    }

    @Override
    public boolean canWithdrawThisMonth() {
        return !hasWithdrewThisMonth;
    }

    @Override
    public void calculateAPR() {
        super.calculateAPR();
    }

    @Override
    public int getMonthsPassed() {
        return super.getMonthsPassed();
    }

    @Override
    public void addMonthsPassed(int monthsPassed) {
        super.addMonthsPassed(monthsPassed);
    }

    @Override
    public boolean getHasWithdrewThisMonth() {
        return this.hasWithdrewThisMonth;
    }

    @Override
    public void setHasWithdrewThisMonth(boolean value) {
        this.hasWithdrewThisMonth = value;
    }
}
