package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/operations")
public class OperationsController {

    private final OperationsService operationsService;

    @ModelAttribute(name = "operations")
    public List<Operation> allOperations(@AuthenticationPrincipal User user) {
        if (user == null || user.getBudget() == null) {
            return null;
        } else {
            return operationsService.loadAllOperationsForBudget(user.getBudget());
        }
    }

    @GetMapping
    public String allOperationsView() {
        return "operations";
    }

    @Autowired
    public OperationsController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }
}
