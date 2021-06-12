package banking;

public class PassCommandValidator extends CommandValidator {

    public PassCommandValidator(Bank bank) {
        super(bank);
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
