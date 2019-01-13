package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/home/budget")
public class TestBudgetUserController {

    private UserService userService;

    @GetMapping("/add-user")
    public String addNewUserToBudgetForm() {
        return "add-budget-user";
    }

    @PostMapping("/add-user")
    @ResponseBody
    public String addNewUserToBudget(@AuthenticationPrincipal User user, @RequestParam("new-user") String email) {
        Optional<User> optionalUser = userService.findUserByUsername(email);
        if (optionalUser.isPresent()) {
            User newUser = optionalUser.get();
            //prawdopodobnie tak to będzie wyglądać:
            //newUser.setBudget(user.getBudget);
            return "User has been added to your budget!";
        } else {
            return "User not found! Please check provided email adress";
        }
    }

    @Autowired
    public TestBudgetUserController(UserService userService) {
        this.userService = userService;
    }
}
