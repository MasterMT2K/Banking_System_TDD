package banking;

public class CheckingAccount extends Account {

    private final double MIN_DEPOSIT = 0;
    private final double MAX_DEPOSIT = 1000;
    private final double MIN_WITHDRAWAL = 0;
    private final double MAX_WITHDRAWAL = 400;
    private final String accountType = "Checking";

    public CheckingAccount(String accountId, double accountApr, double accountBalance) {
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
    public void deposit(double depositAmount) {
        super.deposit(depositAmount);
    }

    @Override
    public void withdrawal(double withdrawalAmount) {
        super.withdrawal(withdrawalAmount);
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
        return super.canWithdrawThisMonth();
    }

    @Override
    public boolean checkCreateBounds(double createAmount) {
        return (createAmount == 0);
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
        return super.getHasWithdrewThisMonth();
    }

    @Override
    public void setHasWithdrewThisMonth(boolean value) {
        super.setHasWithdrewThisMonth(value);
    }
}
