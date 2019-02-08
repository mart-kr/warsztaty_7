package pl.coderslab.warsztaty_7.util;

import org.springframework.stereotype.Component;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.Receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ReceiptUtil {

    public Expense createNewExpense(Receipt receipt) {
        Expense expense = new Expense();
        if (receipt.getExpenses().isEmpty()) {
            expense.setAmount(receipt.getAmount());
        } else {
            BigDecimal expensesSum = calculateExpensesSum(receipt);
            expense.setAmount(receipt.getAmount().subtract(expensesSum));
        }
        return expense;
    }

    private BigDecimal calculateExpensesSum(Receipt receipt) {
        return receipt.getExpenses().stream()
                .map(x -> x.getAmount())
                .reduce((x1, x2) -> x1.add(x2))
                .orElse(BigDecimal.ZERO);
    }

    public boolean validateExpensesAmount(Receipt receipt) {
        BigDecimal expensesSum = calculateExpensesSum(receipt);
        return receipt.getAmount().setScale(2, RoundingMode.HALF_UP)
                .equals(expensesSum.setScale(2, RoundingMode.HALF_UP));
    }


}
