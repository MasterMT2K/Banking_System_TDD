package banking;

public class DepositCommandProcessor extends CommandProcessor {

    public DepositCommandProcessor(Bank bank) {
        super(bank);
    }

    public void depositToAccount(String command) {
        CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
        String[] commandArgs = command.split(" ");
        String accountId = commandArgs[1];
        double accountDeposit = Double.parseDouble(commandArgs[2]);
        if (!this.bank.getAccounts().isEmpty() && this.bank.getAccounts().get(accountId).getAccountId().equals(accountId)) {
            bank.depositToAccount(accountId, accountDeposit);
        }
    }
}
