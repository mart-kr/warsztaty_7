package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.repository.UserRepository;
import pl.coderslab.warsztaty_7.service.SecurityService;
import pl.coderslab.warsztaty_7.service.impl.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Controller
public class TestController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserServiceImpl userServiceImpl;

//    @GetMapping("/home")
//    public String hello() {
//        return "test";
//    }

    @GetMapping("/remove")
    public String removeBudget(@AuthenticationPrincipal User user) {
        user.setBudget(null);
        User userInstance = user;
        repository.save(userInstance);
        return "/home";
    }

    @GetMapping("/add-admin")
    @ResponseBody
    public String addAdmin() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        Set<Role> roles1 = new HashSet<>();
        roles1.add(new Role("USER"));
        repository.save(new User("user1@email.com","user1name",encoder.encode("123456789"), true, true,true,true, roles1));
        repository.save(new User("admin@email.com", "admin1name", encoder.encode("123456789"), true, true, true, true, roles));
        return "added default admin and user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/rolecheck")
    @ResponseBody
    public String roleCheck() {
        return "You have got admin role (annotation check)";
    }

    @GetMapping("/admin/")
    @ResponseBody
    public String roleCheck2() {
        return "You have got admin role (configuration check)";
    }

    // pozwala na wywołanie metody tylko jeśli zalogowany użytkownik
    // posiada którąkolwiek z ról (admin bądź user)
    // TO WSZYSTKO PEWNIE WYWALI 10 BŁĘDÓW WIĘC RACZEJ NIE TESTUJCIE :D
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/security-example")
    // pobiera z securityContextHolder obiekt authentication, na nim wywołuje metode getPrincipal
    // principal jest castowany na naszego User'a
    public String securityExample(@AuthenticationPrincipal User user) {
        // to tylko dla przyklady jak bedzie wygladać sprawdzanie czy user moze edytowac encję
        IncomeCategory incomeCategory = new IncomeCategory();
        if (securityService.canViewOrEditEntity(user, incomeCategory)) {
            // wywolanie jakiegoś serwisu itp itd
            // np. incomeCategoryService.edit(entity);
            return "redirect:/home";
        } else {
            // jesli user nie ma prawa do edycji to rzucamy błąd
            // AccesDeniedException ustawia status http na 403 czyli Forbidden
            // Błąd jest automatycznie obslugiwany przez springa ktory odsyla do
            // naszej strony błędu /error.html
            throw new AccessDeniedException("User is not allowed to edit this entity");
        }
    }
}
