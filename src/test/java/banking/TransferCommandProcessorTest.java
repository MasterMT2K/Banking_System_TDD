package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferCommandProcessorTest {

    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6332423;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;
    private static final double DEPOSIT_AMOUNT = 500;
    private static final double WITHDRAWAL_AMOUNT = 100;
    private static final double TRANSFER_AMOUNT = 100;

    TransferCommandProcessor transferCommandProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        transferCommandProcessor = new TransferCommandProcessor(bank);
    }

    @Test
    void valid_transfer_between_two_checking_accounts() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        transferCommandProcessor.transfer("Transfer 12345678 87654321 100");
        assertEquals(BALANCE + DEPOSIT_AMOUNT - TRANSFER_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
        assertEquals(BALANCE + +TRANSFER_AMOUNT, bank.getAccounts().get(ID2).getAccountBalance());
    }

    @Test
    void valid_transfer_between_two_savings_accounts() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        transferCommandProcessor.transfer("Transfer 12345678 87654321 100");
        assertEquals(BALANCE + DEPOSIT_AMOUNT - TRANSFER_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
        assertEquals(BALANCE + +TRANSFER_AMOUNT, bank.getAccounts().get(ID2).getAccountBalance());
    }

    @Test
    void valid_transfer_from_checking_to_savings_account() {
        bank.addAccount(ID, APR, BALANCE, CHECKING);
        bank.addAccount(ID2, APR, BALANCE, SAVINGS);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        transferCommandProcessor.transfer("Transfer 12345678 87654321 100");
        assertEquals(BALANCE + DEPOSIT_AMOUNT - TRANSFER_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
        assertEquals(BALANCE + +TRANSFER_AMOUNT, bank.getAccounts().get(ID2).getAccountBalance());
    }

    @Test
    void valid_transfer_from_savings_to_checking_account() {
        bank.addAccount(ID, APR, BALANCE, SAVINGS);
        bank.addAccount(ID2, APR, BALANCE, CHECKING);
        bank.depositToAccount(ID, DEPOSIT_AMOUNT);
        transferCommandProcessor.transfer("Transfer 12345678 87654321 100");
        assertEquals(BALANCE + DEPOSIT_AMOUNT - TRANSFER_AMOUNT, bank.getAccounts().get(ID).getAccountBalance());
        assertEquals(BALANCE + +TRANSFER_AMOUNT, bank.getAccounts().get(ID2).getAccountBalance());
    }
}
