package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.Target;
import pl.coderslab.warsztaty_7.repository.TargetRepository;
import pl.coderslab.warsztaty_7.service.TargetService;
import pl.coderslab.warsztaty_7.util.TargetExpenseAmountsTransfer;
import pl.coderslab.warsztaty_7.util.TargetUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TargetServiceJpaImpl implements TargetService {

    private TargetRepository targetRepository;
    private TargetUtil targetUtil;

    @Autowired
    public TargetServiceJpaImpl(TargetRepository targetRepository, TargetUtil targetUtil) {
        this.targetRepository = targetRepository;
        this.targetUtil = targetUtil;
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

    @Override
    public List<Target> findAllFromThisMonth(Budget budget) {
        return targetRepository.findAllInCurrentMonth(budget);

    }

    @Override
    public Target findTargetWithEndDateIsNull(List<Target> targets, Budget budget){
        return targetUtil.getTargetWhereDateIsNull(targets, budget);
    }

    @Override
    public Target setEndDateInNullTarget(Target nullTarget, Target formTarget){

        return targetUtil.setEndDateInNulltarget(nullTarget, formTarget);
    }

    @Override
    public List<Target> findAllByBudget(Budget budget) {
        return targetRepository.findAllByBudget(budget);
    }

    @Override
    public List<TargetExpenseAmountsTransfer> targetAndExpensesToPercentage(List<Target> targets, Map<String, BigDecimal> expensesSum) {
        return targetUtil.targetExpensesPercentageAmounts(targets, expensesSum);
    }


}
