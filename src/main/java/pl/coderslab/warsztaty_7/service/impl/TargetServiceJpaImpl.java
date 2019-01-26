package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.Target;
import pl.coderslab.warsztaty_7.repository.TargetRepository;
import pl.coderslab.warsztaty_7.service.TargetService;

import java.util.List;

@Service
@Transactional
public class TargetServiceJpaImpl implements TargetService {

    private TargetRepository targetRepository;

    @Autowired
    public TargetServiceJpaImpl(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    @Override
    public List<Target> findAll() {
        return targetRepository.findAll();
    }

    @Override
    public List<Target> findByCategory(ExpenseCategory expenseCategory) {
        return targetRepository.findAllByExpenseCategory(expenseCategory);
    }

    @Override
    public List<Target> findByCategoryId(Long id) {
        return targetRepository.findAllByExpenseCategoryId(id);
    }

    @Override
    public Target findById(Long id) {
        return targetRepository.findOne(id);
    }

    @Override
    public Target create(Target target) {
        return targetRepository.save(target);
    }

    @Override
    public Target edit(Target target) {
        return targetRepository.save(target);
    }

    @Override
    public void deleteById(Long id) {
        targetRepository.delete(id);
    }
}
