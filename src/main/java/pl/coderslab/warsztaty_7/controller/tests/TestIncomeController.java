package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.IncomeCategoryService;
import pl.coderslab.warsztaty_7.service.IncomeService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value = "/home/income")
public class TestIncomeController {

    private final IncomeService incomeService;
    private final IncomeCategoryService incomeCategoryService;
    private final BankAccountService bankAccountService;

    @Autowired
    public TestIncomeController(IncomeService incomeService, IncomeCategoryService incomeCategoryService, BankAccountService bankAccountService) {
        this.incomeService = incomeService;
        this.incomeCategoryService = incomeCategoryService;
        this.bankAccountService = bankAccountService;
    }

    @ModelAttribute(name = "incomeCategories")
    public List<IncomeCategory> findAllCategories(){
        return incomeCategoryService.findAll();
    }

    @ModelAttribute(name = "income")
    public Income createEmptyIncome(){
        return new Income();
    }

    @ModelAttribute(name = "bankAccounts")
    public List<BankAccount> findBankAccountsForBudget(@AuthenticationPrincipal User user) {
        return bankAccountService.findByBudgetId(user.getBudget().getId());
    }

    @GetMapping(value = "/all")
    public String allIncomes(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("incomes", incomeService.findAllForBudgetOrderedByDate(user.getBudget()));
        return "test_incomes";
    }

    @GetMapping(value = "/category/{id}")
    public String findIncomesByCategory(@PathVariable Long id, Model model) {
        model.addAttribute("incomes", incomeService.findByCategoryId(id));
        return "test_incomes";
    }

    @GetMapping(value = "/add")
    public String showCreateIncomeForm(Model model) {
        model.addAttribute("action", "/home/income/add");
        return "incomeForm";
    }

    @PostMapping(value = "/add")
    public String createIncome(@ModelAttribute Income income) {
        incomeService.create(income);
        return "redirect:/home";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditIncomeForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/income/edit/" + id);
        model.addAttribute("income", incomeService.findById(id));
        return "incomeForm";
    }

    @PostMapping(value = "/edit/{id}")
    public String editIncome(@PathVariable Long id, @ModelAttribute Income income) {
        incomeService.edit(income);
        return "redirect:/home";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteIncome(@PathVariable Long id) {
        incomeService.deleteById(id);
        return "redirect:/home";
    }

    @GetMapping(value = "/thisMonth")
    @ResponseBody
    public String findIncomesFromThisMonth(@AuthenticationPrincipal User user, Model model){
        BigDecimal sumOfIncomesFromThisMonth = incomeService.sumAllFromThisMonth(user.getBudget());
//        model.addAttribute("sumOfIncomesFromThisMonth", sumOfIncomesFromThisMonth);

        return "Suma: " + String.valueOf(sumOfIncomesFromThisMonth);
    }




























//    @GetMapping(value = "/edit/{id}")
//    public String editIncome(@PathVariable Long id, Model model) {
//        model.addAttribute("income", incomeService.findById(id));
//        return "test_editIncome";
//    }
//
//    @PostMapping(value = "/")
//    public String createIncome(@ModelAttribute Income income) {
////        Long categoryId = income.getIncomeCategory().getId();
////        income.setIncomeCategory(incomeService.findCategoryById(categoryId)); //TODO: to jest tymczasowe rozwiązanie
//        incomeService.create(income);
//        return "test_incomes";
//    }
//
//    @PostMapping(value = "/edit/{id}")
//    public String editIncome(@ModelAttribute Income income) {
////        Long categoryId = income.getIncomeCategory().getId();
////        income.setIncomeCategory(incomeService.findCategoryById(categoryId)); //TODO: to jest tymczasowe rozwiązanie
//        incomeService.edit(income);
//        return "redirect:http://localhost:8080/home/income/";
//    }
//
//    @GetMapping(value = "delete/{id}")
//    public String deleteIncome(@PathVariable Long id) {
//        incomeService.deleteById(id);
//        return "redirect:http://localhost:8080/home/income/";
//    }
}
