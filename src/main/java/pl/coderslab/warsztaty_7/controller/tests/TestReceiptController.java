package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Receipt;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.ReceiptService;
import pl.coderslab.warsztaty_7.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/home/receipt")
public class TestReceiptController {

    private final ReceiptService receiptService;
    private final UserService userService;

    @Autowired
    public TestReceiptController(ReceiptService receiptService, UserService userService) {
        this.receiptService = receiptService;
        this.userService = userService;
    }

    @ModelAttribute
    public Receipt createEmptyReceipt() {
        return new Receipt();
    }

    @GetMapping(value = "/all")
    public String allReceipts(Model model) {
        model.addAttribute("receipts", receiptService.findAllOrderedByDate());
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

}
