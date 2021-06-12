package banking;

public class TransferCommandValidator extends CommandValidator {

    public TransferCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validateNumberOfArguments(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs.length == 4) {
            return validateTransferCommand(command);
        } else {
            return false;
        }
    }

    public boolean validateTransferCommand(String command) {
        return (validateTransferFromAccount(command) && validateTransferToAccount(command));
    }

    public boolean validateTransferFromAccount(String command) {
        return validateWithdrawalFromAccount(command);
    }

    public boolean validateTransferToAccount(String command) {
        return validateDepositToAccount(command);
    }

    public boolean validateDepositToAccount(String command) {
        String[] commandArgs = command.split(" ");
        return (validateDepositAccountID(command) && validateDepositAccountAmount(command));
    }

    public boolean validateDepositAccountID(String command) {
        String[] commandArgs = command.split(" ");
        return (this.bank.accountExistsByID(commandArgs[2]) && commandArgs[2].length() == 8 && validateDepositAccountIDIsInteger(command));
    }

    public boolean validateDepositAccountAmount(String command) {
        String[] commandArgs = command.split(" ");
        try {
            return (this.bank.accountDepositWithinBounds(commandArgs[2], Double.parseDouble(commandArgs[3])));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateWithdrawalFromAccount(String command) {
        String[] commandArgs = command.split(" ");
        return (validateWithdrawalAccountID(command) && validateWithdrawalAccountAmount(command) && validateWithdrawalThisMonth(command));
    }

    public boolean validateWithdrawalAccountID(String command) {
        String[] commandArgs = command.split(" ");
        return (this.bank.accountExistsByID(commandArgs[1]) && commandArgs[1].length() == 8 && validateWithdrawalAccountIDIsInteger(command));
    }

    public boolean validateWithdrawalAccountAmount(String command) {
        String[] commandArgs = command.split(" ");
        try {
            return (this.bank.accountWithdrawalWithinBounds(commandArgs[1], Double.parseDouble(commandArgs[3])));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateWithdrawalThisMonth(String command) {
        String[] commandArgs = command.split(" ");
        return this.bank.canWithdrawalFromAccountThisMonth(commandArgs[1]);
    }

    public boolean validateDepositAccountIDIsInteger(String command) {
        String[] commandArgs = command.split(" ");
        try {
            int IdValue = Integer.parseInt(commandArgs[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateWithdrawalAccountIDIsInteger(String command) {
        String[] commandArgs = command.split(" ");
        try {
            int IdValue = Integer.parseInt(commandArgs[1]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
