package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.model.VerificationToken;

import java.util.List;
import java.util.Optional;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findOneByToken(String token);
    List<VerificationToken> findAllByUser(User user);
}
