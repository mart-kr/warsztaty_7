package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.event.UserRegistrationEvent;

public interface MailService {

    void sendConfirmationLink(String email, String subject, String text);
}