package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.business.dao.RestaurantAddressDAO;
import pl.foodflow.business.exceptions.RestaurantAddressNotFoundException;
import pl.foodflow.domain.RestaurantAddress;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someRestaurantAddress1;

@ExtendWith(MockitoExtension.class)
class RestaurantAddressServiceTest {
    @InjectMocks
    private RestaurantAddressService restaurantAddressService;
    @Mock
    private RestaurantAddressDAO restaurantAddressDAO;

    @Test
    void shouldFindRestaurantAddressByAddressId() {
        //given
        Long addressId = 1L;
        RestaurantAddress restaurantAddress = someRestaurantAddress1();

        when(restaurantAddressDAO.findRestaurantAddressByAddressId(addressId))
                .thenReturn(Optional.ofNullable(restaurantAddress));
        //when
        RestaurantAddress expected = restaurantAddressService.findRestaurantAddressByAddressId(addressId);
        //then
        assertNotNull(expected);
        assertEquals(expected, restaurantAddress);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantAddressAddressIdDoesNotExist() {
        //given
        Long addressId = 1L;
        when(restaurantAddressDAO.findRestaurantAddressByAddressId(addressId)).thenReturn(Optional.empty());
        //when,then
        assertThrows(RestaurantAddressNotFoundException.class,
                () -> restaurantAddressService.findRestaurantAddressByAddressId(addressId));
        verify(restaurantAddressDAO).findRestaurantAddressByAddressId(addressId);
    }

    @Test
    void shouldDeleteRestaurantAddress() {
        //given
        RestaurantAddress restaurantAddress = someRestaurantAddress1();
        //when
        restaurantAddressService.deleteRestaurantAddress(restaurantAddress);
        //then
        verify(restaurantAddressDAO).deleteRestaurantAddress(restaurantAddress);
    }

    @Test
    void shouldSaveRestaurantAddress() {
        //given
        RestaurantAddress restaurantAddress = someRestaurantAddress1();
        //when
        restaurantAddressService.saveRestaurantAddress(restaurantAddress);
        //then
        verify(restaurantAddressDAO).saveRestaurantAddress(restaurantAddress);
    }
}