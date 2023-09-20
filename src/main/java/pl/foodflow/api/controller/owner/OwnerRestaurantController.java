package pl.foodflow.api.controller.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.mapper.RestaurantMapper;
import pl.foodflow.business.OwnerService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.foodflow.api.controller.owner.OwnerRestaurantController.OWNER;

@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerRestaurantController {

    public static final String OWNER = "/owner";
    public static final String RESTAURANT = "/restaurant";
    public static final String RESTAURANT_DETAILS = "/restaurant-details";
    public static final String RESTAURANT_DELETE = "/restaurant-delete";
    public static final String RESTAURANT_UPDATE = "/restaurant-update";

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final OwnerService ownerService;
    private final UserService userService;

    @GetMapping(value = RESTAURANT)
    public String restaurantForm(
            Model model,
            Authentication auth) {
        model.addAttribute("restaurantDTO", RestaurantDTO.buildDefault());

        String username = auth.getName();
        int userId = userService.findByUsername(username).getUserId();
        Owner owner = ownerService.findOwnerByUserId(userId);

        if (owner.getRestaurant() != null) {
            model.addAttribute("existingRestaurant", owner.getRestaurant());
        }

        return "owner_restaurant_form";
    }

    @GetMapping(value = RESTAURANT_DETAILS)
    public ModelAndView restaurantDetails(Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.findByUsername(username).getUserId();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);

        if (Objects.isNull(owner)) {
            owner = Owner.builder().build();
        }

        Restaurant restaurant = owner.getRestaurant();
        if (Objects.isNull(restaurant)) {
            restaurant = Restaurant.builder().build();
        }

        Set<MenuCategory> menuCategories = owner.getRestaurant() != null
                && owner.getRestaurant().getMenu() != null
                && owner.getRestaurant().getMenu().getMenuCategories() != null
                ? owner.getRestaurant().getMenu().getMenuCategories()
                : new HashSet<>();

        Map<String, Set<CategoryItem>> menuCategoryItem = menuCategories.stream()
                .collect(Collectors.toMap(
                        MenuCategory::getName,
                        MenuCategory::getCategoryItems
                ));

        Map<String, ?> model = Map.of(
                "owner", owner,
                "restaurant", restaurant,
                "menuCategoryItem", menuCategoryItem
        );
        return new ModelAndView("owner_restaurant_view", model);
    }

    @GetMapping(value = RESTAURANT_UPDATE)
    public String updateRestaurantForm(
            @RequestParam Long restaurantId,
            Model model) {
        Restaurant existingRestaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("existingRestaurant", existingRestaurant);
        return "owner_restaurant_update";
    }

    @PostMapping(value = RESTAURANT)
    public String addRestaurant(
            @Valid @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO,
            Authentication authentication
    ) {
        String username = authentication.getName();
        int userId = userService.findByUsername(username).getUserId();
        Owner owner = ownerService.findOwnerByUserId(userId);

        Restaurant restaurant = restaurantMapper.map(restaurantDTO);
        restaurantService.addRestaurant(restaurant.withOwner(owner));

        return "redirect:/owner/restaurant";
    }

    @PostMapping(value = RESTAURANT_UPDATE)
    public String updateRestaurant(
            @ModelAttribute("existingRestaurant") RestaurantDTO restaurantDTO,
            Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.findByUsername(username).getUserId();
        Owner owner = ownerService.findOwnerByUserId(userId);

        Restaurant updatedRestaurant = restaurantMapper.map(restaurantDTO);
        restaurantService.updateRestaurant(updatedRestaurant.withOwner(owner));
        return "redirect:/owner/restaurant";
    }

    @PostMapping(value = RESTAURANT_DELETE)
    public String getDeleteRestaurant(
            @RequestParam Long restaurantId
    ) {
        restaurantService.deleteRestaurantById(restaurantId);
        return "redirect:/owner/restaurant";
    }

}
