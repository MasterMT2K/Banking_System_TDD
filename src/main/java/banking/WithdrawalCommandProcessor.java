package banking;

public class WithdrawalCommandProcessor extends CommandProcessor {

    public WithdrawalCommandProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void checkCommandType(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("withdraw")) {
            withdrawFromAccount(command);
        }
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
