package pl.foodflow.infrastructure.security.user;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.repository.jpa.AbstractJpa;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=UserJpaRepositoryTest")
public class UserJpaRepositoryTest extends AbstractJpa {

    private UserJpaRepository userJpaRepository;

    @Test
    void shouldFindUserByUsername() {
        Optional<UserEntity> foundUser = userJpaRepository.findUserEntityByUserName("test_owner");
        assertThat(foundUser).isPresent();
    }
}
