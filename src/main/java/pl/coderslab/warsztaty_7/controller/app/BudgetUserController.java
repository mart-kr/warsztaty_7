package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.BudgetService;
import pl.coderslab.warsztaty_7.service.impl.UserServiceImpl;

import java.util.Optional;

@Controller
@RequestMapping("/home/budget/user")
public class BudgetUserController {

    private final UserServiceImpl userServiceImpl;
    private final BudgetService budgetService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/add")
    public String addUserToBudgetForm(@AuthenticationPrincipal final User user) {
        if (user.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else {
            return "addBudgetUser";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public String addUserToBudget(@AuthenticationPrincipal final User currentUser, @RequestParam("user-email") final String userEmail) {
        Optional<User> newUser = userServiceImpl.findUserByUsername(userEmail);
        if (currentUser.getBudget() == null) {
            return "redirect:/home/budget/add";
        } else if (!newUser.isPresent()) {
            return "redirect:/home/budget/user/add?error=user";
        } else if (newUser.get().getBudget()!= null) {
            return "redirect:/home/budget/user/add?error=budget";
        } else {
            currentUser.getBudget().addUser(newUser.get());
            budgetService.edit(currentUser.getBudget());
            return "redirect:/home/budget/user/add?success";
        }
    }

    @Autowired
    public BudgetUserController(UserServiceImpl userServiceImpl, BudgetService budgetService) {
        this.userServiceImpl = userServiceImpl;
        this.budgetService = budgetService;
    }
}
