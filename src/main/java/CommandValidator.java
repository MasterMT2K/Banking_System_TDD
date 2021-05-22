public class CommandValidator {

    private final double MIN_APR = 0;
    private final double MAX_APR = 10;
    Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean checkCommandType(String command) {
        CreateCommandValidator createCommandValidator = new CreateCommandValidator(bank);
        DepositCommandValidator depositCommandValidator = new DepositCommandValidator(bank);
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("create")) {
            return createCommandValidator.checkCommandType(command);
        } else if (commandArgs[0].equalsIgnoreCase("deposit")) {
            return depositCommandValidator.checkCommandType(command);
        } else {
            return false;
        }
    }

    public boolean validateCreateAccountID(String command) {
        String[] commandArgs = command.split(" ");
        return (!bank.accountExistsByID(commandArgs[2]) && commandArgs[2].length() == 8 && validateAccountIDIsInteger(command));
    }

    //citation for this check method: https://stackabuse.com/java-check-if-string-is-a-number/
    public boolean validateAccountIDIsInteger(String command) {
        String[] commandArgs = command.split(" ");
        try {
            int IdValue = Integer.parseInt(commandArgs[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateAccountAPR(String command) {
        String[] commandArgs = command.split(" ");
        if ((commandArgs.length == 4 || commandArgs.length == 5) && validateAccountAPRIsDouble(command)) {
            return Double.parseDouble(commandArgs[3]) >= MIN_APR && Double.parseDouble(commandArgs[3]) <= MAX_APR;
        } else {
            return false;
        }
    }

    public boolean validateAccountAPRIsDouble(String command) {
        String[] commandArgs = command.split(" ");
        try {
            double IdValue = Double.parseDouble(commandArgs[3]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
