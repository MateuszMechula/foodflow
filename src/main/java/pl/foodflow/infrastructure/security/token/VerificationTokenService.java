package pl.foodflow.infrastructure.security.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.infrastructure.security.user.User;
import pl.foodflow.infrastructure.security.user.UserDAO;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationTokenService implements IVerificationTokenService {

    private final VerificationTokenRepository tokenRepository;
    private final UserDAO userDAO;

    @Override
    public String ValidateToken(String token) {
        Optional<VerificationToken> theToken = tokenRepository.findByToken(token);
        if (theToken.isEmpty()) {
            return "Invalid verification token";
        }
        return "";
//        User user = theToken.get().getUser();
//        Calendar calendar = Calendar.getInstance();
//        if (theToken.get().getExpirationTime()
//                .getTime() - calendar.getTime().getTime() <= 0) {
//            return "expired";
//        }
//        user.setActive(true);
//        userDAO.save(user);
//        return "valid";
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
//        var verificationToken = new VerificationToken(token, user);
//        tokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
