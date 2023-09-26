package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;

import java.util.Optional;

import static pl.foodflow.util.TestDataFactory.*;


@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=RestaurantJpaRepositoryTest")
class RestaurantJpaRepositoryTest extends AbstractJpa {

    private UserJpaRepository userJpaRepository;
    private OwnerJpaRepository ownerJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;

    @Test
    void shouldFindRestaurantByNip() {
        //given
        RestaurantEntity restaurant = someRestaurantEntity1();
        userJpaRepository.saveAndFlush(someUserEntity1());
        ownerJpaRepository.saveAndFlush(someOwnerEntity1());
        restaurantJpaRepository.saveAndFlush(restaurant);
        //when
        Optional<RestaurantEntity> foundNipRestaurant = restaurantJpaRepository.findByNip(restaurant.getNip());
        //then
        Assertions.assertThat(foundNipRestaurant).isPresent();
    }
}