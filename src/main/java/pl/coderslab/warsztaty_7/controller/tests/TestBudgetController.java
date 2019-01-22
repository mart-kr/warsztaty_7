package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.BudgetService;
import pl.coderslab.warsztaty_7.service.UserService;

@Controller
@RequestMapping(value = "home/budget")
public class TestBudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    @Autowired
    public TestBudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @ModelAttribute
    private Budget createEmptybudget(){
        return new Budget();
    }

    @GetMapping(value = "/all")
    public String showBudget(Model model){
        model.addAttribute("budgets", budgetService.findAll());
        return "test_budget";
    }

    @GetMapping(value = "/add")
    public String showCreateBudgetForm(Model model) {
        model.addAttribute("action", "/home/budget/add");
        return "test_budgetForm";
    }

    @PostMapping(value = "/add")
    public String createBudget(@AuthenticationPrincipal User user, @ModelAttribute Budget budget) {
        if (user.getBudget() == null) {
            budgetService.create(budget); // TODO: rozwiązać w lepszy sposób problem "org.hibernate.PersistentObjectException: detached entity passed to persist: pl.coderslab.warsztaty_7.model.User"
            budget.addUser(user);
            budgetService.edit(budget);
            return "redirect:/home/budget/all";
        } else {
            return "redirect:/home";
        }

    }

    @GetMapping(value = "/edit/{id}")
    public String showEditBudgetForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/budget/edit/" + id);
        model.addAttribute("budget", budgetService.findById(id));
        return "test_budgetForm";
    }

    @PostMapping(value = "/edit/{id}")
    public String editBudget(@ModelAttribute Budget budget) {
        budgetService.edit(budget);
        return "redirect:/home/budget/all";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteBudget(@PathVariable Long id) {
        budgetService.deleteById(id);
        return "redirect:/home/budget/all";
    }
}
