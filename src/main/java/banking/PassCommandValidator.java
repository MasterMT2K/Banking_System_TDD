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
            if (Integer.parseInt(commandArgs[1]) >= 1 && Integer.parseInt(commandArgs[1]) <= 60) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateNumberOfArguments(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs.length == 2) {
            return validatePassTimeValue(command);
        } else {
            return false;
        }
    }
}
