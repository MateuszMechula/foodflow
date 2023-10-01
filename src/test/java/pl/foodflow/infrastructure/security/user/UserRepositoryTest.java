package pl.foodflow.infrastructure.security.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someUser1;
import static pl.foodflow.util.TestDataFactory.someUserEntity5;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @InjectMocks
    private UserRepository userRepository;
    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private UserEntityMapper userEntityMapper;

    @Test
    void shouldFindByUserName() {
        //given
        String username = "testUser";
        User user = someUser1();
        UserEntity userEntity = someUserEntity5();

        when(userJpaRepository.findUserEntityByUserName(username)).thenReturn(Optional.ofNullable(userEntity));
        when(userEntityMapper.mapFromEntity(Objects.requireNonNull(userEntity))).thenReturn(user);
        //when
        Optional<User> foundUser = userRepository.findByUserName(username);
        //then
        assertThat(foundUser)
                .isPresent()
                .isEqualTo(Optional.of(user));
        verify(userJpaRepository).findUserEntityByUserName(username);
        verify(userEntityMapper).mapFromEntity(userEntity);
    }

    @Test
    void shouldSaveUser() {
        //given
        User user = someUser1();
        UserEntity userEntity = someUserEntity5();

        when(userEntityMapper.mapToEntity(user)).thenReturn(userEntity);
        when(userJpaRepository.save(userEntity)).thenReturn(userEntity);
        when(userEntityMapper.mapFromEntity(userEntity)).thenReturn(user);
        //when
        User savedUser = userRepository.save(user);
        //then
        assertThat(savedUser)
                .isNotNull()
                .isEqualTo(user);

        verify(userEntityMapper).mapToEntity(user);
        verify(userJpaRepository).save(userEntity);
        verify(userEntityMapper).mapFromEntity(userEntity);
    }
}