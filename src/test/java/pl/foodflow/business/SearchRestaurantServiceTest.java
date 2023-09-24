package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class SearchRestaurantServiceTest {
    @InjectMocks
    private SearchRestaurantService searchRestaurantService;

    @Test
    void shouldFindMatchingRestaurants() {
        //given
        SearchAddressDTO searchAddressDTO = someSearchAddressDTO();
        RestaurantAddress restaurantAddress1 = someRestaurantAddress1();
        RestaurantAddress restaurantAddress2 = someRestaurantAddress2();

        List<Restaurant> allRestaurants = new ArrayList<>();
        Restaurant restaurant1 = mock(Restaurant.class);
        when(restaurant1.getRestaurantAddresses()).thenReturn(Set.of(restaurantAddress1));
        allRestaurants.add(restaurant1);

        Restaurant restaurant2 = mock(Restaurant.class);
        when(restaurant2.getRestaurantAddresses()).thenReturn(Set.of(restaurantAddress2));
        allRestaurants.add(restaurant2);
        //when
        List<Restaurant> matchingRestaurants = searchRestaurantService
                .filterMatchingRestaurants(searchAddressDTO, allRestaurants);
        //then
        assertEquals(1, matchingRestaurants.size());
        assertTrue(matchingRestaurants.contains(restaurant1));
        assertFalse(matchingRestaurants.contains(restaurant2));
    }
}