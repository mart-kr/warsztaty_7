package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByExpenseCategory(ExpenseCategory expenseCategory);
    List<Expense> findAllByExpenseCategoryId(Long id);
    List<Expense> findAllByReceiptId(Long id);
    List<Expense> findAllByCreatedByUserIdInAndReceiptDateOfPaymentBetween
            (Collection<Long> userIds, LocalDate begin, LocalDate end);
    void deleteAllByIdIn(Collection<Long> ids);


}
