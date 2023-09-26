package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;

import java.util.Optional;

import static pl.foodflow.util.TestDataFactory.*;


@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=RestaurantAddressJpaRepositoryTest")
class RestaurantAddressJpaRepositoryTest extends AbstractJpa {

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