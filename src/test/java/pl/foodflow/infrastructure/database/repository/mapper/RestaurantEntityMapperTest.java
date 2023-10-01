package pl.foodflow.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantEntityMapperTest {
    private RestaurantEntityMapper restaurantEntityMapper;

    @Test
    void shouldMapRestaurantToRestaurantEntity() {
        //given
        Restaurant restaurant = TestDataForMappers.someRestaurant();
        //when
        RestaurantEntity restaurantEntity = restaurantEntityMapper.mapToEntity(restaurant);
        //then
        assertEquals(restaurantEntity.getRestaurantId(), restaurant.getRestaurantId());
        assertEquals(restaurantEntity.getNip(), restaurant.getNip());
        assertEquals(restaurantEntity.getName(), restaurant.getName());
        assertEquals(restaurantEntity.getDescription(), restaurant.getDescription());
        assertEquals(restaurantEntity.getOpenTime(), restaurant.getOpenTime());
        assertEquals(restaurantEntity.getCloseTime(), restaurant.getCloseTime());
        assertEquals(restaurantEntity.getPhone(), restaurant.getPhone());
    }

    @Test
    void shouldMapRestaurantEntityToRestaurant() {
        //given
        RestaurantEntity restaurantEntity = TestDataForMappers.someRestaurantEntity();
        //when
        Restaurant restaurant = restaurantEntityMapper.mapFromEntity(restaurantEntity);
        //then
        assertEquals(restaurant.getRestaurantId(), restaurantEntity.getRestaurantId());
        assertEquals(restaurant.getNip(), restaurantEntity.getNip());
        assertEquals(restaurant.getName(), restaurantEntity.getName());
        assertEquals(restaurant.getDescription(), restaurantEntity.getDescription());
        assertEquals(restaurant.getOpenTime(), restaurantEntity.getOpenTime());
        assertEquals(restaurant.getCloseTime(), restaurantEntity.getCloseTime());
        assertEquals(restaurant.getPhone(), restaurantEntity.getPhone());
    }
}