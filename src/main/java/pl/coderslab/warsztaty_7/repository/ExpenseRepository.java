package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    public List<Expense> findAllByExpenseCategory(ExpenseCategory expenseCategory);
    public List<Expense> findAllByExpenseCategoryId(Long id);
    public List<Expense> findAllByReceiptId(Long id);

}
