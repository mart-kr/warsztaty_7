package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.repository.ExpenseRepository;
import pl.coderslab.warsztaty_7.service.ExpenseService;

import java.util.List;

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
}
