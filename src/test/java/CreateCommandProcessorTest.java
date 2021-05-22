import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCommandProcessorTest {

    private static final String ID = "12345678";
    private static final String CHECKING = "checking";
    private static final String SAVINGS = "savings";
    private static final String CD = "cd";
    private static final double APR = 0.6;
    private static final double BALANCE = 0;
    private static final double CDBALANCE = 2000;
    private static final double DEPOSIT_AMOUNT = 100;

    CreateCommandProcessor createCommandProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createCommandProcessor = new CreateCommandProcessor(bank);
    }

    @Test
    void create_checking_account_with_correct_id_and_apr_and_balance_of_zero() {
        createCommandProcessor.checkCommandType("Create checking 12345678 0.6");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(APR, bank.getAccounts().get(ID).getAccountApr(), 0.0001);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void create_savings_account_with_correct_id_and_apr_and_balance_of_zero() {
        createCommandProcessor.checkCommandType("Create savings 12345678 0.6");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(APR, bank.getAccounts().get(ID).getAccountApr(), 0.0001);
        assertEquals(BALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }

    @Test
    void create_cd_account_with_correct_id_and_apr_and_balance() {
        createCommandProcessor.checkCommandType("Create cd 12345678 0.6 2000");
        assertEquals(ID, bank.getAccounts().get(ID).getAccountId());
        assertEquals(APR, bank.getAccounts().get(ID).getAccountApr(), 0.0001);
        assertEquals(CDBALANCE, bank.getAccounts().get(ID).getAccountBalance());
    }
}
