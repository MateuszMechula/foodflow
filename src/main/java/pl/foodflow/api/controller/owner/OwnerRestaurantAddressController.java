package pl.foodflow.api.controller.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.AddressDTO;
import pl.foodflow.api.dto.mapper.AddressMapper;
import pl.foodflow.business.OwnerService;
import pl.foodflow.business.RestaurantAddressService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.foodflow.api.controller.owner.OwnerRestaurantAddressController.OWNER;

@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerRestaurantAddressController {
    public static final String OWNER = "/owner";
    public static final String DELETE_ADDRESS = "/delete-address";
    private static final String RESTAURANT_ADDRESSES = "/restaurant-addresses";

    private final RestaurantAddressService restaurantAddressService;
    private final AddressMapper addressMapper;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final OwnerService ownerService;

    @GetMapping(value = RESTAURANT_ADDRESSES)
    public ModelAndView addDeliveryAddressToRestaurantForm(Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.findByUsername(username).getUserId();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);

        Set<RestaurantAddress> restaurantAddresses = Optional.ofNullable(owner)
                .map(Owner::getRestaurant)
                .map(Restaurant::getRestaurantAddresses)
                .orElse(Collections.emptySet());

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
            @Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
            Authentication authentication) {

        String username = authentication.getName();
        int userId = userService.findByUsername(username).getUserId();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);

        Address address = addressMapper.map(addressDTO);

        restaurantService.addDeliveryAddressToRestaurant(address, owner);

        return "redirect:/owner/restaurant-addresses";
    }

    @PostMapping(value = DELETE_ADDRESS)
    public String deleteRestaurantAddress(@RequestParam Long addressId) {
        RestaurantAddress restaurantAddress = restaurantAddressService.findRestaurantAddressByAddressId(addressId);

        Address address = restaurantAddress.getAddress();
        Restaurant restaurant = restaurantAddress.getRestaurant();

        Set<RestaurantAddress> addressRestaurantAddresses = address.getRestaurantAddresses();
        addressRestaurantAddresses.remove(restaurantAddress);

        Set<RestaurantAddress> restaurantRestaurantAddresses = restaurant.getRestaurantAddresses();
        restaurantRestaurantAddresses.remove(restaurantAddress);

        restaurantAddressService.deleteRestaurantAddress(restaurantAddress);

        return "redirect:/owner/restaurant-addresses";
    }

}
