public class DepositCommandValidator extends CommandValidator {

    private Bank bank;
    private CreateCommandValidator createCommandValidator;
    private DepositCommandValidator depositCommandValidator;

    public DepositCommandValidator(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    @Override
    public boolean checkCommandType(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("deposit")) {
            return validateDepositToAccount(command);
        } else {
            return false;
        }
    }

    @Override
    public boolean validateDepositToAccount(String command) {
        String[] commandArgs = command.split(" ");
        return (commandArgs.length == 3 && validateDepositAccountID(command) && validateDepositAccountAmount(command));
    }

    public boolean validateDepositAccountID(String command) {
        String[] commandArgs = command.split(" ");
        return (bank.accountExistsByID(commandArgs[1]) && commandArgs[1].length() == 8 && validateAccountIDIsInteger(command));
    }

    public boolean validateDepositAccountAmount(String command) {
        String[] commandArgs = command.split(" ");
        return (bank.accountDepositWithinBounds(commandArgs[1], Double.parseDouble(commandArgs[2])));
    }
}
