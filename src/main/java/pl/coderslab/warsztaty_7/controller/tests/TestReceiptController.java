package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Receipt;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.ReceiptService;
import pl.coderslab.warsztaty_7.service.UserService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value = "/home/receipt")
public class TestReceiptController {

    private final ReceiptService receiptService;
    private final BankAccountService bankAccountService;

    @Autowired
    public TestReceiptController(ReceiptService receiptService, BankAccountService bankAccountService) {
        this.receiptService = receiptService;
        this.bankAccountService = bankAccountService;
    }

    @ModelAttribute(name = "receipt")
    public Receipt createEmptyReceipt() {
        return new Receipt();
    }

    @ModelAttribute(name = "bankAccounts")
    public List<BankAccount> findBankAccountsForBudget(@AuthenticationPrincipal User user) {
        return bankAccountService.findByBudgetId(user.getBudget().getId());
    }

    @GetMapping(value = "/all")
    public String allReceipts(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("receipts", receiptService.findAllForBudgetOrderedByDate(user.getBudget()));
        return "test_receipts";
    }

    @GetMapping(value = "/add")
    public String showCreateReceiptForm(Model model) {
        model.addAttribute("action", "/home/receipt/add");
        return "test_receiptForm";
    }

    @PostMapping(value = "/add")
    public String createReceipt(@ModelAttribute Receipt receipt) {
        receiptService.create(receipt);
        return "redirect:/home/receipt/all";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditReceiptForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/receipt/edit/" + id);
        model.addAttribute("receipt", receiptService.findById(id));
        return "test_receiptForm";
    }

    @PostMapping(value = "/edit/{id}")
    public String editReceipt(@PathVariable Long id, @ModelAttribute Receipt receipt) {
        receiptService.edit(receipt);
        return "redirect:/home/receipt/all";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteReceipt(@PathVariable Long id) {
        receiptService.deleteById(id);
        return "redirect:/home/receipt/all";
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
