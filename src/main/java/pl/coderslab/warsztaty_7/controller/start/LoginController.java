package pl.coderslab.warsztaty_7.controller.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.NewUserService;
import pl.coderslab.warsztaty_7.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
public class LoginController {

    private final UserServiceImpl userServiceImpl;
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
    public String startPage(@AuthenticationPrincipal User user) {
        if (user != null) {
            return "redirect:/home";
        }
        return "start";
    }

    @GetMapping(value = "/start/login")
    public String loginPage() {
        return "loginForm";
    }

    @GetMapping(value = "/start/register")
    public String registerPage() {
        return "regForm";
    }

    @PostMapping("/start/register")
    public String registerNewUser(@Valid final User user, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal final User authUser) {
        if (authUser != null) {
            return "redirect:/home";
        }
        if (!newUserService.isPasswordValid(user.getPassword())) {
            bindingResult.rejectValue("password", "password.error", "Password length must be between 8-20 characters");
        }
        if (!user.getPassword().equals(user.getConfirm())) {
            bindingResult.rejectValue("confirm", "confirm.error", "Passwords must match");
        }
        if (userServiceImpl.findUserByUsername(user.getUsername()).isPresent()) {
            bindingResult.rejectValue("username","username.error", "This email is already registered");
        }
        if (bindingResult.hasErrors()) {
            return "regForm";
        } else {
            newUserService.saveNewUser(user);
            return "redirect:/start/login?register";
        }
    }

    @Autowired
    public LoginController(UserServiceImpl userServiceImpl, NewUserService newUserService) {
        this.userServiceImpl = userServiceImpl;
        this.newUserService = newUserService;
    }
}
