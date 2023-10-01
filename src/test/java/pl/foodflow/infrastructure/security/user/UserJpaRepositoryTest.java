package pl.foodflow.infrastructure.security.user;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.repository.jpa.AbstractJpa;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.foodflow.util.TestDataFactory.someUserDTO2;
import static pl.foodflow.util.TestDataFactory.someUserEntity5;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=UserJpaRepositoryTest")
public class UserJpaRepositoryTest extends AbstractJpa {

    private UserJpaRepository userJpaRepository;

    @Test
    void shouldFindUserByUsername() {
        //given
        someUserDTO2();
        UserEntity userEntity = someUserEntity5();
        String username = someUserEntity5().getUserName();
        userJpaRepository.saveAndFlush(userEntity);
        //when
        Optional<UserEntity> foundUser = userJpaRepository.findUserEntityByUserName(username);
        //then
        assertThat(foundUser).isPresent();
    }
}
