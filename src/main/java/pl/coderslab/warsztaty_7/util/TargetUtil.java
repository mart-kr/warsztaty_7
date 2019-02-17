package pl.coderslab.warsztaty_7.util;

import org.springframework.stereotype.Component;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.GlobalTarget;
import pl.coderslab.warsztaty_7.model.Target;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TargetUtil {
    BigDecimal bd100 = new BigDecimal(100);


    public Integer getGlobalTargetPercent(List<Expense> expenses, GlobalTarget globalTarget){
        BigDecimal targetsSumFromThisMonth = getSumOfExpenses(expenses);
        Integer maxPercentage = 100;
        BigDecimal bd100 = new BigDecimal(100);
        Integer value = globalTarget.getAmount().compareTo(targetsSumFromThisMonth);

        if (value == 0){
            return maxPercentage;
        } else if (value == -1){
            return globalTarget.getAmount().multiply(bd100).divide(targetsSumFromThisMonth, RoundingMode.HALF_UP).intValue();
        } else {
            return maxPercentage;
        }
    }

    public Integer getExpensesPercent(List<Expense> expenses, GlobalTarget globalTarget){
        BigDecimal bd = new BigDecimal(10000);

        BigDecimal expensesSumFromThisMonth = getSumOfExpenses(expenses);
        Integer maxPercentage = 100;
        BigDecimal bd100 = new BigDecimal(100);
//        Integer value = expensesSumFromThisMonth.compareTo(globalTarget.getAmount());
        Integer value = expensesSumFromThisMonth.compareTo(bd);

        if (value == 0){
            return maxPercentage;
        } else if (value == -1){
//            return expensesSumFromThisMonth.multiply(bd100).divide(globalTarget.getAmount(), RoundingMode.HALF_UP).intValue();
            return expensesSumFromThisMonth.multiply(bd100).divide(bd, RoundingMode.HALF_UP).intValue();
        } else {
            return maxPercentage;
        }
    }

    public Integer getExpensesPercent(BigDecimal expensesFromThisMonth, GlobalTarget globalTargetForThisMonth){
        return expensesFromThisMonth.multiply(bd100)
                .divide(globalTargetForThisMonth.getAmount(), RoundingMode.HALF_UP)
                .intValue();
    }


    public BigDecimal getSumOfExpenses(List<Expense> expenses){
        BigDecimal expensesSumFromThisMonth = BigDecimal.ZERO;

        for (Expense expense : expenses){
            expensesSumFromThisMonth = expensesSumFromThisMonth.add(expense.getAmount());
        }
        return expensesSumFromThisMonth;
    }



    public Target getTargetWhereDateIsNull(List<Target> targets, Budget budget){
        Target nullTarget = targets.stream()
                                    .filter(tar -> tar.getBudget().equals(budget))
                                    .filter(tar -> tar.getEndDate() == null)
                                    .findFirst()
                                    .orElse(null);
        return nullTarget;
    }

    public Target setEndDateInNulltarget(Target nullTarget, Target formTarget){
        LocalDate startDate = formTarget.getStartDate();
        LocalDate endDateWork = null;

        int december = 12;
        int lastDayOfDecember = 31;

        if (startDate.getMonthValue() == 1) {
            endDateWork = startDate.withYear(startDate.getYear() - 1).withMonth(december).withDayOfMonth(lastDayOfDecember);
        } else {
            endDateWork = startDate.withMonth(startDate.getMonthValue() - 1);
            endDateWork = endDateWork.withDayOfMonth(endDateWork.lengthOfMonth());
        }
        LocalDate endDate = endDateWork;
        nullTarget.setEndDate(endDate);

        return nullTarget;
    }

    public List<Map.Entry<String, Integer>> targetsToPercentagesSorted(List<Target> targets) {
        if (targets.isEmpty()) {
            return Collections.emptyList();
        }
        List<Target> sortedTargets = targets.stream()
                .sorted((t1, t2) -> t2.getAmount().compareTo(t1.getAmount()))
                .collect(Collectors.toList());
        BigDecimal maxValue = sortedTargets.get(0).getAmount();
        List<Map.Entry<String, Integer>> result = new ArrayList<>();
        Integer value;
        BigDecimal bd100 = new BigDecimal(100);
        for (Target target : sortedTargets) {
            value = target.getAmount().multiply(bd100)
                    .divide(maxValue, RoundingMode.HALF_UP)
                    .setScale(0, RoundingMode.HALF_UP)
                    .intValue();
            result.add(new AbstractMap.SimpleEntry<>(target.getExpenseCategory().getName(), value));
        }
        return result;
    }

    public List<TargetExpenseAmountsTransfer> targetExpensesPercentageAmounts(List<Target> targets, Map<String, BigDecimal> expenses) {
        if (targets.isEmpty()) {
            return Collections.emptyList();
        }
        targets.sort((o1, o2) -> o2.getAmount().compareTo(o1.getAmount()));
        List<TargetExpenseAmountsTransfer> result = new ArrayList<>();
        BigDecimal bd100 = new BigDecimal(100);
        for (Target target : targets) {
            String categoryName = target.getExpenseCategory().getName();
            BigDecimal expenseAmount;
            Integer expensePercentage;
            if (expenses.isEmpty() || !expenses.containsKey(categoryName)) {
                expenseAmount = BigDecimal.ZERO;
                expensePercentage = 0;
            } else {
                expenseAmount = expenses.get(categoryName);
                expensePercentage = expenseAmount.multiply(bd100)
                        .divide(target.getAmount(), RoundingMode.HALF_UP)
                        .setScale(0, RoundingMode.HALF_UP)
                        .intValue();
                if (expensePercentage > 100) {
                    expensePercentage = 100;
                }
            }
            result.add(new TargetExpenseAmountsTransfer(categoryName, target.getAmount(), expenseAmount,
                    100, expensePercentage));
        }
        return result;
    }
}




