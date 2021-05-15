import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandValidatorTest {

    private static final String ID = "12345678";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6332423;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;

    DepositCommandValidator depositCommandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        depositCommandValidator = new DepositCommandValidator(bank);
    }

    @Test
    void valid_deposit_into_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 500");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_into_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 500");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_minimum_into_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_minimum_into_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_maximum_into_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 2500");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_maximum_into_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 1000");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_into_account_with_trailing_whitespace() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 1000        ");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_into_account_case_insensitive() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("DePoSiT 12345678 1000");
        assertTrue(actual);
    }

    @Test
    void deposit_into_account_with_whitespace_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit    12345678    1000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_account_with_no_spaces_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit123456781000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_cd_account_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 1000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_savings_account_with_negative_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 -1000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_checking_account_with_negative_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 -1000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_savings_account_with_more_than_maximum_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 2501");
        assertFalse(actual);
    }

    @Test
    void deposit_into_checking_account_with_more_than_maximum_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 1001");
        assertFalse(actual);
    }

    @Test
    void deposit_into_account_with_missing_deposit_command_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("12345678 1000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_account_with_missing_account_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 1000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_account_with_missing_deposit_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678");
        assertFalse(actual);
    }

    @Test
    void deposit_into_account_with_typo_in_deposit_command_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Depsit 12345678 1000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_account_that_does_not_exist_is_invalid() {
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 1000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_account_with_alphanumeric_in_account_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit abcd5678 1000");
        assertFalse(actual);
    }

    @Test
    void deposit_into_account_with_alphanumeric_in_deposit_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = depositCommandValidator.checkCommandType("Deposit 12345678 10ef0");
        assertFalse(actual);
    }
}
