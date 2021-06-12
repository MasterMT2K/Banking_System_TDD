package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

    private List<String> InvalidCommands = new ArrayList<>();
    private List<String> Output = new ArrayList<>();

    public CommandStorage() {
    }

    public void assembleOutputToStorage(Bank bank) {
        for (String accountId : bank.getAccounts().keySet()) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            decimalFormat.setRoundingMode(RoundingMode.FLOOR);
            String accountType = bank.getAccounts().get(accountId).getAccountType();
            String accountBalance = decimalFormat.format(bank.getAccounts().get(accountId).getAccountBalance());
            String accountAPR = decimalFormat.format(bank.getAccounts().get(accountId).getAccountApr());
            String currentStatement = accountType + " " + accountId + " " + accountBalance + " " + accountAPR;
            Output.add(currentStatement);
            if (bank.getTransactionHistory().containsKey(accountId)) {
                List<String> accountHistory = bank.getTransactionHistory().get(accountId);
                Output.addAll(accountHistory);
            }
        }
        Output.addAll(InvalidCommands);
    }

    public void addInvalidCommandToStorage(String command) {
        InvalidCommands.add(command);
    }

    public List<String> getInvalidCommands() {
        return InvalidCommands;
    }

    public List<String> getOutput() {
        return Output;
    }

    public List<String> getOutput(Bank bank) {
        assembleOutputToStorage(bank);
        return getOutput();
    }
}
