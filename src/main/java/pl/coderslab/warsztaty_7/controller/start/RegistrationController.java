package pl.coderslab.warsztaty_7.controller.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.model.VerificationToken;
import pl.coderslab.warsztaty_7.service.impl.NewUserService;
import pl.coderslab.warsztaty_7.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(path = "/start/register")
public class RegistrationController {

    private final NewUserService newUserService;
    private final UserServiceImpl userServiceImpl;

    @ModelAttribute(name = "user")
    public User newUser() {
        return new User();
    }

    @GetMapping
    public String registerPage() {
        return "regForm";
    }

    @PostMapping
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

            //TODO alert o potwierdzeniu maila w widoku logowania
            return "redirect:/start/login?confirm";
        }
    }

    @GetMapping(path = "/confirm", params = "token")
    public String confirmUserEmail(@RequestParam(name = "token") final String tokenString, RedirectAttributes model) {
        Optional<VerificationToken> verificationToken = newUserService.findVerificationTokenByTokenString(tokenString);
        if (!verificationToken.isPresent()) {
            // TODO widok potwierdzenia maila z alertami
            return "redirect:/start/register/resend/token?error";
        } else if (verificationToken.get().isExpired()) {
            model.addFlashAttribute("token", tokenString);
            return "redirect:/start/register/resend/token?expired";
        } else {
            newUserService.confirmNewUser(tokenString);
            return "redirect:/start/login?register";
        }
    }

    @GetMapping(path = "/resend/token")
    public String confirmUserEmailError(@ModelAttribute(name = "token") String token, Model model) {
        model.addAttribute("token", token);
        return "tokenExpired";
    }

    @GetMapping(path = "/resend", params = "token")
    public String resendVerificationLinkForToken(@RequestParam(name = "token") final String expiredToken) {
        newUserService.resendConfirmationLinkForToken(expiredToken);
        return "redirect:/start/login?confirm";
    }

    @PostMapping(path = "/resend")
    public String resendVerificationLinkForEmail(@RequestParam(name = "email") final String email) {
        newUserService.resendConfirmationLinkForEmail(email);
        return "redirect:/start/login?confirm";
    }

    @Autowired
    public RegistrationController(NewUserService newUserService, UserServiceImpl userServiceImpl) {
        this.newUserService = newUserService;
        this.userServiceImpl = userServiceImpl;
    }
}
