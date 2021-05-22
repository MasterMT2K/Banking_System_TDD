public class CommandProcessor {

    Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void checkCommandType(String command) {
        CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
        DepositCommandProcessor depositCommandProcessor = new DepositCommandProcessor(bank);
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("create")) {
            createCommandProcessor.createAccount(command);
        } else if (commandArgs[0].equalsIgnoreCase("deposit")) {
            depositCommandProcessor.depositToAccount(command);
        }
    }
}
