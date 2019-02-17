package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.GlobalTarget;
import pl.coderslab.warsztaty_7.repository.GlobalTargetRepository;
import pl.coderslab.warsztaty_7.service.GlobalTargetService;
import pl.coderslab.warsztaty_7.util.TargetUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class GlobalTargetServiceJpaImpl implements GlobalTargetService {

    private GlobalTargetRepository globalTargetRepository;
    private TargetUtil targetUtil;

    @Autowired
    public GlobalTargetServiceJpaImpl(GlobalTargetRepository globalTargetRepository, TargetUtil targetUtil) {
        this.globalTargetRepository = globalTargetRepository;
        this.targetUtil = targetUtil;
    }

    @Override
    public GlobalTarget create(GlobalTarget globalTarget) {
        return globalTargetRepository.save(globalTarget);
    }

    @Override
    public GlobalTarget edit(GlobalTarget globalTarget) {
        return globalTargetRepository.save(globalTarget);
    }

    @Override
    public List<GlobalTarget> findAllByBudget(Budget budget) {
        return globalTargetRepository.findAllByBudget(budget);
    }

    @Override
    public GlobalTarget findGlobalTargetWithEndDateIsNull(List<GlobalTarget> globalTargets, Budget budget){
        GlobalTarget nullGlobalTarget = globalTargets.stream()
                                                    .filter(tar -> tar.getEndDate() == null)
                                                    .findFirst()
                                                    .orElse(null);
        return nullGlobalTarget;
//        return globalTargetUtil.getTargetWhereDateIsNull(globalTargets);
        //TODO:ogarnąć i dołożyć do zwykłych targetów

    }

    @Override
    public GlobalTarget findGlobalTargetForThisMonth(Budget budget){
         List<GlobalTarget> globaltargets = globalTargetRepository.findGlobalTargetForThisMonth(budget);
         return globaltargets.stream()
                 .findFirst()
                 .orElse(new GlobalTarget());
    }

    public Integer getExpensePercent(BigDecimal expensesFromThisMonth, GlobalTarget globalTargetForThisMonth){
        return targetUtil.getExpensesPercent(expensesFromThisMonth, globalTargetForThisMonth);
    }


    @Override
    public GlobalTarget setEndDateInNullGlobalTarget(GlobalTarget nullGlobalTarget, GlobalTarget formGlobalTarget){
        LocalDate startDate = formGlobalTarget.getStartDate();
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
        nullGlobalTarget.setEndDate(endDate);

        return nullGlobalTarget;
//        return targetUtil.setEndDateInNulltarget(nullTarget, formTarget);

        //TODO:ogarnąć duplikujący się kod
    }
}
