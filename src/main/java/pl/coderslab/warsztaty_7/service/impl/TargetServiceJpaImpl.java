package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.Target;
import pl.coderslab.warsztaty_7.repository.TargetRepository;
import pl.coderslab.warsztaty_7.service.TargetService;
import pl.coderslab.warsztaty_7.util.TargetUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TargetServiceJpaImpl implements TargetService {

    private TargetRepository targetRepository;
    private TargetUtil targetUtil;

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

    @Override
    public List<Target> sumAllFromThisMonth(Budget budget) {
        return targetRepository.findAllInCurrentMonth(budget);

    }

    @Override
    public Target findTargetWithEndDateIsNull(List<Target> targets, Budget budget){
        Target nullTarget = targets.stream()
                                   .filter(tar -> tar.getBudget().equals(budget))
                                   .filter(tar -> tar.getEndDate() == null)
                                   .findFirst()
                                   .orElse(null);
        return nullTarget;
//        return targetUtil.getTargetWhereDateIsNull(targets);
    }

    @Override
    public Target setEndDateInNullTarget(Target nullTarget, Target formTarget){
        LocalDate startDate = formTarget.getStartDate();
        LocalDate endDateWork = null;

        int december = 12;
        int lastDayOfDecember = 31;

        if (startDate.getMonthValue() == 1) {
            endDateWork = startDate.withYear(startDate.getYear() - 1).withMonth(december).withDayOfMonth(lastDayOfDecember);
        } else {
            endDateWork = startDate.withMonth(startDate.getMonthValue() - 1);
            endDateWork = endDateWork.withDayOfMonth(endDateWork.lengthOfMonth());
        }
        LocalDate endDate = endDateWork;
        nullTarget.setEndDate(endDate);

        return nullTarget;
//        return targetUtil.setEndDateInNulltarget(nullTarget, formTarget);
    }

    @Override
    public List<Target> findAllByBudget(Budget budget) {
        return targetRepository.findAllByBudget(budget);
    }
}
