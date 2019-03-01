package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.event.UserRegistrationEventPublisher;
import pl.coderslab.warsztaty_7.model.Role;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.model.VerificationToken;
import pl.coderslab.warsztaty_7.service.VerificationTokenService;

import java.util.Comparator;
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
    private final UserRegistrationEventPublisher registrationEventPublisher;

    @Autowired
    public NewUserService(PasswordEncoder passwordEncoder, UserServiceImpl userServiceImpl,
                          RoleService roleService, VerificationTokenService verificationTokenService,
                          UserRegistrationEventPublisher registrationEventPublisher) {
        this.passwordEncoder = passwordEncoder;
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;
        this.verificationTokenService = verificationTokenService;
        this.registrationEventPublisher = registrationEventPublisher;
    }

    public boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEXP);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void saveNewUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName("USER"));
        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        verificationTokenService.generateAndSaveNewTokenForUser(user);
        registrationEventPublisher.publishRegistrationEvent(user, getUsersLastTokenString(user));
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

    public Optional<VerificationToken> findVerificationTokenByTokenString(String token) {
        return verificationTokenService.findVerificationTokenByTokenString(token);
    }

    public void resendConfirmationLinkForToken(String expiredTokenString) {
        Optional<VerificationToken> expiredVerificationToken =
                verificationTokenService.findVerificationTokenByTokenString(expiredTokenString);
        if (expiredVerificationToken.isPresent()){
            User user = expiredVerificationToken.get().getUser();
            verificationTokenService.invalidateTokens(user);
            verificationTokenService.generateAndSaveNewTokenForUser(user);
            registrationEventPublisher.publishRegistrationEvent(user, getUsersLastTokenString(user));
        }
    }

    public void resendConfirmationLinkForEmail(String email) {
        Optional<User> optUser = userServiceImpl.findUserByUsername(email);
        if (optUser.isPresent() && !optUser.get().isEnabled()) {
            User user = optUser.get();
            verificationTokenService.invalidateTokens(user);
            verificationTokenService.generateAndSaveNewTokenForUser(user);
            registrationEventPublisher.publishRegistrationEvent(user, getUsersLastTokenString(user));
        }
    }

    private String getUsersLastTokenString(User user) {
        Optional<VerificationToken> optToken = verificationTokenService.findAllVerificationTokensByUser(user).stream()
                .max(Comparator.comparing(VerificationToken::getExpireTime));
        return optToken.isPresent()? optToken.get().getToken() : "";
    }

}
