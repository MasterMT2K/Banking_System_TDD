package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {

    MasterControl masterControl;
    List<String> input;

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(bank, new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage());
    }

    @Test
    void typo_in_create_command_is_invalid() {
        input.add("creat checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("depositt 12345678 100", actual.get(1));
    }

    @Test
    void invalid_to_create_accounts_with_same_ID() {
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
        assertEquals("create checking 12345678 1.0", actual.get(1));
    }

    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

    @Test
    void input_output_scenario_without_pass_time() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(8, actual.size());
        assertEquals("Savings 12345678 1000.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Checking 98765432 0.00 0.01", actual.get(3));
        assertEquals("Deposit 98765432 300", actual.get(4));
        assertEquals("Transfer 98765432 12345678 300", actual.get(5));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(6));
        assertEquals("Deposit 12345678 5000", actual.get(7));
    }

    @Test
    void input_output_scenario_with_longer_pass_time() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 10");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1005.01 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

    @Test
    void input_output_scenario_with_max_pass_time() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 60");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1030.44 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

    @Test
    void input_output_accounts_with_some_pass_time() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Create cd 23456789 1.2 2000");
        input.add("Pass 60");
        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Savings 12345678 721.31 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Checking 98765432 300.15 0.01", actual.get(2));
        assertEquals("Deposit 98765432 300", actual.get(3));
        assertEquals("Cd 23456789 2542.19 1.20", actual.get(4));
        assertEquals("Deposit 12345678 5000", actual.get(5));
    }

    @Test
    void input_output_with_withdrawal_from_cd() {
        input.add("Create cd 12345678 1.2 2000");
        input.add("Pass 12");
        input.add("Withdraw 12345678 3000");
        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 12345678 0.00 1.20", actual.get(0));
        assertEquals("Withdraw 12345678 3000", actual.get(1));
    }

    @Test
    void input_output_with_transfer_in_both_account_histories() {
        input.add("Create checking 12345678 0.6");
        input.add("Create savings 87651234 0.6");
        input.add("Deposit 12345678 1000");
        input.add("Transfer 12345678 87651234 300");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Checking 12345678 700.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 1000", actual.get(1));
        assertEquals("Transfer 12345678 87651234 300", actual.get(2));
        assertEquals("Savings 87651234 300.00 0.60", actual.get(3));
        assertEquals("Transfer 12345678 87651234 300", actual.get(4));
    }
}
