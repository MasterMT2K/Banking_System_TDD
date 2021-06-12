package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawalCommandValidatorTest {

    private static final String ID = "12345678";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6332423;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;

    WithdrawalCommandValidator withdrawalCommandValidator;
    PassCommandProcessor passCommandProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        withdrawalCommandValidator = new WithdrawalCommandValidator(bank);
        passCommandProcessor = new PassCommandProcessor(bank);
    }

    @Test
    void valid_withdrawal_from_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 300");
        assertTrue(actual);
    }

    @Test
    void valid_withdrawal_from_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 300");
        assertTrue(actual);
    }

    @Test
    void valid_withdrawal_from_cd_account() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 3000");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_minimum_amount_from_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_minimum_amount_from_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_minimum_amount_from_cd_account() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 2052");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_maximum_amount_from_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 400");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_maximum_amount_from_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 1000");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_maximum_amount_from_cd_account() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 999999");
        assertTrue(actual);
    }

    @Test
    void valid_withdrawal_amount_from_checking_account_with_trailing_whitespace() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 100      ");
        assertTrue(actual);
    }

    @Test
    void valid_withdrawal_amount_from_savings_account_with_trailing_whitespace() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 200       ");
        assertTrue(actual);
    }

    @Test
    void valid_withdrawal_amount_from_cd_account_with_trailing_whitespace() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 3000     ");
        assertTrue(actual);
    }

    @Test
    void valid_withdrawal_from_checking_account_is_case_insensitive() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("WiThDraW 12345678 300");
        assertTrue(actual);
    }

    @Test
    void valid_withdrawal_from_savings_account_is_case_insensitive() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("WITHdraw 12345678 300");
        assertTrue(actual);
    }

    @Test
    void valid_withdrawal_from_cd_account_is_case_insensitive() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("wiThDRAw 12345678 3000");
        assertTrue(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_whitespace_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw    12345678      300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_whitespace_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw         12345678 300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_whitespace_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678        3000");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_negative_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 -300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_negative_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 -300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_negative_amount_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 -3000");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_less_than_minimum_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 -1");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_less_than_minimum_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 -1");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_less_than_minimum_amount_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 1000");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_greater_than_maximum_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 401");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_greater_than_maximum_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 1001");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_more_than_once_in_the_first_month_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.withdrawalFromAccount(ID, 100);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 100");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_more_than_once_in_the_next_month_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, 500);
        passCommandProcessor.checkCommandType("Pass 1");
        bank.withdrawalFromAccount(ID, 100);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 100");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_more_than_once_in_the_same_month_after_passing_many_months_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, 500);
        passCommandProcessor.checkCommandType("Pass 10");
        bank.withdrawalFromAccount(ID, 100);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 100");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_less_than_twelve_months_passed_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 11");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 1000");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_no_space_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw12345678300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_no_space_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw12345678300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_no_space_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw123456783000");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_typo_in_withdraw_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdrw 12345678 300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_typo_in_withdraw_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdefw 12345678 300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_typo_in_withdraw_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Witaw 12345678 3000");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_invalid_account_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345673 300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_invalid_account_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12344321 300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_invalid_account_id_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12341234 3000");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_alphanumeric_in_account_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345d73 300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_alphanumeric_in_account_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 1234cw21 300");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_alphanumeric_in_account_id_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 1c3412a4 3000");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_alphanumeric_in_withdrawal_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 3a0");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_alphanumeric_in_withdrawal_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 3cc");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_alphanumeric_in_withdrawal_amount_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 3e00");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_checking_account_with_extra_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 100 withdraw");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_savings_account_with_extra_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 100 alpha");
        assertFalse(actual);
    }

    @Test
    void withdrawal_from_cd_account_with_extra_arguments_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 12");
        boolean actual = withdrawalCommandValidator.checkCommandType("Withdraw 12345678 3000 0.4");
        assertFalse(actual);
    }
}
