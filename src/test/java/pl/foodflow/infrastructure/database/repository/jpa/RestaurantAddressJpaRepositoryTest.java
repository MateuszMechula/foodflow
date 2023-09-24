package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;
import pl.foodflow.integration.configuration.PersistenceContainerTestConfiguration;

import java.util.Optional;

import static pl.foodflow.util.TestDataFactory.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=RestaurantAddressJpaRepositoryTest")
class RestaurantAddressJpaRepositoryTest {

    private RestaurantAddressJpaRepository restaurantAddressJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private OwnerJpaRepository ownerJpaRepository;
    private UserJpaRepository userJpaRepository;

    @Test
    void shouldFindRestaurantAddressEntityByAddressId() {
        //given
        RestaurantAddressEntity restaurantAddress = someRestaurantAddressEntity1();
        Long addressId = restaurantAddress.getAddress().getAddressId();

        userJpaRepository.saveAndFlush(someUserEntity1());
        ownerJpaRepository.saveAndFlush(someOwnerEntity1());
        restaurantJpaRepository.saveAndFlush(someRestaurantEntity1());
        restaurantAddressJpaRepository.saveAndFlush(restaurantAddress);
        //when
        Optional<RestaurantAddressEntity> expected = restaurantAddressJpaRepository
                .findRestaurantAddressEntityByAddressAddressId(addressId);
        //then
        Assertions.assertThat(expected).isPresent();
    }
}