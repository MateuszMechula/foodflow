package pl.foodflow.api.controller.rest.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.mapper.RestaurantMapper;
import pl.foodflow.business.OwnerService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerRestaurantRestController.RESTAURANTS)
public class OwnerRestaurantRestController {

    public static final String RESTAURANTS = "/api/v1/owner/restaurants";
    public static final String RESTAURANT_ID = "/{restaurantId}";
    public static final String OWNER_ID = "/{ownerId}";
    private final OwnerService ownerService;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantService restaurantService;

    @GetMapping(value = RESTAURANT_ID)
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @PostMapping(value = OWNER_ID)
    public ResponseEntity<Void> addRestaurant(
            @Valid @RequestBody RestaurantDTO restaurantDTO,
            @PathVariable Long ownerId) {

        Owner owner = ownerService.findOwnerById(ownerId);

        Restaurant restaurant = restaurantMapper.map(restaurantDTO);
        restaurantService.addRestaurant(restaurant.withOwner(owner));
        log.info("Added a new restaurant for user: {}", owner);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = OWNER_ID)
    public ResponseEntity<Void> updateRestaurant(
            @RequestBody RestaurantDTO restaurantDTO,
            @PathVariable Long ownerId) {
        Owner owner = ownerService.findOwnerById(ownerId);

        Restaurant updatedRestaurant = restaurantMapper.map(restaurantDTO);
        restaurantService.updateRestaurant(updatedRestaurant.withOwner(owner));

        log.info("Updated restaurant details for owner: {}", owner);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = RESTAURANT_ID)
    public ResponseEntity<Void> getDeleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteRestaurantById(restaurantId);
        log.info("Deleted restaurant with ID: {}", restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
