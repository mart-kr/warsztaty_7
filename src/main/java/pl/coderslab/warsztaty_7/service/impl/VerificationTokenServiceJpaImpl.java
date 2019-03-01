package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.model.VerificationToken;
import pl.coderslab.warsztaty_7.repository.VerificationTokenRepository;
import pl.coderslab.warsztaty_7.service.VerificationTokenService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenServiceJpaImpl implements VerificationTokenService {

    private static final int TOKEN_EXPIRATION_HOURS = 24;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public Optional<VerificationToken> findVerificationTokenByTokenString(String tokenString) {
        return verificationTokenRepository.findOneByToken(tokenString);
    }

    @Override
    public List<VerificationToken> findAllVerificationTokensByUser(User user) {
        return verificationTokenRepository.findAllByUser(user);
    }

    @Override
    public void generateAndSaveNewTokenForUser(User user) {
        String tokenString = UUID.randomUUID().toString();
        LocalDateTime expireTime = LocalDateTime.now().plusHours(TOKEN_EXPIRATION_HOURS);
        VerificationToken verificationToken = new VerificationToken(tokenString, user, expireTime);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void invalidateTokens(User user) {
        List<VerificationToken> allTokens = findAllVerificationTokensByUser(user);
        for (VerificationToken token : allTokens) {
            token.setExpireTime(LocalDateTime.now().minusYears(1000));
        }
        verificationTokenRepository.save(allTokens);
    }

    @Autowired
    public VerificationTokenServiceJpaImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }
}
