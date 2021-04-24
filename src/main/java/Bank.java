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

    public void addAccount(String accountId, double accountApr, double accountBalance) {
        accounts.put(accountId, new Account(accountId, accountApr, accountBalance));
    }

    public void depositToAccount(String accountId, double depositAmount) {
        accounts.get(accountId).deposit(depositAmount);
    }

    public void withdrawalFromAccount(String accountId, double withdrawalAmount) {
        accounts.get(accountId).withdrawal(withdrawalAmount);
    }
}
