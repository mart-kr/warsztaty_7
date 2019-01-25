package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.UserService;

@Controller
@RequestMapping(value = "/home/bankAccount")
public class TestBankAccountController {

    private final BankAccountService bankAccountService;
    private final UserService userService;

    @Autowired
    public TestBankAccountController(BankAccountService bankAccountService, UserService userService) {
        this.bankAccountService = bankAccountService;
        this.userService = userService;
    }

    @ModelAttribute
    BankAccount createEmptyBankAccount() {
        return new BankAccount();
    }

    @GetMapping(value = "/all" )
    public String allBankAccounts(@AuthenticationPrincipal User user, Model model) {
        Budget userBudget = user.getBudget();
        if (userBudget != null) {
            model.addAttribute("bankAccounts", bankAccountService.findByBudgetId(userBudget.getId()));
            return "fragments/bankAccounts";
        } else {
            throw new IllegalArgumentException("Could not find your budget!"); //tymczasowe rozwiązanie
        }
    }

    @GetMapping(value = "/add")
    public String showCreateBankAccountForm(Model model) {
        model.addAttribute("action", "/home/bankAccount/add");
        return "bankAccountForm";
    }

    @PostMapping(value = "/add")
    public String createBankAccount(@AuthenticationPrincipal User user, @ModelAttribute BankAccount bankAccount) {
        bankAccount.setBudget(user.getBudget());
        bankAccountService.create(bankAccount);
        return "redirect:/home";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditBankAccountForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/bankAccount/edit/" + id);
        model.addAttribute("bankAccount", bankAccountService.findById(id));
        return "bankAccountForm";
    }

    @PostMapping(value = "/edit/{id}")
    public String editBankAccount(@PathVariable Long id, @ModelAttribute BankAccount bankAccount) {
        bankAccountService.edit(bankAccount);
        return "redirect:/home";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteById(id);
        return "redirect:/home";
    }




}
