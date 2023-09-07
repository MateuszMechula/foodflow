package pl.foodflow.infrastructure.security.user;

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
        return userEntityJpaRepository.findByUsername(userName)
                .map(userEntityMapper::mapFromEntity);
    }

    @Override
    public User save(User user) {
        UserEntity toSave = userEntityMapper.mapToEntity(user);
        UserEntity saved = userEntityJpaRepository.save(toSave);
        return userEntityMapper.mapFromEntity(saved);
    }
}
