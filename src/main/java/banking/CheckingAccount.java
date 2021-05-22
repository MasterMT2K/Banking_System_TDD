package banking;

public class CheckingAccount extends Account {

    private double MIN_DEPOSIT = 0;
    private double MAX_DEPOSIT = 1000;

    public CheckingAccount(String accountId, double accountApr, double accountBalance) {
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
        super.withdrawal(withdrawalAmount);
    }

    @Override
    public boolean checkDepositBounds(double depositAmount) {
        return depositAmount >= MIN_DEPOSIT && depositAmount <= MAX_DEPOSIT;
    }

    @Override
    public boolean checkCreateBounds(double createAmount) {
        return (createAmount == 0);
    }
}
