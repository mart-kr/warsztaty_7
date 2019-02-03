package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.Target;

import java.util.List;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {

    List<Target> findAllByExpenseCategory(ExpenseCategory expenseCategory);
    List<Target> findAllByExpenseCategoryId(Long id);
    List<Target> findAllByBudget(Budget budget);

    @Query("SELECT t FROM Target t WHERE t.budget = :budget and t.startDate < curdate() and t.endDate is null or t.startDate < curdate() and t.endDate > LAST_DAY(curdate())")
    List<Target> findAllInCurrentMonth(@Param(value = "budget") Budget budget);



//    List<Target> findAllByBudgetAndStartDateIsBeforeAndEndDateIsNull
//            (Budget budget, LocalDate begin);
//    List<Target> findAllByBudgetAndStartDateIsGreaterThanEqual
//            (Budget budget, LocalDate begin);
//    List<Target> findAllByBudgetAndStartDateIsGreaterThanEqualAndEndDateBeforeOrEndDateIsNull
//            (Budget budget, LocalDate begin, LocalDate begin2);
//    List<Target> findAllByBudgetAndStartDateIsGreaterThanEqualAndEndDateBeforeOrStartDateIsGreaterThanEqualAndEndDateIsNull
//            (Budget budget, LocalDate begin, LocalDate begin2, LocalDate begin3);
//    List<Target> findAllByBudgetAndStartDateIsLessThanEqualAndEndDateIsNullOrEndDateIsBefore
//            (Budget budget, LocalDate today, LocalDate endDate);
//    List<Target> findAllByBudgetAndStartDateLessThanEqualAndEndDateBeforeOrStartDateLessThanEqualAndEndDateNull
//            (Budget budget, LocalDate today, LocalDate endDate, LocalDate today2);
//    List<Target> findAllByBudgetAndStartDateIsLessThanEqualAndEndDateIsNullOrStartDateIsLessThanEqualAndEndDateIsBefore
//            (Budget budget, LocalDate today, LocalDate today2, LocalDate endDate);
//    List<Target> findAllByBudgetAndStartDateIsGreaterThanEqualAndEndDateIsNullOrEndDateIsBefore
//            (Budget budget, LocalDate today, LocalDate endDate);
//    List<Target> findAllByBudgetAndEndDateIsNull
//            (Budget budget);
//    List<Target> findAllByBudgetAndExpenseCategoryAndEndDateIsNull
//            (Budget budget, ExpenseCategory expenseCategory);
}
