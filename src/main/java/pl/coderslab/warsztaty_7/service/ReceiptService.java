package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.Receipt;

import java.math.BigDecimal;
import java.util.List;

public interface ReceiptService {

    List<Receipt> findAllOrderedByDate();
    List<Receipt> findAllForBudgetOrderedByDate(Budget budget);
    Receipt findById(Long id);
    Receipt create(Receipt receipt);
    Receipt edit(Receipt receipt);
    void deleteById(Long id);
    Receipt findByExpenseId(Long id);
    List<Receipt> findLast5ReceiptsForBudget(Budget budget);
    BigDecimal sumAllFromThisMonth(Budget budget);
    Expense createNewExpense(Receipt receipt);
    boolean validateExpensesAmount(Receipt receipt);


}
