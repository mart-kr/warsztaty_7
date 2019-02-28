package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.model.VerificationToken;
import pl.coderslab.warsztaty_7.repository.VerificationTokenRepository;
import pl.coderslab.warsztaty_7.service.VerificationTokenService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenServiceJpaImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public Optional<VerificationToken> findVerificationTokenByTokenString(String tokenString) {
        return verificationTokenRepository.findOneByToken(tokenString);
    }

    @Override
    public void generateAndSaveNewTokenForUser(User user) {
        String tokenString = UUID.randomUUID().toString();
        LocalDateTime expireTime = LocalDateTime.now().plusHours(24);
        VerificationToken verificationToken = new VerificationToken(tokenString, user, expireTime);
        verificationTokenRepository.save(verificationToken);
    }

    @Autowired
    public VerificationTokenServiceJpaImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }
}
