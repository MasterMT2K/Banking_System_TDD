package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferCommandValidatorTest {

    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6332423;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;

    TransferCommandValidator transferCommandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        transferCommandValidator = new TransferCommandValidator(bank);
    }

    @Test
    void valid_transfer_between_two_checking_accounts() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 300");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_between_two_savings_accounts() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 300");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_checking_to_savings_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 300");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_checking_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 300");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_checking_account_is_case_insensitive() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("TrAnSFeR 12345678 87654321 300");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_between_two_checking_accounts_with_minimum_deposit() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 0");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_between_two_savings_accounts_with_minimum_deposit() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 0");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_checking_to_savings_account_with_minimum_deposit() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 0");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_checking_account_with_minimum_deposit() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 0");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_between_two_checking_accounts_with_maximum_withdrawal() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 400");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_between_two_savings_accounts_with_maximum_withdrawal() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 1000");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_checking_to_savings_account_with_maximum_withdrawal() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 400");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_checking_account_with_maximum_withdrawal() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 1000");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_checking_to_savings_account_with_maximum_deposit() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 400");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_checking_account_with_maximum_deposit() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 1000");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_twice_from_checking_to_savings_account_with_maximum_deposit() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, 500);
        bank.transfer(ID, ID2, 100);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 400");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_between_two_checking_accounts_with_trailing_whitespace() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 300      ");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_with_deposit_amount_bound() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.validateDepositAccountAmount("Transfer 12345678 87654321 1000");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_with_withdrawal_amount_bound() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.validateWithdrawalAccountAmount("Transfer 12345678 87654321 400");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_with_less_than_max_deposit_amount_bound() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.validateDepositAccountAmount("Transfer 12345678 87654321 999");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_with_less_than_max_withdrawal_amount_bound() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.validateWithdrawalAccountAmount("Transfer 12345678 87654321 399");
        assertTrue(actual);
    }

    @Test
    void transfer_between_two_checking_accounts_with_less_than_minimum_deposit_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 -1");
        assertFalse(actual);
    }

    @Test
    void transfer_between_two_savings_accounts_with_less_than_minimum_deposit_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 -1");
        assertFalse(actual);
    }

    @Test
    void transfer_between_two_checking_accounts_with_greater_than_maximum_withdrawal_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 401");
        assertFalse(actual);
    }

    @Test
    void transfer_between_two_savings_accounts_with_greater_than_maximum_withdrawal_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 1001");
        assertFalse(actual);
    }

    @Test
    void transfer_checking_to_savings_with_greater_than_maximum_withdrawal_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 1001");
        assertFalse(actual);
    }

    @Test
    void transfer_savings_to_checking_with_greater_than_maximum_withdrawal_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 1001");
        assertFalse(actual);
    }

    @Test
    void transfer_between_two_checking_accounts_with_maximum_deposit_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 1000");
        assertFalse(actual);
    }

    @Test
    void transfer_between_two_savings_accounts_with_maximum_deposit_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 2500");
        assertFalse(actual);
    }

    @Test
    void transfer_between_two_checking_accounts_with_negative_deposit_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 -100");
        assertFalse(actual);
    }

    @Test
    void transfer_between_two_savings_accounts_with_negative_deposit_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 -100");
        assertFalse(actual);
    }

    @Test
    void transfer_from_cd_to_savings_account_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 300");
        assertFalse(actual);
    }

    @Test
    void transfer_from_cd_to_checking_account_is_invalid() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 300");
        assertFalse(actual);
    }

    @Test
    void transfer_from_savings_to_cd_account_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, CDBALANCE, CD);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 300");
        assertFalse(actual);
    }

    @Test
    void transfer_from_checking_to_cd_account_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, CDBALANCE, CD);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 300");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_no_spaces_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer1234567887654321100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_extra_spaces_between_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer    12345678   87654321      100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_typo_in_transfer_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Trafer 12345678 87654321 100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_alphanumeric_in_first_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12e45678 87654321 100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_alphanumeric_in_second_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 876ew321 100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_shortened_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 123678 87654321 100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_shortened_second_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 876521 100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_longer_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678910 87654321 100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_longer_second_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 124487654321 100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_letters_in_amount_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 10ef0");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_extra_arguments_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.checkCommandType("Transfer 12345678 87654321 100 amount 0.6");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_greater_than_deposit_amount_bound_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.validateDepositAccountAmount("Transfer 12345678 87654321 1001");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_non_integer_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.validateDepositAccountIDIsInteger("Transfer 12345678 87.34321 401");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_alphanumeric_id_is_invalid() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        boolean actual = transferCommandValidator.validateWithdrawalAccountIDIsInteger("Transfer 12efw678 87654321 401");
        assertFalse(actual);
    }
}
