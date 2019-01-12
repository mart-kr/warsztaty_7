package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.service.ExpenseCategoryService;
import pl.coderslab.warsztaty_7.service.ExpenseService;

import java.util.List;

@Controller
@RequestMapping(value = "/home/expense")
public class TestExpenseController {

    private final ExpenseService expenseService;

    private final ExpenseCategoryService expenseCategoryService;

    @Autowired
    public TestExpenseController(ExpenseService expenseService, ExpenseCategoryService expenseCategoryService) {
        this.expenseService = expenseService;
        this.expenseCategoryService = expenseCategoryService;
    }

    @ModelAttribute(name = "expenseCategories")
    public List<ExpenseCategory> findAllCategories() {
        return expenseCategoryService.findAll();
    }

    @ModelAttribute(name = "expenses")
    public List<Expense> findAllExpenses() {
        return expenseService.findAll();
    }

    @ModelAttribute(name = "expense")
    public Expense createEmptyExpense() {
        return new Expense();
    }

    @GetMapping(value = "/")
    public String allExpenses() {
        return "test_expenses";
    }

    @GetMapping(value = "/category/{id}")
    public String findExpensesByCategory(@PathVariable Long id, Model model) {
        model.addAttribute("expenses", expenseService.findByCategoryId(id));
        return "test_expenses";
    }

    @GetMapping(value = "/receipt/{id}")
    public String findExpencesByReceipt(@PathVariable Long id, Model model) {
        model.addAttribute("expenses", expenseService.findByReceiptId(id));
        return "test_expenses";
    }

    @GetMapping(value = "/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        model.addAttribute("expense", expenseService.findById(id));
        return "test_editExpense";
    }

    @PostMapping(value = "/")
    public String createExpense(@ModelAttribute Expense expense) {
        //Long categoryId = expense.getExpenseCategory().getId();
        //expense.setExpenseCategory(expenseService.findCategoryById(categoryId)); //TODO: to jest tymczasowe rozwiązanie
        expenseService.create(expense);
        return "test_expenses";
    }

    @PostMapping(value = "/edit/{id}")
    public String editExpense(@ModelAttribute Expense expense) {
//        Long categoryId = expense.getExpenseCategory().getId();
//        expense.setExpenseCategory(expenseCategoryService.findById(categoryId)); //TODO: to jest tymczasowe rozwiązanie
        expenseService.edit(expense);
        return "redirect:http://localhost:8080/home/expense/";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteById(id);
        return "redirect:http://localhost:8080/home/expense/";
    }

}
