public class CDAccount extends Account {

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
    public void withdrawal(double withdrawalAmount) {
        super.withdrawal(withdrawalAmount);
    }

    @Override
    public boolean checkDepositBounds(double depositAmount) {
        return false;
    }
}
