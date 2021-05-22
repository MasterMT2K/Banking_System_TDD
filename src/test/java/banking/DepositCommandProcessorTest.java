package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositCommandProcessorTest {

    private static final String ID = "12345678";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;
    private static final double DEPOSIT_AMOUNT = 100;

    DepositCommandProcessor depositCommandProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        depositCommandProcessor = new DepositCommandProcessor(bank);
    }

    @Test
    void deposit_into_checking_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        depositCommandProcessor.checkCommandType("Deposit 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void deposit_into_savings_account_with_correct_balance() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        depositCommandProcessor.checkCommandType("Deposit 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void deposit_command_creates_new_checking_account_with_correct_balance() {
        depositCommandProcessor.checkCommandType("Deposit 12345678 100");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(BALANCE + DEPOSIT_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
    }
}
