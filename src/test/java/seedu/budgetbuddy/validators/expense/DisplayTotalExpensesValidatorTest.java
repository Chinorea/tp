package seedu.budgetbuddy.validators.expense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.commands.Command;
import seedu.budgetbuddy.commands.IncorrectCommand;
import seedu.budgetbuddy.commands.expense.DisplayTotalExpensesCommand;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayTotalExpensesValidatorTest {

    private String baseCommand;

    @BeforeEach
    void setUp() {
        // Set up the base command or any common variables used in tests
        baseCommand = "display monthly expenses";
    }

    @Test
    void testValidYearInput() {
        Command command = DisplayTotalExpensesValidator.processCommand(baseCommand + " y/2023");
        assertTrue(command instanceof DisplayTotalExpensesCommand);
        assertEquals("Displaying expense graph for 2023", ((DisplayTotalExpensesCommand) command).
                getFeedbackToUser());

    }

    @Test
    void testMissingYearInput() {
        Command command = DisplayTotalExpensesValidator.processCommand(baseCommand);
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Please provide a year.", ((IncorrectCommand) command).getFeedbackToUser());
    }

    @Test
    void testInvalidYearFormat() {
        Command command = DisplayTotalExpensesValidator.processCommand(baseCommand + " y/abc");
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Invalid or missing year format.", ((IncorrectCommand) command).getFeedbackToUser());
    }

    @Test
    void testInvalidCommandFormat() {
        Command command = DisplayTotalExpensesValidator.processCommand(baseCommand + " y2023");
        assertTrue(command instanceof IncorrectCommand);
        assertEquals("Unknown command 'y2023'. Expected format: 'y/<year>'", ((IncorrectCommand) command).
                getFeedbackToUser());
    }
}
