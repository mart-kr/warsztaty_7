package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.Target;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.ExpenseCategoryService;
import pl.coderslab.warsztaty_7.service.SecurityService;
import pl.coderslab.warsztaty_7.service.TargetService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = "/home/target")
public class TargetController {

    private final TargetService targetService;
    private final ExpenseCategoryService expenseCategoryService;
    private final SecurityService securityService;

    @Autowired
    public TargetController(TargetService targetService, ExpenseCategoryService expenseCategoryService,
                            SecurityService securityService) {
        this.targetService = targetService;
        this.expenseCategoryService = expenseCategoryService;
        this.securityService = securityService;
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "target")
    public Target createEmptyTarget() {
        return new Target();
    }

    @ModelAttribute(name = "expenseCategories")
    public List<ExpenseCategory> findAllCategories(){
        return expenseCategoryService.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all" )
    public String allTargets(@AuthenticationPrincipal User user, Model model) {
        if (user.getBudget() != null) {
            model.addAttribute("monthTarAll", targetService.findAllByBudget(user.getBudget()));
            return "targetsHistory";
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateTargetForm(@AuthenticationPrincipal User user, Model model) {
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
    public String createTarget(@AuthenticationPrincipal @Valid User user, @ModelAttribute Target target,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (user.getBudget() != null){
            List<Target> targets = targetService.findByCategoryId(target.getExpenseCategory().getId());
            if (targets.size() > 0) {
                Target nullTarget = targetService.findTargetWithEndDateIsNull(targets, target.getBudget());
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
                target.setStartDate(target.getStartDate().withDayOfMonth(01));
                targetService.create(target);
                return "redirect:/home/target/thisMonth";
            }
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/thisMonth")
    public String targetsInThisMonth(@AuthenticationPrincipal User user, Model model) {
        if (user.getBudget() != null) {
            model.addAttribute("monthTarSum", targetService.sumAllFromThisMonth(user.getBudget()));
            return "targets";
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
