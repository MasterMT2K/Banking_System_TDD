package banking;

public class TransferCommandProcessor extends CommandProcessor {

    public TransferCommandProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void checkCommandType(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("transfer")) {
            transfer(command);
        }
    }

    public void transfer(String command) {
        String[] commandArgs = command.split(" ");
        String withdrawFromAccountId = commandArgs[1];
        String depositToAccountId = commandArgs[2];
        double transferAmount = Double.parseDouble(commandArgs[3]);
        bank.transfer(withdrawFromAccountId, depositToAccountId, transferAmount);
        bank.addToTransactionHistory(withdrawFromAccountId, command);
        bank.addToTransactionHistory(depositToAccountId, command);
    }
}
