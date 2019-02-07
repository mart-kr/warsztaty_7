package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.Auditable;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.BudgetService;
import pl.coderslab.warsztaty_7.service.SecurityService;

@Controller
@RequestMapping(value = "/home/account")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final SecurityService securityService;
    private final BudgetService budgetService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, SecurityService securityService,
                                 BudgetService budgetService) {
        this.bankAccountService = bankAccountService;
        this.securityService = securityService;
        this.budgetService = budgetService;
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "bankAccount")
    public BankAccount createEmptyBankAccount() {
        return new BankAccount();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all" )
    public String allBankAccounts(@AuthenticationPrincipal final User user, Model model) {
        Budget userBudget = user.getBudget();
        if (userBudget != null) {
            model.addAttribute("bankAccounts", bankAccountService.findByBudgetId(userBudget.getId()));
            return "fragments/bankAccounts";
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateBankAccountForm(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() != null) {
            model.addAttribute("action", "/home/account/add");
            return "bankAccountForm";
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public String createBankAccount(@AuthenticationPrincipal final User user, @ModelAttribute BankAccount bankAccount) {
        if (user.getBudget() != null) {
            bankAccount.setBudget(user.getBudget());
            bankAccountService.create(bankAccount);
            budgetService.edit(user.getBudget());
            return "redirect:/home";
        } else {
            return "redirect:/home/budget/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/edit/{id}")
    public String showEditBankAccountForm(@AuthenticationPrincipal final User user, @PathVariable Long id, Model model) {
        BankAccount accountToEdit = bankAccountService.findById(id);
        if (securityService.canViewOrEditEntity(user, accountToEdit)) {
            model.addAttribute("action", "/home/account/edit/" + id);
            model.addAttribute("bankAccount", bankAccountService.findById(id));
            return "bankAccountForm";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/edit/{id}")
    public String editBankAccount(@AuthenticationPrincipal final User user, @PathVariable Long id, @ModelAttribute BankAccount bankAccount) {
        if (securityService.canViewOrEditEntity(user, bankAccount) && id.equals(bankAccount.getId())) {
            bankAccountService.edit(bankAccount);
            return "redirect:/home";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/delete/{id}")
    public String deleteBankAccount(@AuthenticationPrincipal final User user, @PathVariable Long id) {
        BankAccount accountToEdit = bankAccountService.findById(id);
        if (securityService.canDeleteEntity(user, accountToEdit)) {
            bankAccountService.deleteById(id);
            return "redirect:/home";
        } else {
            throw new AccessDeniedException("You can't delete this entity");
        }
    }




}
