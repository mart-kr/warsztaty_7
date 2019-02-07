package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.warsztaty_7.model.Operation;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.OperationsService;

import java.util.List;

@Controller
@RequestMapping("/home/operations")
public class OperationsController {

    private final OperationsService operationsService;

    @PreAuthorize("hasRole('USER')")
    @ModelAttribute(name = "operations")
    public List<Operation> allOperations(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return null;
        } else {
            return operationsService.loadAllOperationsForBudget(user.getBudget());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String allOperationsView(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else {
            return "operations";
        }
    }

    @Autowired
    public OperationsController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }
}
