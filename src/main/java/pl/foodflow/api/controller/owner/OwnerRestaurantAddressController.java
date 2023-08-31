package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.foodflow.business.RestaurantAddressService;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;

import java.util.Set;

import static pl.foodflow.api.controller.owner.OwnerRestaurantAddressController.OWNER;

@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerRestaurantAddressController {
    public static final String OWNER = "/owner";
    public static final String DELETE_ADDRESS = "/delete-address";

    private final RestaurantAddressService restaurantAddressService;

    @PostMapping(value = DELETE_ADDRESS)
    public String deleteRestaurantAddress(@RequestParam Long addressId) {
        RestaurantAddress restaurantAddress = restaurantAddressService.findByAddressId(addressId);

        Address address = restaurantAddress.getAddress();
        Restaurant restaurant = restaurantAddress.getRestaurant();

        Set<RestaurantAddress> addressRestaurantAddresses = address.getRestaurantAddresses();
        addressRestaurantAddresses.remove(restaurantAddress);

        Set<RestaurantAddress> restaurantRestaurantAddresses = restaurant.getRestaurantAddresses();
        restaurantRestaurantAddresses.remove(restaurantAddress);

        restaurantAddressService.deleteRestaurantAddress(restaurantAddress);

        return "redirect:/owner";
    }
}
