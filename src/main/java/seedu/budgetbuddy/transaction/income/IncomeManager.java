package seedu.budgetbuddy.transaction.income;

import seedu.budgetbuddy.Ui;
import seedu.budgetbuddy.util.LoggerSetup;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages a collection of income transactions.
 * Provides functionality to add, delete, and list incomes.
 */
public class IncomeManager {
    private static final Logger LOGGER = LoggerSetup.getLogger();
    private static int numberOfIncomes = 0;
    private static ArrayList<Income> incomes = new ArrayList<>();

    /**
     * Construct a IncomeManager of array content incomes
     *
     * @param incomes is the content to be instantiated
     */
    public IncomeManager(ArrayList<Income> incomes, int numberOfIncomes) {
        assert numberOfIncomes >= 0 : "numberOfIncomes should be greater than 0";
        IncomeManager.incomes = incomes;
        IncomeManager.numberOfIncomes = numberOfIncomes;
    }

    /**
     * Adds a new income to the manager.
     *
     * @param income The income to be added.
     */
    public static void addIncome(Income income) {
        incomes.add(income);
        numberOfIncomes++;
        String result = "The following income transaction has been added:\n"
                + income + '\n'
                + "You have " + numberOfIncomes + " income transaction(s) in total.";
        Ui.displayToUser(result);
    }

    /**
     * Deletes an income from the manager at the specified index.
     *
     * @param index The index of the income to be deleted.
     */
    public static void deleteIncome(int index) {
        numberOfIncomes--;
        String result = "The following income transaction has been deleted:\n"
                + incomes.get(index) + '\n'
                + "You have " + numberOfIncomes + " income transaction(s) in total.";
        incomes.remove(index);
        Ui.displayToUser(result);
    }

    /**
     * Returns the current number of incomes.
     *
     * @return The total number of incomes.
     */
    public static int getNumberOfIncomes() {
        return numberOfIncomes;
    }

    /**
     * Lists all the incomes managed by the manager.
     * Displays each income with its corresponding number.
     */
    public static void listIncomes() {
        String result = "";
        int counter = 1;
        for (Income income : incomes) {
            result += counter + ". " + income.toString() + "\n";
            counter++;
        }
        LOGGER.log(Level.INFO, "Listing {0} expenses", numberOfIncomes);
        Ui.displayToUser(result);
    }

    /**
     * Display all income that matches with month field that are managed by the manager.
     * Displays each income with its corresponding number.
     * @param month
     */
    public static void displayIncomeWithMonth(YearMonth month) {
        String result = "";
        int counter = 1;
        for (Income income : incomes) {
            if(month.equals(getYearMonthFromDate(income.getDate()))) {
                result += counter + ". " + income.toString() + "\n";
                counter++;
            }
        }
        if(result.equals("")) {
            result = getEmptyDisplayMessage();
        }
        Ui.displayToUser(result);
    }

    /**
     * Extract YearMonth value from date
     * @param date
     * @return
     */
    public static YearMonth getYearMonthFromDate(LocalDate date) {
        return YearMonth.from(date);
    }

    /**
     * Generates a custom empty Display income message
     * @return custom display income message
     */
    public static String getEmptyDisplayMessage() {
        return "No income entry with given month found, try again with a different month.";
    }

    /**
     * A get-function to obtain the information in the current Income List.
     *
     * @return return the income ArrayList
     */
    public static ArrayList<Income> getIncomes() {
        return incomes;
    }
}
