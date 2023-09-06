package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.mapper.RestaurantMapper;
import pl.foodflow.business.OwnerService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.security.UserService;

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
        int userId = userService.findByUserName(username).getUserId();
        Owner owner = ownerService.findByUserId(userId);

        if (owner.getRestaurant() != null) {
            model.addAttribute("existingRestaurant", owner.getRestaurant());
        }

        return "owner_add_restaurant";
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

    @GetMapping(value = RESTAURANT_DETAILS)
    public ModelAndView restaurantDetails(Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUserId();
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

}
