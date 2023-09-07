package pl.foodflow.infrastructure.security.user;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUserName(String userName);

    User save(User user);
}
