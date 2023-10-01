package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.mapper.RestaurantMapper;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.security.user.UserService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.owner.OwnerRestaurantController.*;
import static pl.foodflow.util.TestDataFactory.*;

@WebMvcTest(OwnerRestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerRestaurantControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private RestaurantMapper restaurantMapper;
    @MockBean
    private RestaurantService restaurantService;

    @Test
    void restaurantFormWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();

        when(userService.getCurrentOwner()).thenReturn(owner);
        //when,then
        mockMvc.perform(get(OWNER + RESTAURANT))
                .andExpect(status().isOk())
                .andExpect(view().name("owner_restaurant_form"));
    }

    @Test
    void restaurantDetailsWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();

        when(userService.getCurrentOwner()).thenReturn(owner);
        //when,then
        mockMvc.perform(get(OWNER + RESTAURANT_DETAILS))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner", "restaurant", "menuCategoryItem"))
                .andExpect(view().name("owner_restaurant_view"));
    }

    @Test
    void updateRestaurantFormWorksCorrectly() throws Exception {
        //given
        Long restaurantId = 1L;
        Restaurant restaurant = someRestaurant1();

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(restaurant);
        //when,then
        mockMvc.perform(get(OWNER + RESTAURANT_UPDATE)
                        .param("restaurantId", String.valueOf(restaurantId)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("existingRestaurant"))
                .andExpect(view().name("owner_restaurant_update"));
    }

    @Test
    void addRestaurantWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();
        Restaurant restaurant = someRestaurant2();
        RestaurantDTO restaurantDTO = someRestaurantDTO2();

        when(userService.getCurrentOwner()).thenReturn(owner);
        when(restaurantMapper.map(restaurantDTO)).thenReturn(restaurant);
        //
        mockMvc.perform(post(OWNER + RESTAURANT)
                        .flashAttr("restaurantDTO", restaurantDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + RESTAURANT));
        verify(restaurantService).addRestaurant(restaurant.withOwner(owner));
    }

    @Test
    void updateRestaurantWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();
        Restaurant updatedRestaurant = someRestaurant1();
        RestaurantDTO restaurantDTO = someRestaurantDTO2();

        when(userService.getCurrentOwner()).thenReturn(owner);
        when(restaurantMapper.map(restaurantDTO)).thenReturn(updatedRestaurant);
        //when,then
        mockMvc.perform(post(OWNER + RESTAURANT_UPDATE)
                        .flashAttr("existingRestaurant", restaurantDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + RESTAURANT));
        verify(restaurantService).updateRestaurant(updatedRestaurant);
    }

    @Test
    void getDeleteRestaurantWorksCorrectly() throws Exception {
        //given
        Long restaurantId = 1L;

        //when,then
        mockMvc.perform(post(OWNER + RESTAURANT_DELETE)
                        .param("restaurantId", String.valueOf(restaurantId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + RESTAURANT));
        verify(restaurantService).deleteRestaurantById(restaurantId);
    }
}