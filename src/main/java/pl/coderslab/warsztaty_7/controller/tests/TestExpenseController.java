package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.ExpenseCategoryService;
import pl.coderslab.warsztaty_7.service.ExpenseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

//    @ModelAttribute(name = "expenseCategories")
//    public List<ExpenseCategory> findAllCategories() {
//        return expenseCategoryService.findAll();
//    }
//
//    @ModelAttribute(name = "expense")
//    public Expense createEmptyExpense() {
//        return new Expense();
//    }
//
//    @GetMapping(value = "/category/{id}")
//    public String findExpensesByCategory(@PathVariable Long id, Model model) {
//        model.addAttribute("expenses", expenseService.findByCategoryId(id));
//        return "test_expenses";
//    }
//
//    @GetMapping(value = "/receipt/{id}")
//    public String findExpencesByReceipt(@PathVariable Long id, Model model) {
//        model.addAttribute("expenses", expenseService.findByReceiptId(id));
//        return "test_expenses";
//    }
//
//    @GetMapping(value = "/add")
//    public String showCreateExpenseForm(Model model) {
//        model.addAttribute("action", "/home/expense/add");
//        return "test_expenseForm";
//    }

//    @PostMapping(value = "/add")
//    public String createExpense(@ModelAttribute Expense expense) {
//        //Long categoryId = expense.getExpenseCategory().getId();
//        //expense.setExpenseCategory(expenseService.findCategoryById(categoryId)); //TODO: to jest tymczasowe rozwiązanie
//        expenseService.create(expense);
//        return "redirect:/home/expense/all";
//    }

//    @GetMapping(value = "/edit/{id}")
//    public String showEditExpenseForm(@PathVariable Long id, Model model) {
//        model.addAttribute("action", "/home/expense/edit/" + id);
//        model.addAttribute("expense", expenseService.findById(id));
//        return "test_expenseForm";
//    }

//    @PostMapping(value = "/edit/{id}")
//    public String editExpense(@ModelAttribute Expense expense) {
//        Long categoryId = expense.getExpenseCategory().getId();
//        expense.setExpenseCategory(expenseCategoryService.findById(categoryId)); //TODO: to jest tymczasowe rozwiązanie
//        expenseService.edit(expense);
//        return "redirect:/home/expense/all";
//    }
//
//    @GetMapping(value = "delete/{id}")
//    public String deleteExpense(@PathVariable Long id) {
//        expenseService.deleteById(id);
//        return "redirect:/home/expense/all";
//    }

//    @GetMapping(value = "/thisMonth")
//    public String expensesInThisMonth(@AuthenticationPrincipal User user, Model model) {
//        if (user.getBudget() == null) {
//            return "redirect:/home/budget/add";
//        }
//        List<Expense> thisMonthExpenses = expenseService.findExpensesInThisMonthForBudget(user.getBudget());
//        Map<String, BigDecimal> sortedExpenses = expenseService.sortedSumOfExpensesInCategory(thisMonthExpenses);
//        model.addAttribute("monthExpSum", sortedExpenses);
//        return "expensesSum";
//    }

}
