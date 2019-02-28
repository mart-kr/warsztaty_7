package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.service.MailService;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendConfirmationLink(String email, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
//        System.out.println("Wyslano maila z rejestracji  "+System.currentTimeMillis());
    }

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
