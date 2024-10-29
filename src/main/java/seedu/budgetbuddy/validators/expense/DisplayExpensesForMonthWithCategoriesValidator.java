package seedu.budgetbuddy.validators.expense;

import seedu.budgetbuddy.commands.Command;
import seedu.budgetbuddy.commands.IncorrectCommand;
import seedu.budgetbuddy.commands.expense.DisplayExpensesForMonthWithCategoriesGraphCommand;
import seedu.budgetbuddy.util.LoggerSetup;
import static seedu.budgetbuddy.validators.DateValidator.validateYearMonth;

import java.time.YearMonth;
import java.util.logging.Logger;

/**
 * Validates and processes commands related to displaying expenses for a specific month with categories.
 */
public class DisplayExpensesForMonthWithCategoriesValidator {
    private static final Logger LOGGER = LoggerSetup.getLogger();

    /**
     * Processes the given command to create a command object for displaying expenses by month and category.
     *
     * @param command The command string input by the user.
     * @return A Command object corresponding to the user's input, or an IncorrectCommand if the input is invalid.
     */
    public static Command processCommand(String command){
        YearMonth yearMonth = null;

        String trimmedCommand = command.substring("display expenses with categories".length()).trim();
        if (trimmedCommand.isEmpty()) {
            return new IncorrectCommand("Please provide a valid month");
        }

        if (trimmedCommand.startsWith("m/")) {
            yearMonth = validateYearMonth(trimmedCommand);
        }else{
            return new IncorrectCommand("Unknown command: " + trimmedCommand + " Please use m/MM/YYYY");
        }

        if (yearMonth == null) {
            return new IncorrectCommand("Invalid Date");
        }else{
            return new DisplayExpensesForMonthWithCategoriesGraphCommand(yearMonth);
        }
    }
}
