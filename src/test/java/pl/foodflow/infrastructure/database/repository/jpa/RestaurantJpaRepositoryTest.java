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
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;
import pl.foodflow.integration.configuration.PersistenceContainerTestConfiguration;

import java.util.Optional;

import static pl.foodflow.util.TestDataFactory.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=RestaurantJpaRepositoryTest")
class RestaurantJpaRepositoryTest {

    private UserJpaRepository userJpaRepository;
    private OwnerJpaRepository ownerJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;

    @Test
    void shouldFindRestaurantByNip() {
        //given
        RestaurantEntity restaurant = someRestaurant1();
        userJpaRepository.saveAndFlush(someUser1());
        ownerJpaRepository.saveAndFlush(someOwner1());
        restaurantJpaRepository.saveAndFlush(restaurant);
        //when
        Optional<RestaurantEntity> foundNipRestaurant = restaurantJpaRepository.findByNip(restaurant.getNip());
        //then
        Assertions.assertThat(foundNipRestaurant).isPresent();
    }
}