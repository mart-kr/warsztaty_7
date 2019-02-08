package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.Auditable;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.ExpenseCategoryService;
import pl.coderslab.warsztaty_7.service.SecurityService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "home/expenseCategory")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;
    private final SecurityService securityService;

    @Autowired
    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService, SecurityService securityService) {
        this.expenseCategoryService = expenseCategoryService;
        this.securityService = securityService;
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "expenseCategory")
    public ExpenseCategory createEmptyExpenseCategory() {
        return new ExpenseCategory();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all")
    public String allExpenseCategories(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else {
            model.addAttribute("expenseCategories", expenseCategoryService.findAllForBudgetId(user.getBudget().getId()));
            return "expenseCategories";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateExpenseCategoryForm(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else {
            model.addAttribute("action", "/home/expenseCategory/add");
            return "expenseCategoryForm";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public String createExpenseCategory(@AuthenticationPrincipal final User user,
                                        @ModelAttribute @Valid final ExpenseCategory expenseCategory,
                                        BindingResult bindingResult) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else if (bindingResult.hasErrors()) {
            return "expenseCategoryForm";
        } else {
            expenseCategory.setBudget(user.getBudget());
            expenseCategoryService.create(expenseCategory);
            return "redirect:/home/expenseCategory/all";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "edit/{id}")
    public String showEditExpenseCategoryForm(@AuthenticationPrincipal final User user, final @PathVariable Long id,
                                              Model model) {
        if (securityService.canViewOrEditEntity(user, expenseCategoryService.findById(id))) {
            model.addAttribute("action", "/home/expenseCategory/edit/" + id);
            model.addAttribute("expenseCategory", expenseCategoryService.findById(id));
            return "expenseCategoryForm";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }

    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "edit/{id}")
    public String editExpenseCategory(@AuthenticationPrincipal final User user, @PathVariable final Long id,
                                      @ModelAttribute final ExpenseCategory expenseCategory) {
        if (securityService.canViewOrEditEntity(user, expenseCategoryService.findById(id))) {
            expenseCategoryService.edit(expenseCategory);
            return "redirect:/home/expenseCategory/all";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "delete/{id}")
    public String deleteExpenseCategory(@AuthenticationPrincipal final User user, @PathVariable Long id) {
        if (securityService.canDeleteEntity(user, expenseCategoryService.findById(id))) {
            expenseCategoryService.deleteById(id);
            return "redirect:/home/expenseCategory/all";
        } else {
            throw new AccessDeniedException("You can't delete this entity");
        }
    }
}
