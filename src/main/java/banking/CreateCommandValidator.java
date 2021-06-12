package banking;

public class CreateCommandValidator extends CommandValidator {

    private final double MIN_APR = 0;
    private final double MAX_APR = 10;
    private final double MIN_CDSTARTAMOUNT = 1000;
    private final double MAX_CDSTARTAMOUNT = 10000;

    public CreateCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean validateCreateAccount(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs[1].equalsIgnoreCase("checking")) {
            return validateCreateCheckingAccount(command);
        } else if (commandArgs[1].equalsIgnoreCase("savings")) {
            return validateCreateSavingsAccount(command);
        } else if (commandArgs[1].equalsIgnoreCase("cd")) {
            return validateCreateCDAccount(command);
        } else {
            return false;
        }
    }

    public boolean validateCreateCheckingAccount(String command) {
        return validateCreateAccountID(command) && validateAccountAPR(command);
    }

    private boolean validateCreateSavingsAccount(String command) {
        return validateCreateAccountID(command) && validateAccountAPR(command);
    }

    private boolean validateCreateCDAccount(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs.length == 5) {
            return validateCreateAccountID(command) && validateAccountAPR(command) && validateCDCreateAmount(command);
        } else {
            return false;
        }
    }

    @Override
    public boolean validateCreateAccountID(String command) {
        String[] commandArgs = command.split(" ");
        return (!this.bank.accountExistsByID(commandArgs[2]) && commandArgs[2].length() == 8 && validateAccountIDIsInteger(command));
    }

    public boolean validateCDCreateAmount(String command) {
        String[] commandArgs = command.split(" ");
        return Double.parseDouble(commandArgs[4]) >= MIN_CDSTARTAMOUNT && Double.parseDouble(commandArgs[4]) <= MAX_CDSTARTAMOUNT;
    }

    @Override
    public boolean validateNumberOfArguments(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs.length == 4) {
            return validateCreateAccount(command);
        } else if (commandArgs.length == 5 && (commandArgs[1].equalsIgnoreCase("cd"))) {
            return validateCreateAccount(command);
        } else {
            return false;
        }
    }
}
