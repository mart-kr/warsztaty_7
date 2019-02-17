package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.GlobalTarget;

import java.math.BigDecimal;
import java.util.List;

public interface GlobalTargetService {

    GlobalTarget create(GlobalTarget globalTarget);
    GlobalTarget edit(GlobalTarget globalTarget);
    List<GlobalTarget> findAllByBudget(Budget budget);
    GlobalTarget findGlobalTargetWithEndDateIsNull(List<GlobalTarget> globalTargets, Budget budget);
    GlobalTarget setEndDateInNullGlobalTarget(GlobalTarget nullGlobalTarget, GlobalTarget formGlobalTarget);
    GlobalTarget findGlobalTargetForThisMonth(Budget budget);
    Integer getExpensePercent(BigDecimal expensesFromThisMonth, GlobalTarget globalTargetForThisMonth);
}
