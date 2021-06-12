package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawalCommandProcessorTest {

    private static final String ID = "12345678";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6332423;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;
    private static final double DEPOSIT_AMOUNT = 500;
    private static final double WITHDRAWAL_AMOUNT = 100;

    WithdrawalCommandProcessor withdrawalCommandProcessor;
    PassCommandProcessor passCommandProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        withdrawalCommandProcessor = new WithdrawalCommandProcessor(bank);
    }

    @Test
    void withdraw_from_checking_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        withdrawalCommandProcessor.checkCommandType("Withdraw 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_from_savings_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        withdrawalCommandProcessor.checkCommandType("Withdraw 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_from_cd_account_with_correct_balance() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        bank.passTime("Pass 12");
        withdrawalCommandProcessor.checkCommandType("Withdraw 12345678 3000");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(0, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_twice_from_checking_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        withdrawalCommandProcessor.checkCommandType("Withdraw 12345678 100");
        withdrawalCommandProcessor.checkCommandType("Withdraw 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT - WITHDRAWAL_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_twice_from_savings_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        withdrawalCommandProcessor.checkCommandType("Withdraw 12345678 100");
        withdrawalCommandProcessor.checkCommandType("Withdraw 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT - WITHDRAWAL_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void withdraw_twice_from_cd_account_with_correct_balance() {
        bank.addAccount(ID, APR, CDBALANCE, CD);
        bank.passTime("Pass 12");
        withdrawalCommandProcessor.checkCommandType("Withdraw 12345678 3000");
        withdrawalCommandProcessor.checkCommandType("Withdraw 12345678 3000");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(0, bank.getAccounts().get(ID).getAccountBalance());
    }
}
