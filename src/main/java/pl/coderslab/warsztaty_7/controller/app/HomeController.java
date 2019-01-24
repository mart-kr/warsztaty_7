package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.warsztaty_7.model.BankAccount;
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

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public String homeAppPage(@AuthenticationPrincipal User user) {
        // głowny widok aplikacji
        return "test";
    }

    @ModelAttribute(name = "lastReceipts")
    public List<Receipt> last5Receipts(@AuthenticationPrincipal User user) {
        if (user.getBudget() != null) {
            return receiptService.findLast5ReceiptsForBudget(user.getBudget());
        } else {
            return null;
        }
    }

    @ModelAttribute(name = "monthIncSum")
    public BigDecimal sumIncomesInThisMonth(@AuthenticationPrincipal User user) {
        if (user.getBudget() != null) {
            return incomeService.sumAllFromThisMonth(user.getBudget());
        } else {
            return null;
        }
    }

    @ModelAttribute(name = "monthRecSum")
    public BigDecimal sumReceiptsInThisMonth(@AuthenticationPrincipal User user) {
        if (user.getBudget() != null) {
            return receiptService.sumAllFromThisMonth(user.getBudget());
        } else {
            return null;
        }
    }

    @ModelAttribute(name = "monthExpSum")
    public Map<String, BigDecimal> sumExpensesInThisMonth(@AuthenticationPrincipal User user) {
        if (user.getBudget() != null) {
            return expenseService.sortedSumOfExpensesInCategory(
                    expenseService.findExpensesInThisMonthForBudget(user.getBudget()));
        } else {
            return null;
        }
    }

    @ModelAttribute(name = "accounts")
    public List<BankAccount> accounts(@AuthenticationPrincipal User user) {
        if (user.getBudget() != null) {
            return bankAccountService.findByBudgetId(user.getBudget().getId());
        } else {
            return null;
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
