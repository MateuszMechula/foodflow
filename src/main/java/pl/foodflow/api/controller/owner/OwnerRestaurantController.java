package pl.foodflow.api.controller.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.mapper.RestaurantMapper;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.*;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.*;
import java.util.stream.Collectors;

import static pl.foodflow.api.controller.owner.OwnerRestaurantController.OWNER;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerRestaurantController {

    public static final String OWNER = "/owner";
    public static final String RESTAURANT = "/restaurant";
    public static final String RESTAURANT_DETAILS = "/restaurant-details";
    public static final String RESTAURANT_DELETE = "/restaurant-delete";
    public static final String RESTAURANT_UPDATE = "/restaurant-update";

    private final UserService userService;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantService restaurantService;

    @GetMapping(value = RESTAURANT)
    public String restaurantForm(Model model) {
        model.addAttribute("restaurantDTO", RestaurantDTO.buildDefault());

        Owner owner = userService.getCurrentOwner();

        if (owner.getRestaurant() != null) {
            model.addAttribute("existingRestaurant", owner.getRestaurant());
        }
        log.info("Loaded restaurant form for owner: {}", owner);
        return "owner_restaurant_form";
    }

    @GetMapping(value = RESTAURANT_DETAILS)
    public ModelAndView restaurantDetails() {
        Owner owner = userService.getCurrentOwner();
        Restaurant restaurant = getRestaurantForOwner(owner);
        Map<String, Set<CategoryItem>> menuCategoryItem = getMenuCategoryItems(restaurant);

        Map<String, ?> model = Map.of(
                "owner", owner,
                "restaurant", restaurant,
                "menuCategoryItem", menuCategoryItem
        );

        log.info("Loaded restaurant details for owner: {}", owner);
        return new ModelAndView("owner_restaurant_view", model);
    }

    @GetMapping(value = RESTAURANT_UPDATE)
    public String updateRestaurantForm(@RequestParam Long restaurantId, Model model) {
        Restaurant existingRestaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("existingRestaurant", existingRestaurant);
        return "owner_restaurant_update";
    }

    @PostMapping(value = RESTAURANT)
    public String addRestaurant(@Valid @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO) {
        Owner owner = userService.getCurrentOwner();

        Restaurant restaurant = restaurantMapper.map(restaurantDTO);
        restaurantService.addRestaurant(restaurant.withOwner(owner));
        log.info("Added a new restaurant for user: {}", owner);

        return "redirect:/owner/restaurant";
    }

    @PostMapping(value = RESTAURANT_UPDATE)
    public String updateRestaurant(@ModelAttribute("existingRestaurant") RestaurantDTO restaurantDTO) {
        Owner owner = userService.getCurrentOwner();

        Restaurant updatedRestaurant = restaurantMapper.map(restaurantDTO);
        restaurantService.updateRestaurant(updatedRestaurant.withOwner(owner));

        log.info("Updated restaurant details for owner: {}", owner);
        return "redirect:/owner/restaurant";
    }

    @PostMapping(value = RESTAURANT_DELETE)
    public String getDeleteRestaurant(@RequestParam Long restaurantId) {
        restaurantService.deleteRestaurantById(restaurantId);
        log.info("Deleted restaurant with ID: {}", restaurantId);
        return "redirect:/owner/restaurant";
    }

    private Restaurant getRestaurantForOwner(Owner owner) {
        if (Objects.isNull(owner)) {
            return Restaurant.builder().build();
        }
        return Objects.requireNonNullElse(owner.getRestaurant(), Restaurant.builder().build());
    }

    private Map<String, Set<CategoryItem>> getMenuCategoryItems(Restaurant restaurant) {
        Set<MenuCategory> menuCategories = Optional.ofNullable(restaurant)
                .map(Restaurant::getMenu)
                .map(Menu::getMenuCategories)
                .orElse(Collections.emptySet());

        return menuCategories.stream()
                .collect(Collectors.toMap(
                        MenuCategory::getName,
                        MenuCategory::getCategoryItems
                ));
    }
}
