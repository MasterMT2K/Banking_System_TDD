package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassCommandValidatorTest {

    private static final String ID = "12345678";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6332423;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;

    PassCommandValidator passCommandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        passCommandValidator = new PassCommandValidator(bank);
    }

    @Test
    void valid_pass_time_command() {
        boolean actual = passCommandValidator.checkCommandType("Pass 10");
        assertTrue(actual);
    }

    @Test
    void valid_pass_time_command_with_minimum_months() {
        boolean actual = passCommandValidator.checkCommandType("Pass 1");
        assertTrue(actual);
    }

    @Test
    void valid_pass_time_command_with_maximum_months() {
        boolean actual = passCommandValidator.checkCommandType("Pass 60");
        assertTrue(actual);
    }

    @Test
    void valid_pass_time_command_is_case_insensitive() {
        boolean actual = passCommandValidator.checkCommandType("PaSS 6");
        assertTrue(actual);
    }

    @Test
    void valid_pass_time_command_with_trailing_spaces() {
        boolean actual = passCommandValidator.checkCommandType("Pass 6    ");
        assertTrue(actual);
    }

    @Test
    void pass_time_with_zero_months_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pass 0");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_greater_than_maximum_months_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pass 61");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_double_value_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pass 6.0");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_non_numeric_value_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pass happiness");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_no_argument_spacing_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pass5");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_typo_in_pass_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pas 5");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_spaces_between_arguments_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pass      5");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_alphanumeric_in_pass_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("P4ss 5");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_unit_of_months_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pass 5months");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_incorrect_number_of_arguments_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pass 5 months");
        assertFalse(actual);
    }

    @Test
    void pass_time_with_negative_amount_of_months_is_invalid() {
        boolean actual = passCommandValidator.checkCommandType("Pass -10");
        assertFalse(actual);
    }
}
