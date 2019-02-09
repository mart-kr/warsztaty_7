package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.ExpenseCategoryService;
import pl.coderslab.warsztaty_7.service.ReceiptService;
import pl.coderslab.warsztaty_7.service.SecurityService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/home/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;
    private final BankAccountService bankAccountService;
    private final ExpenseCategoryService expenseCategoryService;
    private final SecurityService securityService;

    @Autowired
    public ReceiptController(ReceiptService receiptService, BankAccountService bankAccountService,
                             ExpenseCategoryService expenseCategoryService, SecurityService securityService) {
        this.receiptService = receiptService;
        this.bankAccountService = bankAccountService;
        this.expenseCategoryService = expenseCategoryService;
        this.securityService = securityService;
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "receipt")
    public Receipt createEmptyReceipt() {
        return new Receipt();
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "bankAccounts")
    public List<BankAccount> findBankAccountsForBudget(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        }
        return bankAccountService.findByBudgetId(user.getBudget().getId());
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute
    public String allExpenseCategories(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        }
        model.addAttribute("expenseCategories", expenseCategoryService.findAllForBudgetId(user.getBudget().getId()));
        return "expenseCategories";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all")
    public String allReceipts(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        }
        model.addAttribute("receipts", receiptService.findAllForBudgetOrderedByDate(user.getBudget()));
        return "allReceipts";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateReceiptForm(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        }
        List<BankAccount> accounts = bankAccountService.findByBudgetId(user.getBudget().getId());
        if (accounts.isEmpty()) {
            return "redirect:/home/account/add";
        } else {
            model.addAttribute("action", "/home/receipt/add");
            return "receiptForm";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = {"/add", "/edit/{id}"}, params = {"addExpense"})
    public String addExpense(@ModelAttribute final Receipt receipt) {
        Expense expense = receiptService.createNewExpense(receipt);
        receipt.getExpenses().add(expense);
        return "receiptForm";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = {"/add", "/edit/{id}"}, params = {"removeExpense"})
    public String removeExpense(@ModelAttribute final Receipt receipt, final HttpServletRequest req) {
        final Integer expenseId = Integer.valueOf(req.getParameter("removeExpense"));
        receipt.getExpenses().remove(expenseId.intValue());
        return "receiptForm";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public String createReceipt(@AuthenticationPrincipal final User user, @ModelAttribute @Valid final Receipt receipt,
                                BindingResult bindingResult) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        }
        List<BankAccount> accounts = bankAccountService.findByBudgetId(user.getBudget().getId());
        if (accounts.isEmpty()) {
            return "redirect:/home/account/add";
        } else if (receiptService.validateExpensesAmount(receipt)) {
            receiptService.create(receipt);
            return "redirect:/home";
        } else {
            bindingResult.rejectValue("amount", "amount.error");
            return "receiptForm";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/edit/{id}")
    public String showEditReceiptForm(@AuthenticationPrincipal final User user, @PathVariable final Long id, Model model) {
        if (securityService.canViewOrEditEntity(user, receiptService.findById(id))) {
            model.addAttribute("action", "/home/receipt/edit/" + id);
            model.addAttribute("receipt", receiptService.findById(id));
            return "receiptForm";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/edit/{id}")
    public String editReceipt(@AuthenticationPrincipal final User user, @PathVariable final Long id,
                              @Valid @ModelAttribute Receipt receipt, BindingResult bindingResult) {
        if (securityService.canViewOrEditEntity(user, receiptService.findById(id)) && receiptService.validateExpensesAmount(receipt)
                && receipt.getId().equals(id)) {
            receiptService.edit(receipt);
            return "redirect:/home";
        } else if (!securityService.canViewOrEditEntity(user, receipt) || !receipt.getId().equals(id)) {
            throw new AccessDeniedException("You can't edit this entity");
        } else {
            bindingResult.rejectValue("amount", "amount.error");
            return "receiptForm";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "delete/{id}")
    public String deleteReceipt(@AuthenticationPrincipal final User user, @PathVariable final Long id) {
        if (securityService.canDeleteEntity(user, receiptService.findById(id))) {
            receiptService.deleteById(id);
            return "redirect:/home";
        } else {
            throw new AccessDeniedException("You can't delete this entity");
        }
    }
}
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping(value = "/last5")
//    public String last5Receipts(@AuthenticationPrincipal User user, Model model) {
//        Budget budget = new Budget();
//        budget.setName("test budget");
//        budget.addUser(user);
//        List<Receipt> last5 = receiptService.findLast5ReceiptsForBudget(user.getBudget());
//        model.addAttribute("last5Receipts", last5);
//        return "fragments/lastReceipts";
//    }

//    @PreAuthorize("hasRole('USER')")
//    @GetMapping(value = "/thisMonth")
//    @ResponseBody
//    public String findReceiptsFromThisMonth(@AuthenticationPrincipal User user, Model model){
//        BigDecimal sumOfReceiptsFromThisMonth = receiptService.sumAllFromThisMonth(user.getBudget());
////        model.addAttribute("sumOfIncomesFromThisMonth", sumOfIncomesFromThisMonth);
//
//        return "Suma: " + String.valueOf(sumOfReceiptsFromThisMonth);
//    }

