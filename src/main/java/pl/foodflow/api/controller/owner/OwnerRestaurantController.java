package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.mapper.RestaurantMapper;
import pl.foodflow.business.OwnerService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.security.UserService;

import java.util.List;
import java.util.Map;

import static pl.foodflow.api.controller.owner.OwnerRestaurantController.OWNER;

@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerRestaurantController {

    public static final String OWNER = "/owner";
    public static final String RESTAURANT = "/restaurant";
    public static final String RESTAURANT_DETAILS = "/restaurant-details";
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final OwnerService ownerService;
    private final UserService userService;


    @GetMapping(value = RESTAURANT)
    public ModelAndView restaurantSection() {
        var allOwners = ownerService.findAll();
        Map<String, ?> model = Map.of(
                "restaurantDTO", RestaurantDTO.buildDefault(),
                "allOwners", allOwners
        );
        return new ModelAndView("owner_restaurant", model);
    }

    @GetMapping(value = RESTAURANT_DETAILS)
    public ModelAndView restaurantDetails() {

        List<Restaurant> allRestaurants = restaurantService.findAllWithMenuAndCategoriesAndItems();

        Map<String, ?> model = Map.of(
                "allRestaurants", allRestaurants
        );
        return new ModelAndView("owner_restaurant_details", model);
    }


    @PostMapping(value = RESTAURANT)
    public String addRestaurant(
            @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO,
            Authentication authentication
    ) {
        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUserId();
        Owner owner = ownerService.findByUserId(userId);

        Restaurant restaurant = restaurantMapper.map(restaurantDTO);
        restaurantService.createRestaurant(restaurant.withOwner(owner));

        return "redirect:/owner";
    }
}
