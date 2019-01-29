package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.TransferService;

import java.util.List;

@Controller
@RequestMapping(value = "/home/transfer")
public class TestTransferController {

    private final TransferService transferService;
    private final BankAccountService bankAccountService;

    @Autowired
    public TestTransferController(TransferService transferService, BankAccountService bankAccountService) {
        this.transferService = transferService;
        this.bankAccountService = bankAccountService;
    }

    @ModelAttribute(name = "transfer")
    public Transfer createEmptyTransfer() {
        return new Transfer();
    }

    @ModelAttribute(name = "bankAccounts")
    public List<BankAccount> findBankAccountsForBudget(@AuthenticationPrincipal User user) {
        return bankAccountService.findByBudgetId(user.getBudget().getId());
    }

    @GetMapping(value = "/all")
    public String allTransfers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("transfers", transferService.findAllForBudgetOrderedByDate(user.getBudget()));
        return "test_transfers";
    }

    @GetMapping(value = "/add")
    public String showCreateTransferForm(Model model) {
        model.addAttribute("action", "/home/transfer/add");
        return "transferForm";
    }

    @PostMapping(value = "/add")
    public String createTransfer(@ModelAttribute Transfer transfer) {
        transferService.create(transfer);
        return "redirect:/home";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditTransferForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/transfer/edit/" + id);
        model.addAttribute("transfer", transferService.findById(id));
        return "transferForm";
    }

    @PostMapping(value = "/edit/{id}")
    public String editTransfer(@PathVariable Long id, @ModelAttribute Transfer transfer) {
        transferService.edit(transfer);
        return "redirect:/home";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteTransfer(@PathVariable Long id) {
        transferService.deleteById(id);
        return "redirect:/home";
    }
}
