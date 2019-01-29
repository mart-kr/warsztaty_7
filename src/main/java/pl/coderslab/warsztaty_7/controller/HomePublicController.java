package pl.coderslab.warsztaty_7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.NewUserService;
import pl.coderslab.warsztaty_7.service.UserServiceImpl;

import javax.validation.Valid;


// Controller do zasobów dostępnych dla użytkowników bez autoryzacji
// Będzie w nim strona główna, strona rejestracji, strona logowania itp.

@Controller
@RequestMapping("/home")
public class HomePublicController {

    private NewUserService newUserService;

    private UserServiceImpl userServiceImpl;

    @ModelAttribute(name = "user")
    public User user() {
        return new User();
    }

    @GetMapping("/registration")
    public String registrationForm() {
        return "reg-form";
    }

    @PostMapping("/registration")
    public String registerNewUser(@Valid User user, BindingResult bindingResult) {

        // walidacja dlugości hasła jest tutaj
        // ponieważ dodanie do encji max rozmiaru 20 nie pozwala na zapisanie hashu hasła
        // docelowo wypada zrobić oddzielny walidator dla hasła z dużymi znakami cyframi itp.
        if(user.getPassword().length()<8 || user.getPassword().length()>20) {
            bindingResult.rejectValue("password", "password.error", "Password length must be between 8-20 characters");
        }
        if (userServiceImpl.findUserByUsername(user.getUsername()).isPresent()) {
            bindingResult.rejectValue("username","username.error", "This email is already registered");
        }
        if (bindingResult.hasErrors()) {
            return "reg-form";
        } else {
            // metoda nadaje nowemu uzytkownikowi odpowiednie wartosci pól (póki co wszystko na true)
            newUserService.saveNewUser(user);
            return "redirect:/home";
        }
    }

    @Autowired
    public HomePublicController(UserServiceImpl userServiceImpl, NewUserService newUserService) {
        this.userServiceImpl = userServiceImpl;
        this.newUserService = newUserService;
    }
}
