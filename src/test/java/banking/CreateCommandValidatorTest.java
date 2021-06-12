package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandValidatorTest {

    private static final String ID = "12345678";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6332423;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;

    CreateCommandValidator createCommandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createCommandValidator = new CreateCommandValidator(bank);
    }

    @Test
    void valid_create_savings_command() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void valid_create_checking_command() {
        boolean actual = createCommandValidator.checkCommandType("Create checking 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_command() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 0.6 2000 ");
        assertTrue(actual);
    }

    @Test
    void valid_create_account_with_trailing_whitespace() {
        boolean actual = createCommandValidator.checkCommandType("Create checking 12345678 0.6       ");
        assertTrue(actual);
    }

    @Test
    void valid_create_checking_with_zero_apr() {
        boolean actual = createCommandValidator.checkCommandType("Create checking 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_create_checking_with_ten_apr() {
        boolean actual = createCommandValidator.checkCommandType("Create checking 12345678 10.0");
        assertTrue(actual);
    }

    @Test
    void valid_create_savings_with_zero_apr() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_create_savings_with_ten_apr() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12345678 10.0");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_with_zero_apr() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 0 2000");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_with_ten_apr() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 10.0 2000");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_with_min_account_balance() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 0 1000");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_with_max_account_balance() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 10.0 10000");
        assertTrue(actual);
    }

    @Test
    void valid_create_savings_case_insensitive() {
        boolean actual = createCommandValidator.checkCommandType("CrEATe sAVings 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void valid_create_checking_case_insensitive() {
        boolean actual = createCommandValidator.checkCommandType("CrEATe CHEcking 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void valid_create_cd_case_insensitive() {
        boolean actual = createCommandValidator.checkCommandType("CreAtE cD 12345678 10.0 10000");
        assertTrue(actual);
    }

    @Test
    void duplicate_account_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 0.6 2000");
        assertFalse(actual);
    }

    @Test
    void create_savings_no_argument_spacing_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Createsavings123456780.6");
        assertFalse(actual);
    }

    @Test
    void create_checking_no_argument_spacing_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Createchecking123456780.6");
        assertFalse(actual);
    }

    @Test
    void create_cd_no_argument_spacing_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Createcd123456780.62000");
        assertFalse(actual);
    }

    @Test
    void create_savings_with_negative_apr_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12345678 -0.6");
        assertFalse(actual);
    }

    @Test
    void create_checking_with_negative_apr_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create checking 12345678 -0.6");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_negative_apr_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 -0.6 2000");
        assertFalse(actual);
    }

    @Test
    void create_savings_with_greater_than_maximum_apr_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12345678 10.1");
        assertFalse(actual);
    }

    @Test
    void create_checking_with_reater_than_maximum_apr_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create checking 12345678 10.1");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_reater_than_maximum_apr_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 10.1 2000");
        assertFalse(actual);
    }

    @Test
    void create_savings_with_incorrect_length_id_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12378 0.6");
        assertFalse(actual);
    }

    @Test
    void create_checking_with_incorrect_length_id_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create checking 1234567 0.6");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_incorrect_length_id_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678910 0.6 2000");
        assertFalse(actual);
    }

    @Test
    void create_savings_with_spaces_between_arguments_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create    savings 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_checking_with_spaces_between_arguments_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create  checking    12345678       0.6");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_spaces_between_arguments_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd       12345678    0.6");
        assertFalse(actual);
    }

    @Test
    void create_savings_with_alphanumeric_id_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12f3j435 0.6");
        assertFalse(actual);
    }

    @Test
    void create_checking_with_alphanumeric_id_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create checking abcd5678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_alphanumeric_id_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 1A3R5FF8 0.6 2000");
        assertFalse(actual);
    }

    @Test
    void create_account_with_incorrect_account_type_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create happiness 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_negative_amount_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 0.6 -212");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_less_than_min_amount_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 0.6 0");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_greater_than_max_amount_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 0.6 2233230");
        assertFalse(actual);
    }

    @Test
    void create_savings_with_incorrect_APR_symbol_added_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12345678 0.6%");
        assertFalse(actual);
    }

    @Test
    void create_checking_with_incorrect_APR_symbol_added_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create checking 12345678 0.6%");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_incorrect_APR_symbol_added_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 0.6% 2000");
        assertFalse(actual);
    }

    @Test
    void create_account_with_arguments_out_of_order_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create 0.6 cd 12345678 2000");
        assertFalse(actual);
    }

    @Test
    void create_account_with_missing_create_command_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("checking 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_account_with_missing_account_type_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_account_with_missing_id_number_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 0.6 2000");
        assertFalse(actual);
    }

    @Test
    void create_account_with_missing_account_apr_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12345678");
        assertFalse(actual);
    }

    @Test
    void create_cd_account_with_missing_account_balance_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create cd 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_account_with_typo_in_create_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Creame savings 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_account_with_extra_arguments_is_invalid() {
        boolean actual = createCommandValidator.checkCommandType("Create savings 12345678 0.6 0.0");
        assertFalse(actual);
    }
}
