package pl.foodflow.api.controller.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.AddressDTO;
import pl.foodflow.api.dto.mapper.AddressMapper;
import pl.foodflow.business.RestaurantAddressService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.foodflow.api.controller.owner.OwnerRestaurantAddressController.OWNER;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerRestaurantAddressController {
    public static final String OWNER = "/owner";
    public static final String DELETE_ADDRESS = "/delete-address";
    public static final String RESTAURANT_ADDRESSES = "/restaurant-addresses";

    private final UserService userService;
    private final AddressMapper addressMapper;
    private final RestaurantService restaurantService;
    private final RestaurantAddressService restaurantAddressService;

    @GetMapping(value = RESTAURANT_ADDRESSES)
    public ModelAndView addDeliveryAddressToRestaurantForm() {
        Owner owner = userService.getCurrentOwner();

        log.info("Fetching restaurant addresses for owner: {}", owner);

        Set<Address> allAddresses = getAllAddressesForOwner(owner);

        ModelAndView modelAndView = new ModelAndView("owner_restaurant_addresses");
        modelAndView.addObject("addressDTO", AddressDTO.buildDefault());
        modelAndView.addObject("allAddresses", allAddresses);

        return modelAndView;
    }

    @PostMapping(value = RESTAURANT_ADDRESSES)
    public String addDeliveryAddressToRestaurant(@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
        Owner owner = userService.getCurrentOwner();
        Address address = addressMapper.map(addressDTO);

        restaurantService.addDeliveryAddressToRestaurant(address, owner);
        log.info("Delivery address added to restaurant NIP: {}", owner.getRestaurant().getNip());

        return "redirect:/owner/restaurant-addresses";
    }

    @PostMapping(value = DELETE_ADDRESS)
    public String deleteRestaurantAddress(@RequestParam Long addressId) {
        RestaurantAddress restaurantAddress = restaurantAddressService.findRestaurantAddressByAddressId(addressId);

        restaurantAddressService.deleteRestaurantAddress(restaurantAddress);
        log.info("Deleting restaurant address with ID: {} was succesfully", addressId);

        return "redirect:/owner/restaurant-addresses";
    }

    private static Set<Address> getAllAddressesForOwner(Owner owner) {
        return Optional.ofNullable(owner)
                .map(Owner::getRestaurant)
                .map(Restaurant::getRestaurantAddresses)
                .orElse(Collections.emptySet())
                .stream()
                .map(RestaurantAddress::getAddress)
                .collect(Collectors.toSet());
    }

}
