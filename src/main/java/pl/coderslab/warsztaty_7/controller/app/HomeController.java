package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.Receipt;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.ExpenseService;
import pl.coderslab.warsztaty_7.service.IncomeService;
import pl.coderslab.warsztaty_7.service.ReceiptService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {

    private ReceiptService receiptService;
    private ExpenseService expenseService;
    private BankAccountService bankAccountService;
    private IncomeService incomeService;

//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public String homeAppPage(@AuthenticationPrincipal User user) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else {
            return "home";
        }
    }

//    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "lastReceipts")
    public List<Receipt> last5Receipts(@AuthenticationPrincipal User user) {
        if (user == null || user.getBudget() == null) {
            return null;
        } else {
            return receiptService.findLast5ReceiptsForBudget(user.getBudget());
        }
    }

//    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "monthIncSum")
    public BigDecimal sumIncomesInThisMonth(@AuthenticationPrincipal User user) {
        if (user == null || user.getBudget() == null) {
            return null;
        } else {
            return incomeService.sumAllFromThisMonth(user.getBudget());
        }
    }

//    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "monthRecSum")
    public BigDecimal sumReceiptsInThisMonth(@AuthenticationPrincipal User user) {
        if (user == null || user.getBudget() == null) {
            return null;
        } else {
            return receiptService.sumAllFromThisMonth(user.getBudget());
        }
    }

//    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "monthExpSum")
    public Map<String, BigDecimal> sumExpensesInThisMonth(@AuthenticationPrincipal User user) {
        if (user == null || user.getBudget() == null) {
            return null;
        } else {
//            return expenseService.sortedSumOfExpensesInCategory(
//                    expenseService.findExpensesInThisMonthForBudget(user.getBudget()));
            List<Expense> expensesThisMonth = expenseService.findExpensesInThisMonthForBudget(user.getBudget());
            return expenseService.sortedSumOfExpensesInCategory(expensesThisMonth);
        }
    }
//    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "accounts")
    public List<BankAccount> accounts(@AuthenticationPrincipal User user) {
        if (user == null || user.getBudget() == null) {
            return null;
        } else {
            return bankAccountService.findByBudgetId(user.getBudget().getId());
        }
    }

    @Autowired
    public HomeController(ReceiptService receiptService, ExpenseService expenseService, BankAccountService bankAccountService, IncomeService incomeService) {
        this.receiptService = receiptService;
        this.expenseService = expenseService;
        this.bankAccountService = bankAccountService;
        this.incomeService = incomeService;
    }
}
