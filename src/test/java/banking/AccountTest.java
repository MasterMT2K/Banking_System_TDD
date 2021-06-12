package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
