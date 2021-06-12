package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private static final String ID = "12345678";
    private static final double APR = 0.6332423;
    private static final double BALANCE = 0;
    private static final double DEPOSIT = 500;
    private static final double WITHDRAWAL = 250;
    private static final double CDBALANCE = 2000;
    private static final double LARGEWITHDRAWAL = 2500;
    CheckingAccount checkingAccount;
    SavingsAccount savingsAccount;
    CDAccount cdAccount;

    @BeforeEach
    void setUp() {
        checkingAccount = new CheckingAccount(ID, APR, BALANCE);
        savingsAccount = new SavingsAccount(ID, APR, BALANCE);
        cdAccount = new CDAccount(ID, APR, CDBALANCE);
    }

    @Test
    void checking_account_has_id() {
        assertEquals(ID, checkingAccount.getAccountId());
    }

    @Test
    void savings_account_has_id() {
        assertEquals(ID, savingsAccount.getAccountId());
    }

    @Test
    void cd_account_has_id() {
        assertEquals(ID, cdAccount.getAccountId());
    }

    @Test
    void checking_account_has_correct_type() {
        assertEquals("Checking", checkingAccount.getAccountType());
    }

    @Test
    void savings_account_has_correct_type() {
        assertEquals("Savings", savingsAccount.getAccountType());
    }

    @Test
    void cd_account_has_correct_type() {
        assertEquals("Cd", cdAccount.getAccountType());
    }

    @Test
    void checking_account_has_apr() {
        assertEquals(APR, checkingAccount.getAccountApr());
    }

    @Test
    void savings_account_has_apr() {
        assertEquals(APR, savingsAccount.getAccountApr());
    }

    @Test
    void cd_account_has_apr() {
        assertEquals(APR, cdAccount.getAccountApr());
    }

    @Test
    void checking_account_has_balance_of_zero_initially() {
        assertEquals(BALANCE, checkingAccount.getAccountBalance());
    }

    @Test
    void savings_account_has_balance_of_zero_initially() {
        assertEquals(BALANCE, savingsAccount.getAccountBalance());
    }

    @Test
    void cd_account_has_correct_balance_initially() {
        assertEquals(CDBALANCE, cdAccount.getAccountBalance());
    }

    @Test
    void checking_account_has_correct_balance_after_deposit() {
        checkingAccount.deposit(DEPOSIT);
        assertEquals(BALANCE + DEPOSIT, checkingAccount.getAccountBalance());
    }

    @Test
    void savings_account_has_correct_balance_after_deposit() {
        savingsAccount.deposit(DEPOSIT);
        assertEquals(BALANCE + DEPOSIT, savingsAccount.getAccountBalance());
    }

    //cdAccount cannot be deposited into

    @Test
    void checking_account_has_correct_balance_after_withdrawal() {
        checkingAccount.withdrawal(WITHDRAWAL);
        assertEquals(BALANCE, checkingAccount.getAccountBalance());
    }

    @Test
    void savings_account_has_correct_balance_after_withdrawal() {
        savingsAccount.withdrawal(WITHDRAWAL);
        assertEquals(BALANCE, savingsAccount.getAccountBalance());
    }

    @Test
    void cd_account_has_correct_balance_after_withdrawal() {
        cdAccount.withdrawal(CDBALANCE);
        assertEquals(0, cdAccount.getAccountBalance());
    }

    @Test
    void checking_account_balance_cannot_go_below_zero() {
        checkingAccount.withdrawal(WITHDRAWAL);
        assertEquals(0, checkingAccount.getAccountBalance());
    }

    @Test
    void savings_account_balance_cannot_go_below_zero() {
        savingsAccount.withdrawal(WITHDRAWAL);
        assertEquals(0, savingsAccount.getAccountBalance());
    }

    @Test
    void cd_account_balance_cannot_go_below_zero() {
        cdAccount.withdrawal(LARGEWITHDRAWAL);
        assertEquals(0, cdAccount.getAccountBalance());
    }

    @Test
    void checking_account_balance_withdrawal_of_zero_with_balance_of_zero() {
        checkingAccount.withdrawal(0);
        assertEquals(0, checkingAccount.getAccountBalance());
    }

    @Test
    void savings_account_balance_withdrawal_of_zero_with_balance_of_zero() {
        savingsAccount.withdrawal(0);
        assertEquals(0, savingsAccount.getAccountBalance());
    }

    @Test
    void cd_account_balance_withdrawal_of_zero_with_starting_balance() {
        cdAccount.withdrawal(0);
        assertEquals(2000, cdAccount.getAccountBalance());
    }

    @Test
    void savings_account_balance_has_withdrew_this_month() {
        savingsAccount.withdrawal(0);
        assertTrue(savingsAccount.getHasWithdrewThisMonth());
    }

    @Test
    void savings_account_balance_has_not_withdrew_this_month() {
        assertFalse(savingsAccount.getHasWithdrewThisMonth());
    }

    @Test
    void checking_account_balance_withdrawal_of_greater_than_balance_returns_with_balance_of_zero() {
        checkingAccount.withdrawal(100);
        assertEquals(0, checkingAccount.getAccountBalance());
    }

    @Test
    void savings_account_balance_withdrawal_of_greater_than_balance_returns_with_balance_of_zero() {
        savingsAccount.withdrawal(1);
        assertEquals(0, savingsAccount.getAccountBalance());
    }

    @Test
    void cd_account_balance_withdrawal_of_greater_than_balance_returns_with_balance_of_zero() {
        cdAccount.withdrawal(100);
        assertEquals(2000, cdAccount.getAccountBalance());
    }

    @Test
    void valid_cd_account_create_with_minimum_create_bounds() {
        assertTrue(cdAccount.checkCreateBounds(1000));
    }

    @Test
    void valid_cd_account_create_with_maximum_create_bounds_is_invalid() {
        assertTrue(cdAccount.checkCreateBounds(10000));
    }

    @Test
    void valid_cd_account_withdrawal_with_balance() {
        cdAccount.withdrawal(cdAccount.getAccountBalance());
        assertTrue(cdAccount.checkWithdrawalBounds(cdAccount.getAccountBalance()));
    }

    @Test
    void checking_account_negative_withdrawal_is_invalid() {
        assertFalse(checkingAccount.checkWithdrawalBounds(-1));
    }

    @Test
    void savings_account_negative_withdrawal_is_invalid() {
        assertFalse(savingsAccount.checkWithdrawalBounds(-1));
    }

    @Test
    void checking_account_withdrawal_with_less_than_withdrawal_bounds_is_invalid() {
        checkingAccount.withdrawal(-1);
        assertFalse(checkingAccount.checkWithdrawalBounds(-1));
    }

    @Test
    void savings_account_withdrawal_with_less_than_withdrawal_bounds_is_invalid() {
        savingsAccount.withdrawal(-1);
        assertFalse(savingsAccount.checkWithdrawalBounds(-1));
    }

    @Test
    void checking_account_withdrawal_with_greater_than_withdrawal_bounds_is_invalid() {
        checkingAccount.withdrawal(401);
        assertFalse(checkingAccount.checkWithdrawalBounds(401));
    }

    @Test
    void savings_account_withdrawal_with_greater_than_withdrawal_bounds_is_invalid() {
        savingsAccount.withdrawal(1001);
        assertFalse(savingsAccount.checkWithdrawalBounds(1001));
    }

    @Test
    void cd_account_withdrawal_with_less_than_withdrawal_bounds_is_invalid() {
        cdAccount.withdrawal(cdAccount.getAccountBalance() - 1);
        assertFalse(savingsAccount.checkWithdrawalBounds(cdAccount.getAccountBalance() - 1));
    }

    @Test
    void checking_account_create_with_negative_create_bounds_is_invalid() {
        assertFalse(checkingAccount.checkCreateBounds(-1));
    }

    @Test
    void savings_account_create_with_negative_create_bounds_is_invalid() {
        assertFalse(savingsAccount.checkCreateBounds(-1));
    }

    @Test
    void checking_account_create_with_greater_than_create_bounds_is_invalid() {
        assertFalse(checkingAccount.checkCreateBounds(1));
    }

    @Test
    void savings_account_create_with_greater_than_create_bounds_is_invalid() {
        assertFalse(savingsAccount.checkCreateBounds(1));
    }

    @Test
    void valid_cd_account_create_within_create_bounds() {
        assertTrue(cdAccount.checkCreateBounds(2000));
    }

    @Test
    void cd_account_create_with_negative_create_bounds_is_invalid() {
        assertFalse(cdAccount.checkCreateBounds(-1));
    }

    @Test
    void cd_account_create_with_less_than_create_bounds_is_invalid() {
        assertFalse(cdAccount.checkCreateBounds(999));
    }

    @Test
    void cd_account_create_with_greater_than_create_bounds_is_invalid() {
        assertFalse(cdAccount.checkCreateBounds(10001));
    }
}
