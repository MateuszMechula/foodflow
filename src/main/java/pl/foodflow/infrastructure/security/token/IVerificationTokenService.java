package pl.foodflow.infrastructure.security.token;

import pl.foodflow.infrastructure.security.user.User;

import java.util.Optional;

public interface IVerificationTokenService {

    String ValidateToken(String token);

    void saveVerificationTokenForUser(User user, String token);

    Optional<VerificationToken> findByToken(String token);
}
