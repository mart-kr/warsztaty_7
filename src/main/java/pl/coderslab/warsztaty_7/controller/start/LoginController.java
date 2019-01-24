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
import pl.coderslab.warsztaty_7.service.UserService;

import javax.validation.Valid;


@Controller
public class LoginController {

    private UserService userService;
    private NewUserService newUserService;

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
        // TODO: Walidator hasła
        // walidacja dlugości hasła jest tutaj
        // ponieważ dodanie do encji max rozmiaru 20 nie pozwala na zapisanie hashu hasła
        // docelowo wypada zrobić oddzielny walidator dla hasła z dużymi znakami cyframi itp.
        if (user.getPassword().length()<8 || user.getPassword().length()>20) {
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
