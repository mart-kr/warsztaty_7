package pl.coderslab.warsztaty_7.util;

import org.springframework.stereotype.Component;
import pl.coderslab.warsztaty_7.model.Target;

import java.time.LocalDate;
import java.util.List;

@Component
public class TargetUtil {

//    public Target getTargetWhereDateIsNull(List<Target> targets){
//        Target nullTarget = targets.stream()
//                                    .filter(tar -> tar.getEndDate() == null)
//                                    .findFirst()
//                                    .orElse(null);
//        return nullTarget;
//    }

//    public Target setEndDateInNulltarget(Target nullTarget, Target formTarget){
//        LocalDate startDate = formTarget.getStartDate();
//        LocalDate endDateWork = null;
//
//        int december = 12;
//        int lastDayOfDecember = 31;
//
//        if (startDate.getMonthValue() == 1) {
//            endDateWork = startDate.withYear(startDate.getYear() - 1).withMonth(december).withDayOfMonth(lastDayOfDecember);
//        } else {
//            endDateWork = startDate.withMonth(startDate.getMonthValue() - 1);
//            endDateWork = endDateWork.withDayOfMonth(endDateWork.lengthOfMonth());
//        }
//        LocalDate endDate = endDateWork;
//        nullTarget.setEndDate(endDate);
//
//        return nullTarget;
//    }
}




