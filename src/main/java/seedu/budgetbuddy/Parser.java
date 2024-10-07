package seedu.budgetbuddy;

import seedu.budgetbuddy.commands.AddExpenseCommand;
import seedu.budgetbuddy.commands.AddIncomeCommand;
import seedu.budgetbuddy.commands.AddBudgetCommand;
import seedu.budgetbuddy.commands.Command;
import seedu.budgetbuddy.commands.DeductBudgetCommand;
import seedu.budgetbuddy.commands.DeleteExpenseCommand;
import seedu.budgetbuddy.commands.DeleteIncomeCommand;
import seedu.budgetbuddy.commands.ExitCommand;
import seedu.budgetbuddy.commands.HelpCommand;
import seedu.budgetbuddy.commands.IncorrectCommand;
import seedu.budgetbuddy.commands.ListBudgetCommand;
import seedu.budgetbuddy.commands.ListExpenseCommand;
import seedu.budgetbuddy.commands.ListIncomeCommand;
import seedu.budgetbuddy.transaction.budget.Budget;
import seedu.budgetbuddy.transaction.budget.BudgetManager;
import seedu.budgetbuddy.transaction.expense.Category;
import seedu.budgetbuddy.transaction.expense.Expense;
import seedu.budgetbuddy.transaction.expense.ExpenseManager;
import seedu.budgetbuddy.transaction.income.Income;
import seedu.budgetbuddy.transaction.income.IncomeManager;
import seedu.budgetbuddy.validators.AddExpenseValidator;
import seedu.budgetbuddy.validators.AddIncomeValidator;
import seedu.budgetbuddy.validators.AddBudgetValidator;
import seedu.budgetbuddy.validators.DeductBudgetValidator;
import seedu.budgetbuddy.validators.DeleteExpenseValidator;
import seedu.budgetbuddy.validators.DeleteIncomeValidator;
import seedu.budgetbuddy.validators.ListBudgetValidator;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The Parser class is responsible for interpreting user commands.
 * It analyzes the user input, identifies the corresponding command,
 * and returns the appropriate Command object for execution.
 */
public class Parser {
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;
    private BudgetManager budgetManager;

    public Parser(ExpenseManager expenseManager, IncomeManager incomeManager, BudgetManager budgetManager) {
        this.expenseManager = expenseManager;
        this.incomeManager = incomeManager;
        this.budgetManager = budgetManager;
    }

    /**
     * Analyzes the user's input and returns the appropriate {@code Command} object.
     * It checks the input against known commands for expense, income, or exit operations.
     *
     * @param userCommandText The input string provided by the user.
     * @return The corresponding {@code Command} to execute, or an {@code IncorrectCommand}
     *         if the input is invalid.
     */
    public Command parseCommand(String userCommandText) {
        if (AddExpenseCommand.isCommand(userCommandText)) {
            return AddExpenseValidator.processCommand(userCommandText);
        }
        if (DeleteExpenseCommand.isCommand(userCommandText)) {
            return DeleteExpenseValidator.processCommand(userCommandText);
        }
        if (ListExpenseCommand.isCommand(userCommandText)) {
            return new ListExpenseCommand();
        }
        if (AddIncomeCommand.isCommand(userCommandText)) {
            return AddIncomeValidator.processCommand(userCommandText);
        }
        if (DeleteIncomeCommand.isCommand(userCommandText)) {
            return DeleteIncomeValidator.processCommand(userCommandText);
        }
        if (ListIncomeCommand.isCommand(userCommandText)) {
            return new ListIncomeCommand();
        }
        if (AddBudgetCommand.isCommand(userCommandText)) {
            return AddBudgetValidator.processCommand(userCommandText);
        }
        if (DeductBudgetCommand.isCommand(userCommandText)) {
            return DeductBudgetValidator.processCommand(userCommandText);
        }
        if (ListBudgetCommand.isCommand(userCommandText)) {
            return ListBudgetValidator.processCommand(userCommandText);
        }
        if (ExitCommand.isCommand(userCommandText)) {
            return new ExitCommand();
        }
        if (HelpCommand.isCommand(userCommandText)){
            return new HelpCommand();
        }
        return new IncorrectCommand("Invalid input");
    }

    /**
     * Parses a line of input from the file and categorizes it as an expense, income, or budget.
     * Each line is split based on the delimiter " | ", and the resulting parts are used to create
     * the appropriate object (Expense, Income, or Budget).
     *
     * @param input The line of text from the file to be parsed.
     * @param expenses The list of expenses to which new Expense objects will be added.
     * @param incomes The list of incomes to which new Income objects will be added.
     * @param budgets The list of budgets to which new Budget objects will be added.
     */
    public static void parseFile(String input, ArrayList<Expense> expenses, ArrayList<Income> incomes,
            ArrayList<Budget> budgets) {

        String[] parts = input.split(" \\| ");
        String type = parts[0]; // Determines if it's expense, income, or budget

        switch (type.toLowerCase()) {
        case "expense": {
            String description = parts[1];
            double amount = Double.parseDouble(parts[2]);
            LocalDate date = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("d/M/yyyy"));
            Category category = Category.valueOf(parts[4].toUpperCase()); // Ensure category exists for expense

            expenses.add(new Expense(description, amount, date, category));
            break;
        }
        case "income": {
            String description = parts[1];
            double amount = Double.parseDouble(parts[2]);
            LocalDate date = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("d/M/yyyy"));

            incomes.add(new Income(description, amount, date)); // No category needed for income
            break;
        }
        case "budget": {
            double amount = Double.parseDouble(parts[1]); // For budget, only amount is relevant
            YearMonth budgetDate = YearMonth.parse(parts[2], DateTimeFormatter.ofPattern("yyyy-MM"));
            // Adjust date format for YearMonth

            budgets.add(new Budget(amount, budgetDate)); // Only 2 parameters required for budget
            break;
        }
        default:
            System.out.println("Unknown type in file: " + type);
        }
    }
}