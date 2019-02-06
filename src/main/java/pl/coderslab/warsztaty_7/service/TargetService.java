package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.Target;
import pl.coderslab.warsztaty_7.util.TargetExpenseAmountsTransfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TargetService {

    List<Target> findAll();
    List<Target> findByCategory(ExpenseCategory expenseCategory);
    List<Target> findByCategoryId(Long id);
    Target findById(Long id);
    Target create(Target target);
    Target edit(Target target);
    void deleteById(Long id);
    List<Target> findAllFromThisMonth(Budget budget);
    Target findTargetWithEndDateIsNull(List<Target> target, Budget budget);
    Target setEndDateInNullTarget(Target nullTarget, Target formTarget);
    List<Target> findAllByBudget(Budget budget);
    List<TargetExpenseAmountsTransfer> targetAndExpensesToPercentage(List<Target> targets, Map<String, BigDecimal> expensesSum);

}
