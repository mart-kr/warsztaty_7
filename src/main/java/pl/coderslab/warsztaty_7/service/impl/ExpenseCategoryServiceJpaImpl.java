package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.repository.ExpenseCategoryRepository;
import pl.coderslab.warsztaty_7.service.ExpenseCategoryService;

import java.util.List;

@Service
public class ExpenseCategoryServiceJpaImpl implements ExpenseCategoryService {

    private ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    public ExpenseCategoryServiceJpaImpl(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public List<ExpenseCategory> findAll() {
        return expenseCategoryRepository.findAll();
    }

    @Override
    public ExpenseCategory findById(Long id) {
        return expenseCategoryRepository.findOne(id);
    }

    @Override
    public ExpenseCategory create(ExpenseCategory expenseCategory) {
        return expenseCategoryRepository.save(expenseCategory);
    }

    @Override
    public ExpenseCategory edit(ExpenseCategory expenseCategory) {
        return expenseCategoryRepository.save(expenseCategory);
    }

    @Override
    public void deleteById(Long id) {
        expenseCategoryRepository.delete(id);
    }
}
