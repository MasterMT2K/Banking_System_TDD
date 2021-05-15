import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

    private List<String> InvalidCommands = new ArrayList<>();
    private CreateCommandValidator createCommandValidator;
    private DepositCommandValidator depositCommandValidator;

    public CommandStorage(CreateCommandValidator createCommandValidator, DepositCommandValidator depositCommandValidator) {
        this.createCommandValidator = createCommandValidator;
        this.depositCommandValidator = depositCommandValidator;
    }

    public void addInvalidCommandToStorage(String command) {
        if (!createCommandValidator.checkCommandType(command) && !depositCommandValidator.checkCommandType(command)) {
            InvalidCommands.add(command);
        }
    }

    public List<String> getInvalidCommands() {
        return InvalidCommands;
    }
}
