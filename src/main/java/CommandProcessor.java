public class CommandProcessor {

    private Bank bank;
    private CreateCommandProcessor createCommandProcessor;
    private DepositCommandProcessor depositCommandProcessor;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void checkCommandType(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("create")) {
            createAccount(command);
        } else if (commandArgs[0].equalsIgnoreCase("deposit")) {
            depositToAccount(command);
        }
    }

    public void createAccount(String command) {
        String[] commandArgs = command.split(" ");
        String accountType = commandArgs[1];
        String accountId = commandArgs[2];
        double accountApr = Double.parseDouble(commandArgs[3]);
        if (accountType.equalsIgnoreCase("cd")) {
            double accountBalance = Double.parseDouble(commandArgs[4]);
            bank.addAccount(accountId, accountApr, accountBalance, accountType);
        } else {
            bank.addAccount(accountId, accountApr, 0, accountType);
        }
    }

    public void depositToAccount(String command) {
        String[] commandArgs = command.split(" ");
        String accountId = commandArgs[1];
        double accountDeposit = Double.parseDouble(commandArgs[2]);
        if (!bank.getAccounts().isEmpty() && bank.getAccounts().get(accountId).getAccountId().equals(accountId)) {
            bank.depositToAccount(accountId, accountDeposit);
        } else {
            String createCommand = "Create checking " + accountId + " 0";
            createCommandProcessor.checkCommandType(createCommand);
            bank.depositToAccount(accountId, accountDeposit);
        }
    }
}
