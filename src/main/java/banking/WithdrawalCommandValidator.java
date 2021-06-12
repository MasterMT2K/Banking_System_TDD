package banking;

public class WithdrawalCommandValidator extends CommandValidator {

    public WithdrawalCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean checkCommandType(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("withdraw")) {
            return validateNumberOfArguments(command);
        } else {
            return false;
        }
    }

    @Override
    public boolean validateNumberOfArguments(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs.length == 3) {
            return validateWithdrawalFromAccount(command);
        } else {
            return false;
        }
    }

    public boolean validateWithdrawalFromAccount(String command) {
        String[] commandArgs = command.split(" ");
        return (validateWithdrawalAccountID(command) && validateWithdrawalAccountAmount(command) && validateWithdrawalThisMonth(command));
    }

    public boolean validateWithdrawalAccountID(String command) {
        String[] commandArgs = command.split(" ");
        return (this.bank.accountExistsByID(commandArgs[1]) && commandArgs[1].length() == 8 && validateAccountIDIsInteger(command));
    }

    public boolean validateWithdrawalAccountAmount(String command) {
        String[] commandArgs = command.split(" ");
        return (this.bank.accountWithdrawalWithinBounds(commandArgs[1], Double.parseDouble(commandArgs[2])));
    }

    public boolean validateWithdrawalThisMonth(String command) {
        String[] commandArgs = command.split(" ");
        return this.bank.canWithdrawalFromAccountThisMonth(commandArgs[1]);
    }
}
