package pl.foodflow.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;
import pl.foodflow.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.RestaurantEntityMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someRestaurant1;
import static pl.foodflow.util.TestDataFactory.someRestaurantEntity1;

@ExtendWith(MockitoExtension.class)
class RestaurantRepositoryTest {
    @InjectMocks
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantJpaRepository restaurantJpaRepository;
    @Mock
    private RestaurantEntityMapper restaurantEntityMapper;

    @Test
    void shouldFindRestaurantById() {
        //given
        Long restaurantId = 1L;
        Restaurant restaurant = someRestaurant1();
        RestaurantEntity restaurantEntity = someRestaurantEntity1();

        when(restaurantJpaRepository.findById(restaurantId)).thenReturn(Optional.ofNullable(restaurantEntity));
        when(restaurantEntityMapper.mapFromEntity(restaurantEntity)).thenReturn(restaurant);
        //when
        Optional<Restaurant> foundRestaurant = restaurantRepository.findRestaurantById(restaurantId);
        //then
        assertThat(foundRestaurant)
                .isPresent()
                .isEqualTo(Optional.of(restaurant));

        verify(restaurantJpaRepository).findById(restaurantId);
        verify(restaurantEntityMapper).mapFromEntity(restaurantEntity);
    }

    @Test
    void shouldFindRestaurantByNip() {
        String restaurantNip = "7234054403";
        Restaurant restaurant = someRestaurant1();
        RestaurantEntity restaurantEntity = someRestaurantEntity1();

        when(restaurantJpaRepository.findByNip(restaurantNip)).thenReturn(Optional.ofNullable(restaurantEntity));
        when(restaurantEntityMapper.mapFromEntity(restaurantEntity)).thenReturn(restaurant);
        //when
        Optional<Restaurant> foundRestaurant = restaurantRepository.findRestaurantByNip(restaurantNip);
        //then
        assertThat(foundRestaurant)
                .isPresent()
                .isEqualTo(Optional.of(restaurant));

        verify(restaurantJpaRepository).findByNip(restaurantNip);
        verify(restaurantEntityMapper).mapFromEntity(restaurantEntity);
    }

    @Test
    void shouldFindAllRestaurants() {
        //given
        var restaurantEntities = List.of(someRestaurantEntity1());
        var expectedRestaurants = List.of(someRestaurant1());


        when(restaurantJpaRepository.findAll()).thenReturn(restaurantEntities);
        when(restaurantEntityMapper.mapFromEntity(any())).thenAnswer(invocation -> {
            RestaurantEntity entity = invocation.getArgument(0);
            int index = restaurantEntities.indexOf(entity);
            return expectedRestaurants.get(index);
        });
        //when
        List<Restaurant> allRestaurants = restaurantRepository.findAllRestaurants();
        //then
        assertThat(allRestaurants)
                .isNotNull()
                .hasSize(expectedRestaurants.size());

        verify(restaurantJpaRepository).findAll();
        verify(restaurantEntityMapper).mapFromEntity(any());
    }

    @Test
    void shouldSaveRestaurant() {
        //given
        Restaurant restaurant = someRestaurant1();
        RestaurantEntity restaurantEntity = someRestaurantEntity1();

        when(restaurantEntityMapper.mapToEntity(restaurant)).thenReturn(restaurantEntity);
        when(restaurantJpaRepository.save(restaurantEntity)).thenReturn(restaurantEntity);
        when(restaurantEntityMapper.mapFromEntity(restaurantEntity)).thenReturn(restaurant);
        //when
        restaurantRepository.saveRestaurant(restaurant);
        //then
        verify(restaurantEntityMapper).mapToEntity(restaurant);
        verify(restaurantJpaRepository).save(restaurantEntity);
        verify(restaurantEntityMapper).mapFromEntity(restaurantEntity);
    }

    @Test
    void shouldDeleteRestaurantById() {
        //given
        Long restaurantId = 1L;

        //when
        restaurantRepository.deleteRestaurantById(restaurantId);
        //then
        verify(restaurantJpaRepository).deleteById(restaurantId);
    }
}