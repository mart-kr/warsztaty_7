package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.ExpenseCategory;

import java.util.List;

public interface ExpenseCategoryService {

    List<ExpenseCategory> findAll();
    List<ExpenseCategory> findAllForBudgetId(Long id);
    ExpenseCategory findById(Long id);
    ExpenseCategory create(ExpenseCategory expenseCategory);
    ExpenseCategory edit(ExpenseCategory expenseCategory);
    void deleteById(Long id);
}
