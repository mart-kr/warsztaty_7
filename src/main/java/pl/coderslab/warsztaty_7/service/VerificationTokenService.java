package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.model.VerificationToken;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface VerificationTokenService {

    Optional<VerificationToken> findVerificationTokenByTokenString(String tokenString);
    List<VerificationToken> findAllVerificationTokensByUser(User user);
    void generateAndSaveNewTokenForUser(User user);
    void invalidateTokens(User user);
}
