package banking;

public class WithdrawalCommandProcessor extends CommandProcessor {

    public WithdrawalCommandProcessor(Bank bank) {
        super(bank);
    }

    public void withdrawFromAccount(String command) {
        String[] commandArgs = command.split(" ");
        String accountId = commandArgs[1];
        double withdrawalAmount = Double.parseDouble(commandArgs[2]);
        if (!this.bank.getAccounts().isEmpty() && this.bank.getAccounts().get(accountId).getAccountId().equals(accountId)) {
            bank.withdrawalFromAccount(accountId, withdrawalAmount);
            bank.addToTransactionHistory(accountId, command);
        }
    }
}
