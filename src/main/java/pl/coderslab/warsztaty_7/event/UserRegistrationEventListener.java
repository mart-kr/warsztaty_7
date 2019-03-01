package pl.coderslab.warsztaty_7.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.coderslab.warsztaty_7.service.MailService;

@Component
@Async
public class UserRegistrationEventListener implements ApplicationListener<UserRegistrationEvent> {

    private MailService mailService;

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        String text = UserRegistrationEvent.MAIL_MESSAGE_TEXT +
                "http://localhost:8080/start/register/confirm?token="+
                event.getToken();
        mailService.sendConfirmationLink(event.getEmail(), UserRegistrationEvent.SUBJECT, text);
    }

    @Autowired
    public UserRegistrationEventListener(MailService mailService) {
        this.mailService = mailService;
    }
}
