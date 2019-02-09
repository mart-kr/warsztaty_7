package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.IncomeCategoryService;
import pl.coderslab.warsztaty_7.service.IncomeService;
import pl.coderslab.warsztaty_7.service.SecurityService;

import java.util.List;

@Controller
@RequestMapping(value = "/home/income")
public class IncomeController {

    private final IncomeService incomeService;
    private final IncomeCategoryService incomeCategoryService;
    private final BankAccountService bankAccountService;
    private final SecurityService securityService;

    @Autowired
    public IncomeController(IncomeService incomeService, IncomeCategoryService incomeCategoryService,
                            BankAccountService bankAccountService, SecurityService securityService) {
        this.incomeService = incomeService;
        this.incomeCategoryService = incomeCategoryService;
        this.bankAccountService = bankAccountService;
        this.securityService = securityService;
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "incomeCategories")
    public List<IncomeCategory> findAllCategoriesForBudget(@AuthenticationPrincipal final User user){
        if (user.getBudget() == null) {
            return null;
        } else {
            return incomeCategoryService.findAllForBudgetId(user.getBudget().getId());
        }
    }
    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "income")
    public Income createEmptyIncome(){
        return new Income();
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "bankAccounts")
    public List<BankAccount> findBankAccountsForBudget(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        } else {
            return bankAccountService.findByBudgetId(user.getBudget().getId());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all")
    public String allIncomes(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() != null) {
            model.addAttribute("incomes", incomeService.findAllForBudgetOrderedByDate(user.getBudget()));
            //TODO: WIDOK DO LISTY WSZYSTKICH WPŁYWÓW
            return "allIncomes";
        }
        return "redirect:/home/budget/add";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/category/{id}")
    public String findIncomesByCategory(@AuthenticationPrincipal final User user, @PathVariable Long id, Model model) {
        if (user.getBudget() != null && securityService.canViewOrEditEntity(user, incomeService.findById(id))) {
            model.addAttribute("incomes", incomeService.findByCategoryId(id));
            //TODO: WIDOK DO LISTY WSZYSTKICH WPŁYWÓW
            return "allIncomes";
        }
        return "redirect:/home/budget/add";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateIncomeForm(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        }
        List<BankAccount> accounts = bankAccountService.findByBudgetId(user.getBudget().getId());
        if (accounts.size()>0) {
            model.addAttribute("action", "/home/income/add");
            return "incomeForm";
        }
        return "redirect:/home/account/add";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public String createIncome(@AuthenticationPrincipal final User user, @ModelAttribute final Income income) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        }
        List<BankAccount> accounts = bankAccountService.findByBudgetId(user.getBudget().getId());
        if (accounts.size()>0) {
            incomeService.create(income);
            return "redirect:/home";
        }
        return "redirect:/home/account/add";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/edit/{id}")
    public String showEditIncomeForm(@AuthenticationPrincipal final User user, @PathVariable final Long id,
                                     Model model) {
        if (securityService.canViewOrEditEntity(user, incomeService.findById(id))) {
            model.addAttribute("action", "/home/income/edit/" + id);
            model.addAttribute("income", incomeService.findById(id));
            return "incomeForm";
        }
        throw new AccessDeniedException("You can't edit this entity");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/edit/{id}")
    public String editIncome(@AuthenticationPrincipal final User user, @PathVariable final Long id,
                             @ModelAttribute final Income income) {
        if (securityService.canViewOrEditEntity(user, incomeService.findById(id)) && income.getId().equals(id)) {
            incomeService.edit(income);
            return "redirect:/home";
        }
        throw new AccessDeniedException("You can't edit this entity");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/delete/{id}")
    public String deleteIncome(@AuthenticationPrincipal final User user, @PathVariable final Long id) {
        if (securityService.canDeleteEntity(user, incomeService.findById(id))) {
            incomeService.deleteById(id);
            return "redirect:/home";
        }
        throw new AccessDeniedException("You can't delete this entity");
    }

}
