package pl.foodflow.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.business.dao.AddressDAO;
import pl.foodflow.business.dao.RestaurantDAO;
import pl.foodflow.business.exceptions.InvalidAddressException;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.foodflow.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    @InjectMocks
    private RestaurantService restaurantService;
    @Mock
    private RestaurantDAO restaurantDAO;
    @Mock
    private RestaurantAddressService restaurantAddressService;
    @Mock
    private AddressDAO addressDAO;

    @Test
    void shouldGetRestaurantById() {
        //given
        Long restaurantId = 1L;
        Restaurant restaurant = someRestaurant1();

        when(restaurantDAO.findRestaurantById(restaurantId))
                .thenReturn(Optional.ofNullable(restaurant));
        //when
        Restaurant result = restaurantService.getRestaurantById(restaurantId);
        //then
        assertNotNull(result);
        assertEquals(result, restaurant);
    }

    @Test
    void shouldThrownExceptionWhenRestaurantIdDoesNotExist() {
        //given
        Long restaurantId = 1L;

        when(restaurantDAO.findRestaurantById(restaurantId)).thenReturn(Optional.empty());

        //when,then
        Assertions.assertThrows(RestaurantNotFound.class, () -> restaurantService.getRestaurantById(restaurantId));
        verify(restaurantDAO).findRestaurantById(restaurantId);
    }

    @Test
    void shouldGetRestaurantByNip() {
        //given
        String restaurantNip = "7244505040";
        Restaurant restaurant = someRestaurant1();

        when(restaurantDAO.findRestaurantByNip(restaurantNip)).thenReturn(Optional.ofNullable(restaurant));

        //when
        Restaurant result = restaurantService.getRestaurantByNip(restaurantNip);

        //then
        assertNotNull(result);
        assertEquals(result, restaurant);
    }

    @Test
    void shouldThrownExceptionWhenRestaurantNipDoesNotExist() {
        //given
        String restaurantNip = "7244505040";
        when(restaurantDAO.findRestaurantByNip(restaurantNip)).thenReturn(Optional.empty());

        //when,then
        Assertions.assertThrows(RestaurantNotFound.class, () -> restaurantService.getRestaurantByNip(restaurantNip));
        verify(restaurantDAO).findRestaurantByNip(restaurantNip);
    }

    @Test
    void shouldFindAllRestaurants() {
        //given
        var restaurants = List.of(someRestaurant1(), someRestaurant2());
        when(restaurantDAO.findAllRestaurants()).thenReturn(restaurants);

        //when
        List<Restaurant> result = restaurantService.findAll();

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }

    @Test
    void shouldAddRestaurant() {
        //given
        Restaurant restaurant = someRestaurant1();

        //when
        restaurantService.addRestaurant(restaurant);

        //then
        verify(restaurantDAO).saveRestaurant(restaurant);
    }

    @Test
    void shouldThrownExceptionWhenAddressIsInvalid() {
        //given
        Restaurant restaurant = someRestaurant1().withAddress(Address.builder().build());
        //when,then
        assertThrows(InvalidAddressException.class, () -> restaurantService.addRestaurant(restaurant));
    }

    @Test
    void addDeliveryAddressToRestaurant() {
        //given
        Address deliveryAddress = someAddress1();
        Owner owner = someOwner1();

        Address savedAddress = someAddress1();
        when(addressDAO.saveAddress(deliveryAddress)).thenReturn(savedAddress);

        RestaurantAddress restaurantAddress = someRestaurantAddress1();
        when(restaurantAddressService.saveRestaurantAddress(any(RestaurantAddress.class)))
                .thenReturn(restaurantAddress);

        doNothing().when(restaurantDAO).saveRestaurant(any(Restaurant.class));

        //when
        restaurantService.addDeliveryAddressToRestaurant(deliveryAddress, owner);

        //then
        verify(addressDAO, times(1)).saveAddress(deliveryAddress);
        verify(restaurantAddressService, times(1)).saveRestaurantAddress(any(RestaurantAddress.class));
        verify(restaurantDAO).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void updateRestaurant() {
        //given
        String existingNip = "7244505040";
        Restaurant existingRestaurant = someRestaurant1();
        Restaurant updatedRestaurant = someRestaurant2();

        when(restaurantDAO.findRestaurantByNip(existingNip)).thenReturn(Optional.ofNullable(existingRestaurant));

        //when
        restaurantService.updateRestaurant(updatedRestaurant.withNip(existingNip));

        //then
        verify(restaurantDAO, times(1)).saveRestaurant(updatedRestaurant);
    }

    @Test
    void deleteRestaurantById() {
        //given
        Long restaurantId = 1L;
        //when
        restaurantService.deleteRestaurantById(restaurantId);
        //then
        verify(restaurantDAO).deleteRestaurantById(restaurantId);
    }
}