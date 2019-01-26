package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.Target;

import java.util.List;

public interface TargetRepository extends JpaRepository<Target, Long> {

    List<Target> findAllByExpenseCategory(ExpenseCategory expenseCategory);

    List<Target> findAllByExpenseCategoryId(Long id);

}
