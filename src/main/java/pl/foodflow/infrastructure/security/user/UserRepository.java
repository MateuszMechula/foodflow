package pl.foodflow.infrastructure.security.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findUserById(int userId) {
        return userJpaRepository.findById(userId)
                .map(userEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userJpaRepository.findUserEntityByUserName(userName)
                .map(userEntityMapper::mapFromEntity);
    }

    @Override
    public User save(User user) {
        UserEntity toSave = userEntityMapper.mapToEntity(user);
        UserEntity saved = userJpaRepository.save(toSave);
        return userEntityMapper.mapFromEntity(saved);
    }
}
