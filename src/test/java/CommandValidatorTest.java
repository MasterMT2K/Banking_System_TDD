import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void valid_create_savings_command() {
        boolean actual = commandValidator.checkCommandType("Create savings 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void valid_create_checking_command() {
        boolean actual = commandValidator.checkCommandType("Create checking 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_command() {
        boolean actual = commandValidator.checkCommandType("Create cd 12345678 0.6 2000 ");
        assertTrue(actual);
    }

    @Test
    void valid_create_account_with_trailing_whitespace() {
        boolean actual = commandValidator.checkCommandType("Create checking 12345678 0.6       ");
        assertTrue(actual);
    }

    @Test
    void valid_create_checking_with_zero_apr() {
        boolean actual = commandValidator.checkCommandType("Create checking 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_create_checking_with_ten_apr() {
        boolean actual = commandValidator.checkCommandType("Create checking 12345678 10.0");
        assertTrue(actual);
    }

    @Test
    void valid_create_savings_with_zero_apr() {
        boolean actual = commandValidator.checkCommandType("Create savings 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_create_savings_with_ten_apr() {
        boolean actual = commandValidator.checkCommandType("Create savings 12345678 10.0");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_with_zero_apr() {
        boolean actual = commandValidator.checkCommandType("Create cd 12345678 0 2000");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_with_ten_apr() {
        boolean actual = commandValidator.checkCommandType("Create cd 12345678 10.0 2000");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_with_min_account_balance() {
        boolean actual = commandValidator.checkCommandType("Create cd 12345678 0 1000");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_with_max_account_balance() {
        boolean actual = commandValidator.checkCommandType("Create cd 12345678 10.0 10000");
        assertTrue(actual);
    }

    @Test
    void valid_create_savings_case_sensitive() {
        boolean actual = commandValidator.checkCommandType("CrEATe sAVings 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void valid_create_checking_case_sensitive() {
        boolean actual = commandValidator.checkCommandType("CrEATe CHEcking 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_case_sensitive() {
        boolean actual = commandValidator.checkCommandType("CreAtE cD 12345678 10.0 10000");
        assertTrue(actual);
    }
}
