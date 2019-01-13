package pl.coderslab.warsztaty_7.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.Income;
import pl.coderslab.warsztaty_7.model.IncomeCategory;
import pl.coderslab.warsztaty_7.repository.ExpenseRepository;
import pl.coderslab.warsztaty_7.repository.IncomeRepository;
import pl.coderslab.warsztaty_7.service.IncomeService;

import java.util.List;

@Service
@Primary
public class IncomeServiceJpaImpl implements IncomeService {

    private IncomeRepository incomeRepository;

    public IncomeServiceJpaImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public List<Income> findAll() {
        return this.incomeRepository.findAll();
    }

    @Override
    public List<Income> findByCategory(IncomeCategory incomeCategory) {
        return this.incomeRepository.findAllByIncomeCategory(incomeCategory);
    }

    @Override
    public List<Income> findByCategoryId(Long id) {
        return this.incomeRepository.findAllByIncomeCategoryId(id);
    }

    @Override
    public Income findById(Long id) {
        return this.incomeRepository.findOne(id);
    }

    @Override
    public Income create(Income income) {
        return this.incomeRepository.save(income);
    }

    @Override
    public Income edit(Income income) {
        return this.incomeRepository.save(income);
    }

    //TODO: do zmiany
    @Override
    public void deleteById(Long id) {
        this.incomeRepository.delete(id);
    }
}
