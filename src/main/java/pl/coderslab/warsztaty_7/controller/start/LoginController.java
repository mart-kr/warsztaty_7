package pl.coderslab.warsztaty_7.controller.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.NewUserService;
import pl.coderslab.warsztaty_7.service.SecurityService;
import pl.coderslab.warsztaty_7.service.UserService;

import javax.validation.Valid;


@Controller
public class LoginController {

    private final UserService userService;
    private final NewUserService newUserService;

    @ModelAttribute(name = "user")
    public User newUser() {
        return new User();
    }

    @GetMapping(value = "/")
    public String redirectStartPage() {
        return "redirect:/start";
    }

    @GetMapping(value = "/start")
    public String startPage() {
        return "start";
    }

    @GetMapping(value = "/start/login")
    public String loginPage() {
        return "login-form";
    }

    @GetMapping(value = "/start/register")
    public String registerPage() {
        return "reg-form";
    }

    @PostMapping("/start/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (!newUserService.isPasswordValid(user.getPassword())) {
            bindingResult.rejectValue("password", "password.error", "Password length must be between 8-20 characters");
        }
        if (userService.findUserByUsername(user.getUsername()).isPresent()) {
            bindingResult.rejectValue("username","username.error", "This email is already registered");
        }
        if (bindingResult.hasErrors()) {
            return "reg-form";
        } else {
            newUserService.saveNewUser(user);
            return "redirect:/start/login?register";
        }
    }

    @Autowired
    public LoginController(UserService userService, NewUserService newUserService) {
        this.userService = userService;
        this.newUserService = newUserService;
    }
}
