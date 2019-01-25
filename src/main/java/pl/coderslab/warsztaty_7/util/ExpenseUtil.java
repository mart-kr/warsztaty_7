package pl.coderslab.warsztaty_7.util;

import org.springframework.stereotype.Component;
import pl.coderslab.warsztaty_7.model.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExpenseUtil {

    public Map<String, BigDecimal> sortedSumOfExpensesInCategory(List<Expense> expenses) {
        return expenses.stream()
                .sorted((exp1, exp2) -> exp2.getAmount().compareTo(exp1.getAmount()))
                .collect(Collectors.toMap((Expense exp) -> exp.getExpenseCategory().getName(),
                        Expense::getAmount, (amount1, amount2) -> (amount1.add(amount2)), LinkedHashMap::new));

    }

    public Map<String, Integer> sortedSumOfExpensesToPercentages(Map<String, BigDecimal> expensesSum) {
        BigDecimal maxValue;
        if (expensesSum.isEmpty()) {
            return new LinkedHashMap<>();
        } else {
            maxValue = expensesSum.entrySet().iterator().next().getValue();
        }

        Map<String, Integer> result = new LinkedHashMap<>();
        Integer value;
        BigDecimal bd100 = new BigDecimal(100);
        for (Map.Entry<String, BigDecimal> entry : expensesSum.entrySet()) {
            value = entry.getValue().multiply(bd100).divide(maxValue,RoundingMode.HALF_UP).intValue();
            result.put(entry.getKey(), value);
        }
        return result;
    }

}
