package pl.coderslab.warsztaty_7.controller.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.warsztaty_7.event.UserRegistrationEvent;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.model.VerificationToken;
import pl.coderslab.warsztaty_7.service.VerificationTokenService;
import pl.coderslab.warsztaty_7.service.impl.NewUserService;
import pl.coderslab.warsztaty_7.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(path = "/start/register")
public class RegistrationController {

    private final NewUserService newUserService;
    private final UserServiceImpl userServiceImpl;
    private final VerificationTokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;

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
    public String confirmUserEmail(@RequestParam(name = "token") final String tokenString) {
        Optional<VerificationToken> verificationToken = tokenService.findVerificationTokenByTokenString(tokenString);
        if (!verificationToken.isPresent()) {
            // TODO widok potwierdzenia maila z alertami
            return "redirect:/start/register/confirm?error";
        } else if (verificationToken.get().isExpired()) {
            return "redirect:/start/register/confirm?expired";
        } else {
            newUserService.confirmNewUser(tokenString);
            return "redirect:/start/login?register";
        }
    }

    @Autowired
    public RegistrationController(NewUserService newUserService, UserServiceImpl userServiceImpl,
                                  VerificationTokenService tokenService, ApplicationEventPublisher eventPublisher) {
        this.newUserService = newUserService;
        this.userServiceImpl = userServiceImpl;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
    }
}
