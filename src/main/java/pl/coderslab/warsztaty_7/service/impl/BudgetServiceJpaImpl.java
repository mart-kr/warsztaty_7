package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.repository.BudgetRepository;
import pl.coderslab.warsztaty_7.service.BudgetService;

import java.util.List;

@Service
@Transactional
public class BudgetServiceJpaImpl implements BudgetService {

    private BudgetRepository budgetRepository;

    @Autowired
    public BudgetServiceJpaImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    @Override
    public Budget findById(Long id) {
        return budgetRepository.findOne(id);
    }

    @Override
    public Budget create(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public Budget edit(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public void deleteById(Long id) {
        budgetRepository.delete(id);
    }
}
