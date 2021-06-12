package banking;

public class CreateCommandProcessor extends CommandProcessor {

    public CreateCommandProcessor(Bank bank) {
        super(bank);
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
}
