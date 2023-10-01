package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.api.dto.AddressDTO;
import pl.foodflow.api.dto.mapper.AddressMapper;
import pl.foodflow.business.RestaurantAddressService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.security.user.UserService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.owner.OwnerRestaurantAddressController.*;
import static pl.foodflow.util.TestDataFactory.*;

@WebMvcTest(OwnerRestaurantAddressController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerRestaurantAddressControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private AddressMapper addressMapper;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private RestaurantAddressService restaurantAddressService;

    @Test
    void addDeliveryAddressToRestaurantFormWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();

        when(userService.getCurrentOwner()).thenReturn(owner);
        //when,then
        mockMvc.perform(get(OWNER + RESTAURANT_ADDRESSES))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("addressDTO", "allAddresses"))
                .andExpect(view().name("owner_restaurant_addresses"));
    }

    @Test
    void addDeliveryAddressToRestaurantWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();
        Address address = someAddress1();
        AddressDTO addressDTO = someAddressDTO1();

        when(userService.getCurrentOwner()).thenReturn(owner);
        when(addressMapper.map(addressDTO)).thenReturn(address);
        //given,then
        mockMvc.perform(post(OWNER + RESTAURANT_ADDRESSES)
                        .flashAttr("addressDTO", addressDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + RESTAURANT_ADDRESSES));
        verify(restaurantService).addDeliveryAddressToRestaurant(address, owner);
    }

    @Test
    void deleteRestaurantAddress() throws Exception {
        //given
        Long addressId = 1L;
        RestaurantAddress restaurantAddress = someRestaurantAddress1();

        when(restaurantAddressService.findRestaurantAddressByAddressId(addressId)).thenReturn(restaurantAddress);
        //when,then
        mockMvc.perform(post(OWNER + DELETE_ADDRESS)
                        .param("addressId", String.valueOf(addressId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + RESTAURANT_ADDRESSES));
    }
}