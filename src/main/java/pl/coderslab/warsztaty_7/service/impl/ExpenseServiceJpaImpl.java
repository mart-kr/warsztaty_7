package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.repository.ExpenseRepository;
import pl.coderslab.warsztaty_7.service.ExpenseService;
import pl.coderslab.warsztaty_7.util.ExpenseUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@Primary
@Transactional
public class ExpenseServiceJpaImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private ExpenseUtil expenseUtil;

    @Autowired
    public ExpenseServiceJpaImpl(ExpenseRepository expenseRepository, ExpenseUtil expenseUtil) {
        this.expenseRepository = expenseRepository;
        this.expenseUtil = expenseUtil;
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
    public void deleteByIds(Collection<Long> ids) {
        expenseRepository.deleteAllByIdIn(ids);
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

    @Override
    public Map<String, BigDecimal> sortedSumOfExpensesInCategory(List<Expense> expenses) {
        return expenseUtil.sortedSumOfExpensesInCategory(expenses);
    }

    @Override
    public List<Map.Entry<String, Integer>> sumOfSortedExpensesToPercentage(Map<String, BigDecimal> sortedExpenses) {
        return expenseUtil.sortedSumOfExpensesToPercentages(sortedExpenses);
    }

    @Override
    public BigDecimal sumOfAllExpensesFromThisMonth(Budget budget) {
        return expenseUtil.getSumOfAllExpenses(findExpensesInThisMonthForBudget(budget));
    }
}
