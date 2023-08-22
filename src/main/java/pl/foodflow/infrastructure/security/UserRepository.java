package pl.foodflow.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserEntityJpaRepository userEntityJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findByUserName(String userName) {
        return userEntityJpaRepository.findByUserName(userName)
                .map(userEntityMapper::map);
    }
}
