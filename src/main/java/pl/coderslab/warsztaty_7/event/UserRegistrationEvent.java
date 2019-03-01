package pl.coderslab.warsztaty_7.event;

import org.springframework.context.ApplicationEvent;
import pl.coderslab.warsztaty_7.model.User;

public class UserRegistrationEvent extends ApplicationEvent {

    private final String token;
    private final String email;
    public static final String SUBJECT = "Potwierdź rejestrację konta w BudgetApp";
    public static final String MAIL_MESSAGE_TEXT = "Aby potwierdzić rejestrację konta w BudgetApp kliknij w link: \n";

    public UserRegistrationEvent(User user, String token) {
        super(user);
        this.token = token;
        this.email = user.getUsername();
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}
