package pl.coderslab.warsztaty_7.config;

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
        if (auth.getPrincipal() instanceof User && auth.getPrincipal() != null) {
            User currentUser = (User) auth.getPrincipal();
            return currentUser.getId();
        } else {
            throw new UsernameNotFoundException("You must be logged in to access this resources");
        }
    }
}
