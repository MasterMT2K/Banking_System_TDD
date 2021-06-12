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
        bank.passTime(command);
    }
}
