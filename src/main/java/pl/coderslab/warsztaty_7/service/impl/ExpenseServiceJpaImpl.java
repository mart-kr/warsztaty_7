package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.repository.ExpenseRepository;
import pl.coderslab.warsztaty_7.service.ExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
public class ExpenseServiceJpaImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceJpaImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> findByCategory(ExpenseCategory expenseCategory) {
        return expenseRepository.findAllByExpenseCategory(expenseCategory);
    }

    @Override
    public List<Expense> findByCategoryId(Long id) {
        return expenseRepository.findAllByExpenseCategoryId(id);
    }

    @Override
    public List<Expense> findByReceiptId(Long id) {
        return expenseRepository.findAllByReceiptId(id);
    }

    @Override
    public Expense findById(Long id) {
        return expenseRepository.findOne(id);
    }

    @Override
    public Expense create(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense edit(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public void deleteById(Long id) {
        expenseRepository.delete(id);
    }

    @Override
    public List<Expense> findExpensesInThisMonthForBudget(Budget budget) {
        Collection<Long> ids = new ArrayList<>();
        for (User user: budget.getUsers()) {
            ids.add(user.getId());
        }
        LocalDate now = LocalDate.now();
        LocalDate begin = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());
        return expenseRepository
                .findAllByCreatedByUserIdInAndReceiptDateOfPaymentBetween
                        (ids, begin, end);
    }

    public Map<String, BigDecimal> sortedSumOfExpensesInCategory(List<Expense> expenses) {
        return expenses.stream()
                .sorted((exp1, exp2) -> exp2.getAmount().compareTo(exp1.getAmount()))
                .collect(Collectors.toMap((Expense exp) -> exp.getExpenseCategory().getName(),
                        Expense::getAmount, (amount1, amount2) -> (amount1.add(amount2)), LinkedHashMap::new));

    }
}
