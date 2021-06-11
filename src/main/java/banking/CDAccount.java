package banking;

public class CDAccount extends Account {

    private final double MIN_CREATE_AMOUNT = 1000;
    private final double MAX_CREATE_AMOUNT = 10000;

    public CDAccount(String accountId, double accountApr, double accountBalance) {
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
    public void setAccountBalance(double newAccountBalance) {
        super.setAccountBalance(newAccountBalance);
    }

    @Override
    public void withdrawal(double withdrawalAmount) {
        double accountBalance = this.getAccountBalance();
        if (withdrawalAmount >= accountBalance) {
            this.setAccountBalance(0);
        } else {
            accountBalance -= withdrawalAmount;
            setAccountBalance(accountBalance);
        }
    }

    @Override
    public boolean checkDepositBounds(double depositAmount) {
        return false;
    }

    @Override
    public boolean checkCreateBounds(double createAmount) {
        return createAmount >= MIN_CREATE_AMOUNT && createAmount <= MAX_CREATE_AMOUNT;
    }
}
