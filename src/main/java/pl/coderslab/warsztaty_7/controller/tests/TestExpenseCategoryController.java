package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
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
    public String allExpenseCategories(Model model) {
        model.addAttribute("expenseCategories", expenseCategoryService.findAll());
        return "test_expenseCategories";
    }

    @GetMapping(value = "/add")
    public String showCreateExpenseCategoryForm(Model model) {
        return "test_addExpenseCategory";
    }

    @PostMapping(value = "/add")
    public String createExpenseCategory(@ModelAttribute ExpenseCategory expenseCategory) {
        expenseCategoryService.create(expenseCategory);
        return "redirect:http://localhost:8080/home/expenseCategory/all";
    }

    @GetMapping(value = "edit/{id}")
    public String showEditExpenseCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("expenseCategory", expenseCategoryService.findById(id));
        return "test_editExpenseCategory";
    }

    @PostMapping(value = "edit/{id}")
    public String editExpenseCategory(@PathVariable Long id, @ModelAttribute ExpenseCategory expenseCategory) {
        expenseCategoryService.edit(expenseCategory);
        return "redirect:http://localhost:8080/home/expenseCategory/all";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteExpenseCategory(@PathVariable Long id) {
        expenseCategoryService.deleteById(id);
        return "redirect:http://localhost:8080/home/expenseCategory/all";
    }

}
