package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.IncomeCategory;
import pl.coderslab.warsztaty_7.repository.IncomeCategoryRepository;
import pl.coderslab.warsztaty_7.service.IncomeCategoryService;

import java.util.List;

@Service
@Transactional
public class IncomeCategoryServiceJpaImpl implements IncomeCategoryService {

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Override
    public List<IncomeCategory> findAll() {
        return incomeCategoryRepository.findAll();
    }

    @Override
    public IncomeCategory findById(Long id) {
        return incomeCategoryRepository.findOne(id);
    }

    @Override
    public IncomeCategory create(IncomeCategory incomeCategory) {
        return incomeCategoryRepository.save(incomeCategory);
    }

    @Override
    public IncomeCategory edit(IncomeCategory incomeCategory) {
        return incomeCategoryRepository.save(incomeCategory);
    }

    //TODO: do zmiany
    @Override
    public void deleteById(Long id) {
        incomeCategoryRepository.delete(id);
    }

    @Override
    public List<IncomeCategory> findAllForBudgetId(Long id) {
        return incomeCategoryRepository.findAllByIsGlobalIsTrueOrBudgetIdOrderByName(id);
    }
}
