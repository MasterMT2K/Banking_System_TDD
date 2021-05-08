import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {

    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final String ID3 = "32423454";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6332423;
    private static final double BALANCE = 0;
    private static final double DEPOSIT = 500;
    private static final double WITHDRAWAL = 250;
    private static final double CDBALANCE = 2000;
    private static final double LARGEWITHDRAWAL = 2500;

    CommandValidator commandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void valid_create_command() {
        boolean actual = commandValidator.checkCommandType("Create savings 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void duplicate_account_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = commandValidator.checkCommandType("Create checking 12345678 0.6");
        assertFalse(actual);
    }
}
