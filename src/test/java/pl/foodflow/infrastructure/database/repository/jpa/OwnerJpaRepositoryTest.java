package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=OwnerJpaRepositoryTest")
class OwnerJpaRepositoryTest extends AbstractJpa {

    private OwnerJpaRepository ownerJpaRepository;

    @Test
    void shouldFindOwnerByUserId() {
        //given
        Integer userId = 1;
        //when
        Optional<OwnerEntity> ownerFound = ownerJpaRepository.findOwnerEntityByUserId(userId);
        //then
        assertThat(ownerFound).isPresent();
    }

    @Test
    void shouldFindAllOwners() {
        //when
        List<OwnerEntity> expected = ownerJpaRepository.findAll();
        //then
        assertThat(expected).hasSize(1);
    }
}