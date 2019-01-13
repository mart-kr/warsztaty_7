package pl.coderslab.warsztaty_7.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.coderslab.warsztaty_7.model.User;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {
    @Override
    public Long getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UsernameNotFoundException("There is no such user");
        } else {
            User user = (User) auth.getPrincipal();
            return user.getId();
        }
    }
}
