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
import pl.coderslab.warsztaty_7.service.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final ReceiptService receiptService;
    private final ExpenseService expenseService;
    private final BankAccountService bankAccountService;
    private final IncomeService incomeService;
    private final CashflowService cashflowService;
    private final TargetService targetService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String homeAppPage(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else {
            return "home";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "lastReceipts")
    public List<Receipt> last5Receipts(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        } else {
            return receiptService.findLast5ReceiptsForBudget(user.getBudget());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "monthIncSum")
    public BigDecimal sumIncomesInThisMonth(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        } else {
            return incomeService.sumAllFromThisMonth(user.getBudget());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "monthRecSum")
    public BigDecimal sumReceiptsInThisMonth(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        } else {
            return receiptService.sumAllFromThisMonth(user.getBudget());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "monthExpSum")
    public Map<String, BigDecimal> sumExpensesInThisMonth(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        } else {
            List<Expense> expenses = expenseService.findExpensesInThisMonthForBudget(user.getBudget());
            return expenseService.sortedSumOfExpensesInCategory(expenses);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "monthExpPercent")
    public List<Map.Entry<String, Integer>> expensesInPercentageThisMonth(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        } else {
            return expenseService.sumOfSortedExpensesToPercentage(sumExpensesInThisMonth(user));
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "accounts")
    public List<BankAccount> accounts(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        } else {
            return bankAccountService.findByBudgetId(user.getBudget().getId());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "incomePercentCashflow")
    public Integer incomePercentCashflow(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return 0;
        } else {
            return cashflowService.incomePercent(incomeService.sumAllFromThisMonth(user.getBudget()), receiptService.sumAllFromThisMonth(user.getBudget()));
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "receiptPercentCashflow")
    public Integer receiptPercentCashflow(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return 0;
        } else {
            return cashflowService.receiptPercent(receiptService.sumAllFromThisMonth(user.getBudget()), incomeService.sumAllFromThisMonth(user.getBudget()));
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "balanceCashflow")
    public BigDecimal balanceCashflow(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        } else {
            return cashflowService.balanceCashflow(receiptService.sumAllFromThisMonth(user.getBudget()), incomeService.sumAllFromThisMonth(user.getBudget()));
        }
    }

    @Autowired
    public HomeController(ReceiptService receiptService, ExpenseService expenseService, BankAccountService bankAccountService, IncomeService incomeService, CashflowService cashflowService, TargetService targetService) {
        this.receiptService = receiptService;
        this.expenseService = expenseService;
        this.bankAccountService = bankAccountService;
        this.incomeService = incomeService;
        this.cashflowService = cashflowService;
        this.targetService = targetService;
    }
}
