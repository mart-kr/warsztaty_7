package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.BudgetService;
import pl.coderslab.warsztaty_7.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/add-user")
public class BudgetUserController {

    private final UserService userService;
    private final BudgetService budgetService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String addUserToBudgetForm() {
        return "add-budget-user";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public String addUserToBudget(@AuthenticationPrincipal User user, @RequestParam("user-email") String userEmail) {
        Optional<User> newUser = userService.findUserByUsername(userEmail);
        if (!newUser.isPresent()) {
            return "redirect:/add-user?error=user";
        } else if (newUser.get().getBudget()!= null) {
            return "redirect:/add-user?error=budget";
        } else {
            Budget budget = user.getBudget();
            budget.addUser(newUser.get());
            budgetService.edit(budget);
            return "redirect:/add-user?success";
        }
    }

    public BudgetUserController(UserService userService, BudgetService budgetService) {
        this.userService = userService;
        this.budgetService = budgetService;
    }
}
