package banking;

public class DepositCommandValidator extends CommandValidator {

    public DepositCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean checkCommandType(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("deposit")) {
            return validateNumberOfArguments(command);
        } else {
            return false;
        }
    }

    public boolean validateDepositToAccount(String command) {
        String[] commandArgs = command.split(" ");
        return (commandArgs.length == 3 && validateDepositAccountID(command) && validateDepositAccountAmount(command));
    }

    public boolean validateDepositAccountID(String command) {
        String[] commandArgs = command.split(" ");
        return (this.bank.accountExistsByID(commandArgs[1]) && commandArgs[1].length() == 8 && validateAccountIDIsInteger(command));
    }

    public boolean validateDepositAccountAmount(String command) {
        String[] commandArgs = command.split(" ");
        try {
            return (this.bank.accountDepositWithinBounds(commandArgs[1], Double.parseDouble(commandArgs[2])));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean validateNumberOfArguments(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs.length == 3) {
            return validateDepositToAccount(command);
        } else {
            return false;
        }
    }
}
