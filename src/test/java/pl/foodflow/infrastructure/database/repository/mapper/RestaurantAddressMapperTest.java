package pl.foodflow.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantAddressMapperTest {
    private RestaurantAddressMapper restaurantAddressMapper;

    @Test
    void shouldMapRestaurantAddressToRestaurantAddressEntity() {
        //given
        RestaurantAddress restaurantAddress = TestDataForMappers.someRestaurantAddress();
        //when
        RestaurantAddressEntity restaurantAddressEntity = restaurantAddressMapper.mapToEntity(restaurantAddress);
        //then
        assertEquals(restaurantAddressEntity.getRestaurantAddressId(), restaurantAddress.getRestaurantAddressId());
        assertEquals(restaurantAddressEntity.getAddress().getAddressId(), restaurantAddress.getAddress().getAddressId());
        assertEquals(restaurantAddressEntity.getRestaurant().getRestaurantId(), restaurantAddress.getRestaurant()
                .getRestaurantId());

    }

    @Test
    void shouldMapRestaurantAddressEntityToRestaurantAddress() {
        //given
        RestaurantAddressEntity restaurantAddressEntity = TestDataForMappers.someRestaurantAddressEntity();
        //when
        RestaurantAddress restaurantAddress = restaurantAddressMapper.mapFromEntity(restaurantAddressEntity);
        //then
        assertEquals(restaurantAddress.getRestaurantAddressId(), restaurantAddressEntity.getRestaurantAddressId());
        assertEquals(restaurantAddress.getAddress().getAddressId(), restaurantAddressEntity.getAddress().getAddressId());
        assertEquals(restaurantAddress.getRestaurant().getRestaurantId(), restaurantAddressEntity.getRestaurant()
                .getRestaurantId());
    }
}