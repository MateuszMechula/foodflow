package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.AddressDTO;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.mapper.AddressMapper;
import pl.foodflow.api.dto.mapper.RestaurantMapper;
import pl.foodflow.business.AddressService;
import pl.foodflow.business.OwnerService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.*;
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
    private static final String RESTAURANT_ADDRESSES = "/restaurant-addresses";

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final OwnerService ownerService;
    private final UserService userService;
    private final AddressMapper addressMapper;
    private final AddressService addressService;


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
        return new ModelAndView("restaurant_view", model);
    }

    @GetMapping(value = RESTAURANT_ADDRESSES)
    public ModelAndView addDeliveryAddressToRestaurantForm(Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUserId();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);

        Set<RestaurantAddress> restaurantAddresses = owner.getRestaurant().getRestaurantAddresses();

        Set<Address> allAddresses = restaurantAddresses.stream()
                .map(RestaurantAddress::getAddress)
                .collect(Collectors.toSet());

        Map<String, ?> model = Map.of(
                "addressDTO", AddressDTO.buildDefault(),
                "allAddresses", allAddresses
        );
        return new ModelAndView("owner_restaurant_addresses", model);
    }

    @PostMapping(value = RESTAURANT_ADDRESSES)
    public String addDeliveryAddressToRestaurant(
            @ModelAttribute("addressDTO") AddressDTO addressDTO,
            Authentication authentication) {

        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUserId();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);

        Address address = addressMapper.map(addressDTO);

        restaurantService.addDeliveryAddressToRestaurant(address, owner);

        return "redirect:/owner";
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
