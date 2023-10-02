package pl.foodflow.api.controller.rest.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.api.dto.AddressDTO;
import pl.foodflow.api.dto.mapper.AddressMapper;
import pl.foodflow.business.RestaurantAddressService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.security.user.UserService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerRestaurantAddressRestController.RESTAURANT_ADDRESSES)
public class OwnerRestaurantAddressRestController {
    public static final String RESTAURANT_ADDRESSES = "/api/v1/owner/restaurant-addresses";
    public static final String ADDRESS_ID = "/{addressId}";

    private final UserService userService;
    private final AddressMapper addressMapper;
    private final RestaurantService restaurantService;
    private final RestaurantAddressService restaurantAddressService;

    @PostMapping
    public ResponseEntity<Void> addDeliveryAddressToRestaurant(
            @Valid @RequestBody AddressDTO addressDTO
    ) {
        Owner owner = userService.getCurrentOwner();
        Address address = addressMapper.map(addressDTO);

        restaurantService.addDeliveryAddressToRestaurant(address, owner);
        log.info("Delivery address added to restaurant NIP: {}", owner.getRestaurant().getNip());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = ADDRESS_ID)
    public ResponseEntity<Void> deleteRestaurantAddress(
            @PathVariable Long addressId) {
        RestaurantAddress restaurantAddress = restaurantAddressService.findRestaurantAddressByAddressId(addressId);

        restaurantAddressService.deleteRestaurantAddress(restaurantAddress);
        log.info("Deleting restaurant address with ID: {} was succesfully", addressId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
