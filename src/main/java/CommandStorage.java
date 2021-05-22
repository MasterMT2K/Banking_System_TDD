import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

    private List<String> InvalidCommands = new ArrayList<>();

    public CommandStorage() {
    }

    public void addInvalidCommandToStorage(String command) {
        InvalidCommands.add(command);
    }

    public List<String> getInvalidCommands() {
        return InvalidCommands;
    }
}
