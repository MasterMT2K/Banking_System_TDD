package banking;

import java.util.List;

public class MasterControl {

    Bank bank;
    CommandValidator commandValidator;
    CommandProcessor commandProcessor;
    CommandStorage commandStorage;

    public MasterControl(Bank bank, CommandValidator commandValidator, CommandProcessor commandProcessor, CommandStorage commandStorage) {
        this.bank = bank;
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (commandValidator.checkCommandType(command)) {
                commandProcessor.checkCommandType(command);
            } else {
                commandStorage.addInvalidCommandToStorage(command);
            }
        }
        return commandStorage.getInvalidCommands();
    }
}
