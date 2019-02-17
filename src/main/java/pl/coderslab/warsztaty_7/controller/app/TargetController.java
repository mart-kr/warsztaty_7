package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.service.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(value = "/home/target")
public class TargetController {

    private final TargetService targetService;
    private final ExpenseCategoryService expenseCategoryService;
    private final SecurityService securityService;
    private final ExpenseService expenseService;
    private final GlobalTargetService globalTargetService;

    @Autowired
    public TargetController(TargetService targetService, ExpenseCategoryService expenseCategoryService,
                            SecurityService securityService, ExpenseService expenseService, GlobalTargetService globalTargetService) {
        this.targetService = targetService;
        this.expenseCategoryService = expenseCategoryService;
        this.securityService = securityService;
        this.expenseService = expenseService;
        this.globalTargetService = globalTargetService;
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "target")
    public Target createEmptyTarget() {
        return new Target();
    }

    @ModelAttribute(name = "expenseCategories")
    public List<ExpenseCategory> findAllCategories(@AuthenticationPrincipal final User user){
        return expenseCategoryService.findAllForBudgetId(user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all" )
    public String allTargets(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() != null) {
            model.addAttribute("monthTarAll", targetService.findAllByBudget(user.getBudget()));
            return "targetsHistory";
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateTargetForm(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() != null) {
            LocalDate today = LocalDate.now();
            model.addAttribute("action", "/home/target/add");
            model.addAttribute("today", today);
            return "targetForm";
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public String createTarget(@AuthenticationPrincipal final User user, @ModelAttribute @Valid final Target target,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (user.getBudget() != null){
            List<Target> targets = targetService.findByCategoryId(target.getExpenseCategory().getId());
            if (targets.size() > 0) {
                Target nullTarget = targetService.findTargetWithEndDateIsNull(targets, user.getBudget());
                if (nullTarget != null) {
                    if (nullTarget.getStartDate().isAfter(target.getStartDate())) {
                        bindingResult.rejectValue("startDate", "error message");
                    } else {
                        targetService.edit(targetService.setEndDateInNullTarget(nullTarget, target));
                    }
                }
            }
            if (bindingResult.hasErrors()) {
                return "targetForm";
            } else {
                target.setBudget(user.getBudget());
                target.setStartDate(target.getStartDate().withDayOfMonth(1));
                targetService.create(target);
                return "redirect:/home/target/thisMonth";
            }
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/thisMonth")
    public String targetsInThisMonth(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() != null) {
            //the ratio bar of expenses to the targets in each category
            List<Target> targets = targetService.findAllFromThisMonth(user.getBudget());
            List<Expense> expensesInThisMonth = expenseService.findExpensesInThisMonthForBudget(user.getBudget());
            Map<String, BigDecimal> expensesSum = expenseService.sortedSumOfExpensesInCategory(expensesInThisMonth);
            model.addAttribute("monthTarExp", targetService.targetAndExpensesToPercentage(targets, expensesSum));

            //the ratio bar of whole expenses to the global target
            GlobalTarget globalTargetForThisMonth = globalTargetService.findGlobalTargetForThisMonth(user.getBudget());
            BigDecimal expensesFromThisMonth = expenseService.sumOfAllExpensesFromThisMonth(user.getBudget());

            model.addAttribute("globalTarget", globalTargetForThisMonth);
            model.addAttribute("expenses", expensesFromThisMonth);
            if (!Objects.equals(globalTargetForThisMonth.getAmount(), null) && !expensesFromThisMonth.equals(BigDecimal.ZERO)) {
                model.addAttribute("expensePercent", globalTargetService.getExpensePercent(expensesFromThisMonth, globalTargetForThisMonth));
            } else {
                model.addAttribute("expensePercent", 0);
            }

            return "targetsMonth";
        } else {
            return "redirect:/home/budget/add";
        }
    }

    //TODO:Edycja i usuwanie

//    @GetMapping(value = "/edit/{id}")
//    public String showEditTargetForm(@PathVariable Long id, Model model) {
//        model.addAttribute("action", "/home/target/edit/" + id);
//        model.addAttribute("target", targetService.findById(id));
//        return "targetForm";
//    }

//    @PostMapping(value = "/edit/{id}")
//    public String editTarget(@PathVariable Long id, @ModelAttribute Target target) {
//        targetService.edit(target);
//        return "redirect:/home";
//    }

//    @GetMapping(value = "/delete/{id}")
//    public String deleteTarget(@PathVariable Long id) {
//        targetService.deleteById(id);
//        return "redirect:/home";
//    }

}
