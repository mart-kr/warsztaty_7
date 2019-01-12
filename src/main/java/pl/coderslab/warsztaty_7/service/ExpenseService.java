package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;

import java.util.List;

public interface ExpenseService {

    List<Expense> findAll();
    List<Expense> findByCategory(ExpenseCategory expenseCategory);
    List<Expense> findByCategoryId(Long id);
    List<Expense> findByReceiptId(Long id);
    Expense findById(Long id);
    Expense create(Expense expense);
    Expense edit(Expense expense);
    void deleteById(Long id);

    //TODO: Do usunięcia/przeniesienia do oddzielnego interfejsu
/*    List<ExpenseCategory> findAllCategories();
    ExpenseCategory findCategoryById(Long id);*/
}
