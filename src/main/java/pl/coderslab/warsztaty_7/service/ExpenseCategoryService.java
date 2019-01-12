package pl.coderslab.warsztaty_7.service;

import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;

import java.util.List;

public interface ExpenseCategoryService {

    List<ExpenseCategory> findAll();
    ExpenseCategory findById(Long id);
    ExpenseCategory create(ExpenseCategory expenseCategory);
    ExpenseCategory edit(ExpenseCategory expenseCategory);
    void deleteById(Long id);
}
