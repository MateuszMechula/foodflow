package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;
import pl.foodflow.integration.configuration.PersistenceContainerTestConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.foodflow.util.TestDataFactory.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=OwnerJpaRepositoryTest")
class OwnerJpaRepositoryTest {

    private UserJpaRepository userJpaRepository;
    private OwnerJpaRepository ownerJpaRepository;

    @Test
    void shouldFindOwnerByUserId() {
        //given
        userJpaRepository.saveAndFlush(someUser4());
        OwnerEntity owner = someOwner4();

        ownerJpaRepository.saveAndFlush(owner);
        //when
        Optional<OwnerEntity> ownerFound = ownerJpaRepository.findOwnerEntityByUserId(someUser4().getUserId());
        //then
        assertThat(ownerFound).isPresent();
    }

    @Test
    void shouldFindAllOwners() {

        //given
        var users = List.of(someUser1(), someUser2(), someUser3());
        userJpaRepository.saveAllAndFlush(users);
        var owners = List.of(someOwner1(), someOwner2(), someOwner3());
        ownerJpaRepository.saveAllAndFlush(owners);
        //when
        List<OwnerEntity> expected = ownerJpaRepository.findAll();
        //then
        assertThat(expected).hasSize(3);
    }
}