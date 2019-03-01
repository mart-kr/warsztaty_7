package pl.coderslab.warsztaty_7.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import pl.coderslab.warsztaty_7.model.User;

@Component
public class UserRegistrationEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void publishRegistrationEvent(User user, String token) {
//        System.out.println("Event publish start  "+System.currentTimeMillis());
        eventPublisher.publishEvent(new UserRegistrationEvent(user, token));
//        System.out.println("Event publish stop  "+System.currentTimeMillis());
    }
}
