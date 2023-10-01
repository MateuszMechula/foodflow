package pl.foodflow.api.controller.customer;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.business.SearchRestaurantService;
import pl.foodflow.domain.Restaurant;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.customer.CustomerSearchRestaurantController.CUSTOMER;
import static pl.foodflow.api.controller.customer.CustomerSearchRestaurantController.SEARCH_RESTAURANTS;
import static pl.foodflow.util.TestDataFactory.*;

@WebMvcTest(CustomerSearchRestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerSearchRestaurantControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private SearchRestaurantService searchRestaurantService;

    @Test
    @WithMockUser(username = "testCustomer", roles = "CUSTOMER")
    void showSearchFormWorksCorrectly() throws Exception {
        mockMvc.perform(get(CUSTOMER + SEARCH_RESTAURANTS))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchAddressDTO", new SearchAddressDTO()))
                .andExpect(view().name("customer_search_restaurant"));
    }

    @Test
    @WithMockUser(username = "testCustomer", roles = "CUSTOMER")
    void searchRestaurantsWorksCorrectly() throws Exception {
        //given
        SearchAddressDTO searchAddressDTO = someSearchAddressDTO();
        List<Restaurant> allRestaurants = List.of(someRestaurant1(), someRestaurant2());
        List<Restaurant> matchingRestaurants = List.of(someRestaurant2());

        when(restaurantService.findAll()).thenReturn(allRestaurants);
        when(searchRestaurantService.filterMatchingRestaurants(searchAddressDTO, allRestaurants))
                .thenReturn(matchingRestaurants);
        //when,then
        mockMvc.perform(post(CUSTOMER + SEARCH_RESTAURANTS)
                        .param("street", searchAddressDTO.getStreet())
                        .param("city", searchAddressDTO.getStreet())
                        .param("postalCode", searchAddressDTO.getPostalCode()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("matchingRestaurants"))
                .andExpect(model().attributeExists("searchAddressDTO"))
                .andExpect(view().name("customer_search_restaurant"));
    }
}