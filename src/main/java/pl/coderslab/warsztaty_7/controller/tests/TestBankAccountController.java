package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.service.BankAccountService;

@Controller
@RequestMapping(value = "/home/bankAccount")
public class TestBankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public TestBankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @ModelAttribute
    BankAccount createEmptyBankAccount() {
        return new BankAccount();
    }

    @GetMapping(value = "/all" )
    public String allBankAccounts(Model model) {
        model.addAttribute("bankAccounts", bankAccountService.findAll());
        return "test_bankAccounts";
    }

    @GetMapping(value = "/add")
    public String showCreateBankAccountForm(Model model) {
        model.addAttribute("action", "/home/bankAccount/add");
        return "test_bankAccountForm";
    }

    @PostMapping(value = "/add")
    public String createBankAccount(@ModelAttribute BankAccount bankAccount) {
        bankAccountService.create(bankAccount);
        return "redirect:/home/bankAccount/all";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditBankAccountForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/bankAccount/edit/" + id);
        model.addAttribute("bankAccount", bankAccountService.findById(id));
        return "test_bankAccountForm";
    }

    @PostMapping(value = "/edit/{id}")
    public String editBankAccount(@PathVariable Long id, @ModelAttribute BankAccount bankAccount) {
        bankAccountService.edit(bankAccount);
        return "redirect:/home/bankAccount/all";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteById(id);
        return "redirect:/home/bankAccount/all";
    }




}
