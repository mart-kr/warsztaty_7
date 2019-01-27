package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.ExpenseCategoryService;
import pl.coderslab.warsztaty_7.service.ReceiptService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value = "/home/receipt")
public class TestReceiptController {

    private final ReceiptService receiptService;
    private final BankAccountService bankAccountService;
    private final ExpenseCategoryService expenseCategoryService;;

    @Autowired
    public TestReceiptController(ReceiptService receiptService, BankAccountService bankAccountService, ExpenseCategoryService expenseCategoryService) {
        this.receiptService = receiptService;
        this.bankAccountService = bankAccountService;
        this.expenseCategoryService = expenseCategoryService;
    }

    @ModelAttribute(name = "receipt")
    public Receipt createEmptyReceipt() {
        return new Receipt();
    }

    @ModelAttribute(name = "bankAccounts")
    public List<BankAccount> findBankAccountsForBudget(@AuthenticationPrincipal User user) {
        return bankAccountService.findByBudgetId(user.getBudget().getId());
    }

    @ModelAttribute
    public String allExpenseCategories(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("expenseCategories", expenseCategoryService.findAllForBudgetId(user.getBudget().getId()));
        return "expenseCategories";
    }

    @GetMapping(value = "/all")
    public String allReceipts(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("receipts", receiptService.findAllForBudgetOrderedByDate(user.getBudget()));
        return "test_receipts";
    }

    @GetMapping(value = "/add")
    public String showCreateReceiptForm(Model model) {
        model.addAttribute("action", "/home/receipt/add");
        return "receiptForm";
    }

    @RequestMapping(value={"/add", "/edit/{id}"}, params={"addExpense"})
    public String addExpense(final Receipt receipt, final BindingResult bindingResult) {
        receipt.getExpenses().add(new Expense());
        return "receiptForm";
    }

    @RequestMapping(value={"/add", "/edit/{id}"}, params={"removeExpense"})
    public String removeExpense(
            final Receipt receipt, final BindingResult bindingResult,
            final HttpServletRequest req) {
        final Integer expenseId = Integer.valueOf(req.getParameter("removeExpense"));
        receipt.getExpenses().remove(expenseId.intValue());
        return "receiptForm";
    }

    @PostMapping(value = "/add")
    public String createReceipt(@ModelAttribute Receipt receipt) {
        receiptService.create(receipt);
        return "redirect:/home";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditReceiptForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/receipt/edit/" + id);
        model.addAttribute("receipt", receiptService.findById(id));
        return "receiptForm";
    }

    @PostMapping(value = "/edit/{id}")
    public String editReceipt(@PathVariable Long id, @ModelAttribute Receipt receipt) {
        receiptService.edit(receipt);
        return "redirect:/home";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteReceipt(@PathVariable Long id) {
        receiptService.deleteById(id);
        return "redirect:/home";
    }

    @GetMapping(value = "/last5")
    public String last5Receipts(@AuthenticationPrincipal User user, Model model) {
        Budget budget = new Budget();
        budget.setName("test budget");
        budget.addUser(user);
        List<Receipt> last5 = receiptService.findLast5ReceiptsForBudget(user.getBudget());
        model.addAttribute("last5Receipts", last5);
        return "fragments/last-receipts";
    }

    @GetMapping(value = "/thisMonth")
    @ResponseBody
    public String findReceiptsFromThisMonth(@AuthenticationPrincipal User user, Model model){
        BigDecimal sumOfReceiptsFromThisMonth = receiptService.sumAllFromThisMonth(user.getBudget());
//        model.addAttribute("sumOfIncomesFromThisMonth", sumOfIncomesFromThisMonth);

        return "Suma: " + String.valueOf(sumOfReceiptsFromThisMonth);
    }

}
