package pl.foodflow.api.controller.rest.owner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.api.dto.AddressRequestDTO;
import pl.foodflow.api.dto.mapper.AddressMapper;
import pl.foodflow.business.OwnerService;
import pl.foodflow.business.RestaurantAddressService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.RestaurantAddress;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerRestaurantAddressRestController.RESTAURANT_ADDRESSES)
@Tag(name = "owner restaurantAddress")
public class OwnerRestaurantAddressRestController {
    public static final String RESTAURANT_ADDRESSES = "/api/v1/owner/restaurant-addresses";
    public static final String ADDRESS_ID = "/{addressId}";
    public static final String OWNER_ID = "/{ownerId}";

    private final OwnerService ownerService;
    private final AddressMapper addressMapper;
    private final RestaurantService restaurantService;
    private final RestaurantAddressService restaurantAddressService;

    @Operation(summary = "Add deliveryAddress to restaurant")
    @PostMapping(value = OWNER_ID)
    public ResponseEntity<Void> addDeliveryAddressToRestaurant(
            @Valid @RequestBody AddressRequestDTO requestDTO,
            @PathVariable Long ownerId
    ) {
        Owner owner = ownerService.findOwnerById(ownerId);
        Address address = addressMapper.map(requestDTO);

        restaurantService.addDeliveryAddressToRestaurant(address, owner);
        log.info("Delivery address added to restaurant NIP: {}", owner.getRestaurant().getNip());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete restaurantAddress")
    @DeleteMapping(value = ADDRESS_ID)
    public ResponseEntity<Void> deleteRestaurantAddress(
            @PathVariable Long addressId) {
        RestaurantAddress restaurantAddress = restaurantAddressService.findRestaurantAddressByAddressId(addressId);

        restaurantAddressService.deleteRestaurantAddress(restaurantAddress);
        log.info("Deleting restaurant address with ID: {} was succesfully", addressId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
