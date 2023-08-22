package pl.foodflow.infrastructure.security;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUserName(String userName);

}
