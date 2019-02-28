package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Role;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.model.VerificationToken;
import pl.coderslab.warsztaty_7.service.VerificationTokenService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Transactional
public class NewUserService {

    private static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userServiceImpl;
    private final RoleService roleService;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public NewUserService(PasswordEncoder passwordEncoder, UserServiceImpl userServiceImpl,
                          RoleService roleService, VerificationTokenService verificationTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;
        this.verificationTokenService = verificationTokenService;
    }

    public boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEXP);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void saveNewUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName("USER"));
        // TODO zmiana Enabled na false po wprowadzeniu weryfikacji email
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        verificationTokenService.generateAndSaveNewTokenForUser(user);
    }

    public void confirmNewUser(String tokenString) {
        Optional<VerificationToken> verificationToken = verificationTokenService.findVerificationTokenByTokenString(tokenString);
        if (verificationToken.isPresent()) {
            String username = verificationToken.get().getUser().getUsername();
            Optional<User> user = userServiceImpl.findUserByUsername(username);
            if (user.isPresent()) {
                user.get().setEnabled(true);
                userServiceImpl.saveUser(user.get());
            }
        }
    }

}
