package banking;

public class PassCommandValidator extends CommandValidator {

    public PassCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean checkCommandType(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("pass")) {
            return validateNumberOfArguments(command);
        } else {
            return false;
        }
    }

    public boolean validatePassTimeValue(String command) {
        String[] commandArgs = command.split(" ");
        try {
            return Integer.parseInt(commandArgs[1]) >= 1 && Integer.parseInt(commandArgs[1]) <= 60;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean validateNumberOfArguments(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs.length == 2) {
            return validatePassTimeValue(command);
        } else {
            return false;
        }
    }
}
