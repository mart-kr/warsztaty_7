package pl.coderslab.warsztaty_7.controller.start;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.warsztaty_7.model.User;


@Controller
public class LoginController {

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
}
