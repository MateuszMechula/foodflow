package pl.foodflow.api.controller.customer;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.business.SearchRestaurantService;
import pl.foodflow.domain.Restaurant;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.customer.CustomerSearchRestaurantController.*;
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
                .andExpect(view().name("customer_search_restaurant_form"));
    }

    @Test
    @WithMockUser(username = "testCustomer", roles = "CUSTOMER")
    void searchRestaurantsWorksCorrectly() throws Exception {
        //given
        SearchAddressDTO searchAddressDTO = someSearchAddressDTO();
        List<Restaurant> all = List.of(someRestaurant1(), someRestaurant2());
        Page<Restaurant> allRestaurants = new PageImpl<>(all);
        List<Restaurant> matchingRestaurants = List.of(someRestaurant2());
        int page = 1;
        int pageSize = 5;
        String sortColumn = "defaultSortColumn";

        when(restaurantService.findPaginated(page, pageSize, sortColumn)).thenReturn(allRestaurants);
        when(searchRestaurantService.filterMatchingRestaurants(searchAddressDTO, allRestaurants.getContent()))
                .thenReturn(matchingRestaurants);
        //when,then
        mockMvc.perform(get(CUSTOMER + SEARCH_RESTAURANTS + PAGE)
                        .param("street", searchAddressDTO.getStreet())
                        .param("city", searchAddressDTO.getStreet())
                        .param("postalCode", searchAddressDTO.getPostalCode()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalItems"))
                .andExpect(model().attributeExists("listRestaurants"))
                .andExpect(model().attributeExists("searchAddressDTO"))
                .andExpect(view().name("customer_search_restaurant_table"));
    }
}