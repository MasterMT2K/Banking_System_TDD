import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStorageTest {
    CreateCommandValidator createCommandValidator;
    DepositCommandValidator depositCommandValidator;
    CommandStorage commandStorage;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createCommandValidator = new CreateCommandValidator(bank);
        depositCommandValidator = new DepositCommandValidator(bank);
        commandStorage = new CommandStorage(createCommandValidator, depositCommandValidator);
    }

    @Test
    void store_invalid_create_checking_command() {
        commandStorage.addInvalidCommandToStorage("Create checking 123456789 0");
        assertEquals("Create checking 123456789 0", commandStorage.getInvalidCommands().get(0));
    }
}
