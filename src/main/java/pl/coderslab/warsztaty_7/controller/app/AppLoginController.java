package pl.coderslab.warsztaty_7.controller.app;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppLoginController {

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

}
