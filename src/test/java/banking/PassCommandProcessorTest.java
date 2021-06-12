package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PassCommandProcessorTest {

    private static final String ID = "12345678";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;
    private static final double SHORT_DEPOSIT_AMOUNT = 50;
    private static final double DEPOSIT_AMOUNT = 500;
    private static final double INTEREST_ADDED = 0.25;
    private static final double CD_INTEREST_ADDED = 4.003;
    private static final double MINIMUM_BALANCE_FEE = 25;

    PassCommandProcessor passCommandProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        passCommandProcessor = new PassCommandProcessor(bank);
    }

    @Test
    void valid_checking_account_with_zero_balance_is_removed() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        passCommandProcessor.checkCommandType("Pass 1");
        assertNull(bank.getAccounts().get(ID));
    }

    @Test
    void valid_savings_account_with_zero_balance_is_removed() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        passCommandProcessor.checkCommandType("Pass 1");
        assertNull(bank.getAccounts().get(ID));
    }

    @Test
    void valid_cd_account_with_zero_balance_is_removed() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        bank.withdrawalFromAccount(ID, CDBALANCE);
        passCommandProcessor.checkCommandType("Pass 1");
        assertNull(bank.getAccounts().get(ID));
    }

    @Test
    void valid_checking_account_receives_minimum_balance_fee() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, SHORT_DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(BALANCE + SHORT_DEPOSIT_AMOUNT - MINIMUM_BALANCE_FEE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_savings_account_receives_minimum_balance_fee() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, SHORT_DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(BALANCE + SHORT_DEPOSIT_AMOUNT - MINIMUM_BALANCE_FEE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_checking_account_has_correct_balance_after_one_month() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(BALANCE + DEPOSIT_AMOUNT + INTEREST_ADDED, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_savings_account_has_correct_balance_after_one_month() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(BALANCE + DEPOSIT_AMOUNT + INTEREST_ADDED, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_cd_account_has_correct_balance_after_one_month() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(CDBALANCE + CD_INTEREST_ADDED, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_checking_account_has_correct_balance_after_two_months() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 2");
        assertEquals(BALANCE + DEPOSIT_AMOUNT + INTEREST_ADDED + 0.250125, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_savings_account_has_correct_balance_after_two_months() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 2");
        assertEquals(BALANCE + DEPOSIT_AMOUNT + INTEREST_ADDED + 0.250125, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_cd_account_has_correct_balance_after_two_months() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 2");
        assertEquals(CDBALANCE + CD_INTEREST_ADDED + 4.011, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_checking_account_has_correct_balance_after_many_months() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 10");
        assertEquals(BALANCE + DEPOSIT_AMOUNT + 2.505, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_savings_account_has_correct_balance_after_many_months() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 10");
        assertEquals(BALANCE + DEPOSIT_AMOUNT + 2.505, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_cd_account_has_correct_balance_after_many_months() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 10");
        assertEquals(CDBALANCE + 40.392, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_checking_account_has_correct_balance_after_maximum_months() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 60");
        assertEquals(BALANCE + DEPOSIT_AMOUNT + 15.223, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_savings_account_has_correct_balance_after_maximum_months() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 60");
        assertEquals(BALANCE + DEPOSIT_AMOUNT + 15.223, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_cd_account_has_correct_balance_after_maximum_months() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 60");
        assertEquals(CDBALANCE + 254.926, bank.getAccounts().get(ID).getAccountBalance(), 0.001);
    }

    @Test
    void valid_checking_account_receives_minimum_balance_fee_until_zero_balance() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, SHORT_DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 2");
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_savings_account_receives_minimum_balance_fee_until_zero_balance() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, SHORT_DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 2");
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_checking_account_receives_minimum_balance_fee_and_is_removed() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, SHORT_DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 3");
        assertNull(bank.getAccounts().get(ID));
    }

    @Test
    void valid_savings_account_receives_minimum_balance_fee_and_is_removed() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, SHORT_DEPOSIT_AMOUNT);
        passCommandProcessor.checkCommandType("Pass 3");
        assertNull(bank.getAccounts().get(ID));
    }

    @Test
    void valid_checking_account_receives_minimum_balance_fee_at_balance_of_99() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, 99);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(99 - MINIMUM_BALANCE_FEE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_savings_account_receives_minimum_balance_fee_at_balance_of_99() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, 99);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(99 - MINIMUM_BALANCE_FEE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_checking_account_calculates_APR_at_balance_of_101() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, 101);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(101.0505, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_savings_account_calculates_APR_at_balance_of_101() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, 101);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(101.0505, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void valid_checking_account_calculates_correct_months_passed() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, 101);
        passCommandProcessor.checkCommandType("Pass 1");
        assertEquals(1, bank.getAccounts().get(ID).getMonthsPassed());
    }

    @Test
    void valid_savings_account_calculates_correct_months_passed() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, 101);
        passCommandProcessor.checkCommandType("Pass 20");
        assertEquals(20, bank.getAccounts().get(ID).getMonthsPassed());
    }

    @Test
    void valid_cd_account_calculates_correct_months_passed() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        passCommandProcessor.checkCommandType("Pass 60");
        assertEquals(60, bank.getAccounts().get(ID).getMonthsPassed());
    }
}
