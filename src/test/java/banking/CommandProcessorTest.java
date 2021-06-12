package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {

    private static final String ID = "12345678";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;
    private static final double DEPOSIT_AMOUNT = 100;
    private static final double WITHDRAWAL_AMOUNT = 100;

    CommandProcessor commandProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void create_checking_account_with_correct_id_and_apr_and_balance_of_zero() {
        commandProcessor.checkCommandType("Create checking 12345678 0.6");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(APR, bank.getAccounts().get(ID).getAccountApr(), 0.0001);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void create_savings_account_with_correct_id_and_apr_and_balance_of_zero() {
        commandProcessor.checkCommandType("Create savings 12345678 0.6");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(APR, bank.getAccounts().get(ID).getAccountApr(), 0.0001);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void create_cd_account_with_correct_id_and_apr_and_balance() {
        commandProcessor.checkCommandType("Create cd 12345678 0.6 2000");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(APR, bank.getAccounts().get(ID).getAccountApr(), 0.0001);
        assertEquals(CDBALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void deposit_into_checking_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        commandProcessor.checkCommandType("Deposit 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void deposit_into_savings_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        commandProcessor.checkCommandType("Deposit 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void deposit_twice_into_a_single_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        commandProcessor.checkCommandType("Deposit 12345678 100");
        commandProcessor.checkCommandType("Deposit 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT + DEPOSIT_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_from_checking_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        commandProcessor.checkCommandType("Withdraw 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_from_savings_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        commandProcessor.checkCommandType("Withdraw 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_from_cd_account_with_correct_balance() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        bank.passTime("Pass 12");
        commandProcessor.checkCommandType("Withdraw 12345678 3000");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(0, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_twice_from_checking_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        commandProcessor.checkCommandType("Withdraw 12345678 100");
        commandProcessor.checkCommandType("Withdraw 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_twice_from_savings_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        commandProcessor.checkCommandType("Withdraw 12345678 100");
        commandProcessor.checkCommandType("Withdraw 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_twice_from_cd_account_with_correct_balance() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        bank.passTime("Pass 12");
        commandProcessor.checkCommandType("Withdraw 12345678 3000");
        commandProcessor.checkCommandType("Withdraw 12345678 3000");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(0, bank.getAccounts().get(ID).getAccountBalance());
    }
}

