package banking;

public class PassCommandProcessor extends CommandProcessor {

    public PassCommandProcessor(Bank bank) {
        super(bank);
    }


    @Override
    public void checkCommandType(String command) {
        String[] commandArgs = command.split(" ");
        if (commandArgs[0].equalsIgnoreCase("pass")) {
            passTime(command);
        }
    }

    public void passTime(String command) {
        String[] commandArgs = command.split(" ");
        int numberOfMonthsToPass = Integer.parseInt(commandArgs[1]);
        for (int monthsPassed = 0; monthsPassed < numberOfMonthsToPass; monthsPassed++) {
            for (String accountId : bank.getAccounts().keySet()) {
                bank.getAccounts().get(accountId).addMonthsPassed(1);
                if (bank.getAccounts().get(accountId).getAccountBalance() == 0) {
                    removeAccount(accountId);
                } else if (bank.getAccounts().get(accountId).getAccountBalance() < 100) {
                    deductMinimumBalanceFee(accountId);
                } else {
                    calculateAccountAPR(accountId);
                }
            }
        }
    }

    public void removeAccount(String accountId) {
        bank.removeAccount(accountId);
    }

    public void deductMinimumBalanceFee(String accountId) {
        bank.deductMinimumBalanceFee(accountId);
    }

    public void calculateAccountAPR(String accountId) {
        bank.calculateAPR(accountId);
    }
}
