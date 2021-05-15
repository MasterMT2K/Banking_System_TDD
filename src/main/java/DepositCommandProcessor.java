public class DepositCommandProcessor extends CommandProcessor {

    private Bank bank;

    public DepositCommandProcessor(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    @Override
    public void depositToAccount(String command) {
        String[] commandArgs = command.split(" ");
        String accountId = commandArgs[1];
        double accountDeposit = Double.parseDouble(commandArgs[2]);
        if (!bank.getAccounts().isEmpty() && bank.getAccounts().get(accountId).getAccountId().equals(accountId)) {
            bank.depositToAccount(accountId, accountDeposit);
        } else {
            String createCommand = "Create checking " + accountId + " 0";
            createAccount(createCommand);
            bank.depositToAccount(accountId, accountDeposit);
        }
    }
}
