package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.Receipt;
import pl.coderslab.warsztaty_7.service.ExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceStubImpl implements ExpenseService {

    private List<Expense> expenses = new ArrayList<Expense>() {{
        add(new Expense(1L, new BigDecimal(100), new ExpenseCategory(1L, "kat1", true),new Receipt(1L, new BigDecimal(100), LocalDate.parse("2019-01-01"), "sklep1", "notatka1") ));
        add(new Expense(2L, new BigDecimal(200.55), new ExpenseCategory(2L, "kat2", true), new Receipt(2L, new BigDecimal(200.55), LocalDate.parse("2019-01-01"), "sklep1", "notatka1")));
        add(new Expense(3L, new BigDecimal(333), new ExpenseCategory(3L, "kat3", true), new Receipt(3L, new BigDecimal(333), LocalDate.parse("2019-01-01"), "sklep1", "notatka1")));
    }};

    private List<ExpenseCategory> expenseCategories = new ArrayList<ExpenseCategory>() {{
       add(new ExpenseCategory(4L, "kat4", true));
       add(new ExpenseCategory(5L, "kat5", true));
       add(new ExpenseCategory(6L, "kat6", true));
    }};


    @Override
    public List<Expense> findAll() {
        return this.expenses;
    }

/*    @Override
    public List<ExpenseCategory> findAllCategories() {
        return this.expenseCategories;
    }*/

    @Override
    public List<Expense> findByCategory(ExpenseCategory expenseCategory) {
        return this.expenses.stream()
                .filter(p -> p.getExpenseCategory().equals(expenseCategory))
                .collect(Collectors.toList());
    }

/*    @Override
    public ExpenseCategory findCategoryById(Long id) {
        return this.expenseCategories.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }*/

    @Override
    public List<Expense> findByCategoryId(Long id) {
        return this.expenses.stream()
                .filter(p -> p.getExpenseCategory().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> findByReceiptId(Long id) {
        return this.expenses.stream()
                .filter(p -> p.getReceipt().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public Expense findById(Long id) {
        return this.expenses.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Expense create(Expense expense) {
        expense.setId(this.expenses.stream().mapToLong(
                p -> p.getId()).max().getAsLong() + 1);
        this.expenses.add(expense);
        return expense;
    }

    @Override
    public Expense edit(Expense expense) {
        for (int i = 0; i < this.expenses.size(); i++) {
            if (Objects.equals(this.expenses.get(i).getId(), expense.getId())) {
                this.expenses.set(i, expense);
                return expense;
            }
        }
        throw  new RuntimeException("Expense not found: " + expense.getId());
    }

    @Override
    public void deleteById(Long id) {
        for (int i = 0; i < this.expenses.size(); i++) {
            if (Objects.equals(this.expenses.get(i).getId(), id)) {
                this.expenses.remove(i);
                return;
            }
        }
        throw  new RuntimeException("Expense not found: " + id);
    }

    @Override
    public void deleteByIds(Collection<Long> ids) {

    }

    @Override
    public List<Expense> findExpensesInThisMonthForBudget(Budget budget) {
        return Collections.emptyList();
    }

    @Override
    public Map<String, BigDecimal> sortedSumOfExpensesInCategory(List<Expense> expenses) {
        return Collections.emptyMap();
    }

    @Override
    public List<Map.Entry<String, Integer>> sumOfSortedExpensesToPercentage(Map<String, BigDecimal> sortedExpenses) {
        return Collections.emptyList();
    }

    @Override
    public BigDecimal sumOfAllExpensesFromThisMonth(Budget budget) {
        return null;
    }


}
