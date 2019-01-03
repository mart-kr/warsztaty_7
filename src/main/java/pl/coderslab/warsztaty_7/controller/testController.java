package pl.coderslab.warsztaty_7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.warsztaty_7.model.Role;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
public class testController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/home")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/add-admin")
    public String addAdmin() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        Set<Role> roles1 = new HashSet<>();
        roles1.add(new Role("USER"));
        repository.save(new User("user1", encoder.encode("user1"), true, true,true,true, roles1));
        repository.save(new User("admin", encoder.encode("admin"), true, true, true, true, roles));
        return "added default admin and user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/rolecheck")
    public String roleCheck() {
        return "You have got admin role (annotation check)";
    }

    @GetMapping("/admin/")
    public String roleCheck2() {
        return "You have got admin role (configuration check)";
    }
}
