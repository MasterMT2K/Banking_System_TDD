package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Account> accounts;
    private int MINIMUM_BALANCE_FEE = 25;

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

    public void transfer(String WithdrawFromAccountId, String DepositToAccountId, double transferAmount) {
        if (accounts.get(WithdrawFromAccountId).getAccountBalance() < transferAmount) {
            accounts.get(WithdrawFromAccountId).withdrawal(transferAmount);
            accounts.get(DepositToAccountId).deposit(accounts.get(WithdrawFromAccountId).getAccountBalance());
        } else {
            accounts.get(WithdrawFromAccountId).withdrawal(transferAmount);
            accounts.get(DepositToAccountId).deposit(transferAmount);
        }
    }

    public boolean accountExistsByID(String Id) {
        return getAccounts().get(Id) != null;
    }

    public boolean accountDepositWithinBounds(String accountId, double depositAmount) {
        return accounts.get(accountId).checkDepositBounds(depositAmount);
    }

    public boolean checkCreateBounds(String accountId, double createAmount) {
        return accounts.get(accountId).checkCreateBounds(createAmount);
    }

    public boolean accountWithdrawalWithinBounds(String accountId, double withdrawalAmount) {
        return accounts.get(accountId).checkWithdrawalBounds(withdrawalAmount);
    }

    public boolean canWithdrawalFromAccountThisMonth(String accountId) {
        return accounts.get(accountId).canWithdrawThisMonth();
    }

    public void removeAccount(String accountId) {
        accounts.remove(accountId);
    }

    public void deductMinimumBalanceFee(String accountId) {
        accounts.get(accountId).setAccountBalance(accounts.get(accountId).getAccountBalance() - MINIMUM_BALANCE_FEE);
    }

    public void calculateAPR(String accountId) {
        accounts.get(accountId).calculateAPR();
    }

    public void passTime(String command) {
        String[] commandArgs = command.split(" ");
        int numberOfMonthsToPass = Integer.parseInt(commandArgs[1]);
        for (int monthsPassed = 0; monthsPassed < numberOfMonthsToPass; monthsPassed++) {
            for (String accountId : getAccounts().keySet()) {
                getAccounts().get(accountId).addMonthsPassed(1);
                getAccounts().get(accountId).setHasWithdrewThisMonth(false);
                if (getAccounts().get(accountId).getAccountBalance() == 0) {
                    removeAccount(accountId);
                } else if (getAccounts().get(accountId).getAccountBalance() < 100) {
                    deductMinimumBalanceFee(accountId);
                } else {
                    calculateAPR(accountId);
                }
            }
        }
    }
}