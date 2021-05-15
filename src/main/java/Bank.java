import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(String accountId, double accountApr, double accountBalance, String accountType) {
        if (accountType.equalsIgnoreCase("checking")) {
            accounts.put(accountId, new CheckingAccount(accountId, accountApr, accountBalance));
        } else if (accountType.equalsIgnoreCase("savings")) {
            accounts.put(accountId, new SavingsAccount(accountId, accountApr, accountBalance));
        } else if (accountType.equalsIgnoreCase("cd")) {
            accounts.put(accountId, new CDAccount(accountId, accountApr, accountBalance));
        }
    }

    public void depositToAccount(String accountId, double depositAmount) {
        accounts.get(accountId).deposit(depositAmount);
    }

    public void withdrawalFromAccount(String accountId, double withdrawalAmount) {
        accounts.get(accountId).withdrawal(withdrawalAmount);
    }

    public boolean accountExistsByID(String Id) {
        return getAccounts().get(Id) != null;
    }

    public boolean accountDepositWithinBounds(String accountId, double depositAmount) {
        return accounts.get(accountId).checkDepositBounds(depositAmount);
    }
}
