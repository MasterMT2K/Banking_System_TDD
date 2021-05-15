import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandStorageTest {
    CommandStorage commandStorage;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandStorage = new CommandStorage();
    }

    @Test
    void command_storage_is_empty_at_start() {
        assertTrue(commandStorage.getInvalidCommands().isEmpty());
    }

    @Test
    void store_invalid_create_checking_command() {
        commandStorage.addInvalidCommandToStorage("Create checking 123456789 0");
        assertEquals("Create checking 123456789 0", commandStorage.getInvalidCommands().get(0));
    }

    @Test
    void store_two_invalid_create_checking_command() {
        commandStorage.addInvalidCommandToStorage("Create checking 123456789 0");
        commandStorage.addInvalidCommandToStorage("Create sdfs 123456789 0");
        assertEquals("Create checking 123456789 0", commandStorage.getInvalidCommands().get(0));
        assertEquals("Create sdfs 123456789 0", commandStorage.getInvalidCommands().get(1));
    }

    @Test
    void store_many_invalid_create_checking_command() {
        commandStorage.addInvalidCommandToStorage("Create checking 123456789 0");
        commandStorage.addInvalidCommandToStorage("Create sdfs 123456789 0");
        commandStorage.addInvalidCommandToStorage("Deposit checking 12345678 10000000");
        commandStorage.addInvalidCommandToStorage("Depos sdfs 123456789 0");
        assertEquals("Create checking 123456789 0", commandStorage.getInvalidCommands().get(0));
        assertEquals("Create sdfs 123456789 0", commandStorage.getInvalidCommands().get(1));
        assertEquals("Deposit checking 12345678 10000000", commandStorage.getInvalidCommands().get(2));
        assertEquals("Depos sdfs 123456789 0", commandStorage.getInvalidCommands().get(3));
    }
}
