package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.foodflow.util.TestDataFactory.*;


@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=OwnerJpaRepositoryTest")
class OwnerJpaRepositoryTest extends AbstractJpa {

    private UserJpaRepository userJpaRepository;
    private OwnerJpaRepository ownerJpaRepository;

    @Test
    void shouldFindOwnerByUserId() {
        //given
        userJpaRepository.saveAndFlush(someUserEntity4());
        OwnerEntity owner = someOwnerEntity4();

        ownerJpaRepository.saveAndFlush(owner);
        //when
        Optional<OwnerEntity> ownerFound = ownerJpaRepository.findOwnerEntityByUserId(someUserEntity4().getUserId());
        //then
        assertThat(ownerFound).isPresent();
    }

    @Test
    void shouldFindAllOwners() {

        //given
        var users = List.of(someUserEntity1(), someUserEntity2(), someUserEntity3());
        userJpaRepository.saveAllAndFlush(users);
        var owners = List.of(someOwnerEntity1(), someOwnerEntity2(), someOwnerEntity3());
        ownerJpaRepository.saveAllAndFlush(owners);
        //when
        List<OwnerEntity> expected = ownerJpaRepository.findAll();
        //then
        assertThat(expected).hasSize(3);
    }
}