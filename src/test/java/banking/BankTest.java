package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {

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
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void bank_has_no_accounts_initially() {
        Bank bank = new Bank();
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void add_checking_account_to_bank() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
    }

    @Test
    void add_savings_account_to_bank() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
    }

    @Test
    void add_cd_account_to_bank() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
    }

    @Test
    void add_two_accounts_to_bank() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        assertEquals(ID2, bank.getAccounts().get(ID2).getAccountId());
    }

    @Test
    void add_multiple_accounts_to_bank() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        bank.addAccount(ID3, APR, BALANCE, CD);
        assertEquals(ID3, bank.getAccounts().get(ID3).getAccountId());
    }

    @Test
    void deposit_into_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT);
        assertEquals(BALANCE + DEPOSIT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void deposit_twice_into_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT);
        bank.depositToAccount(ID, DEPOSIT);
        assertEquals(BALANCE + DEPOSIT + DEPOSIT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void deposit_into_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT);
        assertEquals(BALANCE + DEPOSIT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void deposit_twice_into_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT);
        bank.depositToAccount(ID, DEPOSIT);
        assertEquals(BALANCE + DEPOSIT + DEPOSIT, bank.getAccounts().get(ID).getAccountBalance());
    }

    //should not test deposit into cd account

    @Test
    void withdrawal_from_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.withdrawalFromAccount(ID, WITHDRAWAL);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdrawal_twice_from_checking_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.withdrawalFromAccount(ID, WITHDRAWAL);
        bank.withdrawalFromAccount(ID, WITHDRAWAL);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdrawal_from_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.withdrawalFromAccount(ID, WITHDRAWAL);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdrawal_twice_from_savings_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.withdrawalFromAccount(ID, WITHDRAWAL);
        bank.withdrawalFromAccount(ID, WITHDRAWAL);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdrawal_from_cd_account() {
        bank.addAccount(ID, APR, BALANCE, CD);
        bank.withdrawalFromAccount(ID, WITHDRAWAL);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdrawal_twice_from_cd_account() {
        bank.addAccount(ID, APR, BALANCE, CD);
        bank.withdrawalFromAccount(ID, WITHDRAWAL);
        bank.withdrawalFromAccount(ID, WITHDRAWAL);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }
}
