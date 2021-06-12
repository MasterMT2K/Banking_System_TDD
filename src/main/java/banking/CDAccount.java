package banking;

public class CDAccount extends Account {

    private final double MIN_CREATE_AMOUNT = 1000;
    private final double MAX_CREATE_AMOUNT = 10000;
    private final double NUM_MONTHLY_APR_CALCULATIONS = 4;
    private final String accountType = "Cd";

    public CDAccount(String accountId, double accountApr, double accountBalance) {
        super(accountId, accountApr, accountBalance);
    }

    @Override
    public String getAccountType() {
        return this.accountType;
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
    public void setAccountBalance(double newAccountBalance) {
        super.setAccountBalance(newAccountBalance);
    }

    @Override
    public void withdrawal(double withdrawalAmount) {
        double accountBalance = this.getAccountBalance();
        if (withdrawalAmount >= accountBalance) {
            this.setAccountBalance(0);
        }
    }

    @Override
    public boolean canWithdrawThisMonth() {
        return monthsPassed >= 12;
    }

    @Override
    public boolean checkDepositBounds(double depositAmount) {
        return false;
    }

    @Override
    public boolean checkWithdrawalBounds(double withdrawalAmount) {
        return !(withdrawalAmount < this.accountBalance);
    }

    @Override
    public boolean checkCreateBounds(double createAmount) {
        return createAmount >= MIN_CREATE_AMOUNT && createAmount <= MAX_CREATE_AMOUNT;
    }

    @Override
    public void calculateAPR() {
        double accountInterest = this.accountApr / 1200;
        for (int monthlyAPRInstallment = 0; monthlyAPRInstallment < NUM_MONTHLY_APR_CALCULATIONS; monthlyAPRInstallment++) {
            this.accountBalance += (this.accountBalance * accountInterest);
        }
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
    public void deposit(double depositAmount) {
    }
}
