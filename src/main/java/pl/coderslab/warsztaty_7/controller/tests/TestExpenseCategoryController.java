package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.ExpenseCategoryService;

@Controller
@RequestMapping(value = "home/expenseCategory")
public class TestExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    @Autowired
    public TestExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @ModelAttribute
    private ExpenseCategory createEmptyExpenseCategory() {
        return new ExpenseCategory();
    }

    @GetMapping(value = "/all")
    public String allExpenseCategories(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("expenseCategories", expenseCategoryService.findAllForBudgetId(user.getBudget().getId()));
        return "expenseCategories";
    }

    @GetMapping(value = "/add")
    public String showCreateExpenseCategoryForm(Model model) {
        model.addAttribute("action", "/home/expenseCategory/add");
        return "expenseCategoryForm";
    }

    @PostMapping(value = "/add")
    public String createExpenseCategory(@AuthenticationPrincipal User user, @ModelAttribute ExpenseCategory expenseCategory) {
        expenseCategory.setBudget(user.getBudget());
        expenseCategoryService.create(expenseCategory);
        return "redirect:/home/expenseCategory/all";
    }

    @GetMapping(value = "edit/{id}")
    public String showEditExpenseCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/expenseCategory/edit/" + id);
        model.addAttribute("expenseCategory", expenseCategoryService.findById(id));
        return "expenseCategoryForm";
    }

    @PostMapping(value = "edit/{id}")
    public String editExpenseCategory(@PathVariable Long id, @ModelAttribute ExpenseCategory expenseCategory) {
        expenseCategoryService.edit(expenseCategory);
        return "redirect:/home/expenseCategory/all";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteExpenseCategory(@PathVariable Long id) {
        expenseCategoryService.deleteById(id);
        return "redirect:/home/expenseCategory/all";
    }

}
