package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.BudgetService;
import pl.coderslab.warsztaty_7.service.SecurityService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "home/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final SecurityService securityService;

    @Autowired
    public BudgetController(BudgetService budgetService, SecurityService securityService) {
        this.budgetService = budgetService;
        this.securityService= securityService;
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute
    public Budget createEmptybudget(){
        return new Budget();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateBudgetForm(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() != null) {
            return "redirect:/home";
        } else {
            model.addAttribute("action", "/home/budget/add");
            return "budgetForm";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public String createBudget(@AuthenticationPrincipal final User user, @ModelAttribute final Budget budget) {
        if (user.getBudget() == null) {
            budgetService.create(budget); // TODO: rozwiązać w lepszy sposób problem "org.hibernate.PersistentObjectException: detached entity passed to persist: pl.coderslab.warsztaty_7.model.User"
            budget.addUser(user);
            budgetService.edit(budget);
        }
        return "redirect:/home";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/edit/{id}")
    public String showEditBudgetForm(@AuthenticationPrincipal final User user, @PathVariable final Long id,
                                     Model model) {
        Budget budgetToEdit = budgetService.findById(id);
        if(securityService.canViewOrEditEntity(user, budgetToEdit)) {
            model.addAttribute("action", "/home/budget/edit/" + id);
            model.addAttribute("budget", budgetToEdit);
            return "budgetForm";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/edit/{id}")
    public String editBudget(@AuthenticationPrincipal final User user, @PathVariable final Long id,
                             @ModelAttribute @Valid final Budget budget) {
        if (securityService.canViewOrEditEntity(user, budgetService.findById(id)) && budget.getId().equals(id)) {
            budgetService.edit(budget);
            return "redirect:/home";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "delete/{id}")
    public String deleteBudget(@AuthenticationPrincipal final User user, @PathVariable final Long id) {
        Budget budgetToDelete = budgetService.findById(id);
        if (securityService.canDeleteBudget(user, budgetToDelete)) {
            budgetService.deleteById(id);
            return "redirect:/home";
        } else {
            throw new AccessDeniedException("You can't delete this entity");
        }
    }
}
