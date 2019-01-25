package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExpenseService {

    List<Expense> findAll();

    List<Expense> findByCategory(ExpenseCategory expenseCategory);

    List<Expense> findByCategoryId(Long id);

    List<Expense> findByReceiptId(Long id);

    Expense findById(Long id);

    Expense create(Expense expense);

    Expense edit(Expense expense);

    void deleteById(Long id);

    List<Expense> findExpensesInThisMonthForBudget(Budget budget);

    Map<String, BigDecimal> sortedSumOfExpensesInCategory(List<Expense> expenses);

    Map<String, Integer> sumOfSortedExpensesToPercentage(Map<String, BigDecimal> sortedExpenses);

}
