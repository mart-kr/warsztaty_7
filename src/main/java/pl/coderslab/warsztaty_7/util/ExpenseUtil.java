package pl.coderslab.warsztaty_7.util;

import org.springframework.stereotype.Component;
import pl.coderslab.warsztaty_7.model.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExpenseUtil {

    public Map<String, BigDecimal> sortedSumOfExpensesInCategory(List<Expense> expenses) {
        return expenses.stream()
                .sorted((exp1, exp2) -> exp2.getAmount().compareTo(exp1.getAmount()))
                .collect(Collectors.toMap((Expense exp) -> exp.getExpenseCategory().getName(),
                        Expense::getAmount, (amount1, amount2) -> (amount1.add(amount2)), LinkedHashMap::new));

    }

    public List<Map.Entry<String, Integer>> sortedSumOfExpensesToPercentages(Map<String, BigDecimal> expensesSum) {
        if (expensesSum.isEmpty()) {
            return Collections.emptyList();
        }
        BigDecimal maxValue = expensesSum.entrySet().iterator().next().getValue();
        List<Map.Entry<String, Integer>> result = new ArrayList<>();
        Integer value;
        BigDecimal bd100 = new BigDecimal(100);
        for (Map.Entry<String, BigDecimal> entry : expensesSum.entrySet()) {
            value = entry.getValue().multiply(bd100)
                    .divide(maxValue, RoundingMode.HALF_UP)
                    .setScale(0, RoundingMode.HALF_UP)
                    .intValue();
            result.add(new AbstractMap.SimpleEntry<>(entry.getKey(), value));
        }
        return result;
    }

    public BigDecimal getSumOfAllExpenses(List<Expense> expenses){
        BigDecimal sum = BigDecimal.ZERO;
        for (Expense expense : expenses){
            sum = sum.add(expense.getAmount());
        }
        return sum;
    }
}
