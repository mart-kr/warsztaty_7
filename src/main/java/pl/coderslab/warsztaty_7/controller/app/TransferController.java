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
import pl.coderslab.warsztaty_7.service.SecurityService;
import pl.coderslab.warsztaty_7.service.TransferService;

import java.util.List;

@Controller
@RequestMapping(value = "/home/transfer")
public class TransferController {

    private final TransferService transferService;
    private final BankAccountService bankAccountService;
    private final SecurityService securityService;

    @Autowired
    public TransferController(TransferService transferService, BankAccountService bankAccountService,
                              SecurityService securityService) {
        this.transferService = transferService;
        this.bankAccountService = bankAccountService;
        this.securityService = securityService;
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "transfer")
    public Transfer createEmptyTransfer() {
        return new Transfer();
    }

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "bankAccounts")
    public List<BankAccount> findBankAccountsForBudget(@AuthenticationPrincipal final User user) {
        if (user.getBudget() != null) {
            return bankAccountService.findByBudgetId(user.getBudget().getId());
        } else {
            return null;
        }
    }

    @GetMapping(value = "/all")
    public String allTransfers(@AuthenticationPrincipal final User user, Model model) {
        model.addAttribute("transfers", transferService.findAllForBudgetOrderedByDate(user.getBudget()));
        return "allTransfers";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/add")
    public String showCreateTransferForm(@AuthenticationPrincipal final User user, Model model) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        }
        List<BankAccount> accounts = bankAccountService.findByBudgetId(user.getBudget().getId());
        if (accounts.size()>1) {
            model.addAttribute("action", "/home/transfer/add");
            return "transferForm";
        } else {
            return "redirect:/home/account/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public String createTransfer(@AuthenticationPrincipal final User user, @ModelAttribute final Transfer transfer) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        }
        List<BankAccount> accounts = bankAccountService.findByBudgetId(user.getBudget().getId());
        if (accounts.size()>1) {
            transferService.create(transfer);
            return "redirect:/home";
        } else {
            return "redirect:/home/account/add";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/edit/{id}")
    public String showEditTransferForm(@AuthenticationPrincipal final User user, @PathVariable final Long id, Model model) {
        if (securityService.canViewOrEditEntity(user, transferService.findById(id))) {
            model.addAttribute("action", "/home/transfer/edit/" + id);
            model.addAttribute("transfer", transferService.findById(id));
            return "transferForm";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @PostMapping(value = "/edit/{id}")
    public String editTransfer(@AuthenticationPrincipal final User user, @PathVariable final Long id, @ModelAttribute final Transfer transfer) {
        if (securityService.canViewOrEditEntity(user, transferService.findById(id)) && id.equals(transfer.getId())) {
            transferService.edit(transfer);
            return "redirect:/home";
        } else {
            throw new AccessDeniedException("You can't edit this entity");
        }
    }

    @GetMapping(value = "delete/{id}")
    public String deleteTransfer(@AuthenticationPrincipal final User user, @PathVariable final Long id) {
        if (securityService.canDeleteEntity(user, transferService.findById(id))) {
            transferService.deleteById(id);
            return "redirect:/home";
        } else {
            throw new AccessDeniedException("You can't delete this entity");
        }
    }
}
