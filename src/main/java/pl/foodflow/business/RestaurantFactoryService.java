package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantFactoryService {

    private final OwnerService ownerService;
    private final RestaurantService restaurantService;

    @Transactional
    public void createRestaurant(Restaurant restaurant) {
        Owner owner = ownerService.findByEmail(restaurant.getOwnerEmail());
        log.debug("Owner found: {}", owner);

        Restaurant updatedRestaurant = buildRestaurant(restaurant, owner);

        restaurantService.addRestaurant(updatedRestaurant);

        log.info("Restaurant added successfully.");
    }

    private static Restaurant buildRestaurant(Restaurant restaurant, Owner owner) {
        return Restaurant.builder()
                .nip(restaurant.getNip())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .openTime(restaurant.getOpenTime())
                .closeTime(restaurant.getCloseTime())
                .phone(restaurant.getPhone())
                .minimumOrderAmount(restaurant.getMinimumOrderAmount())
                .deliveryPrice(restaurant.getDeliveryPrice())
                .deliveryOption(restaurant.getDeliveryOption())
                .address(Address.builder()
                        .street(restaurant.getAddress().getStreet())
                        .postalCode(restaurant.getAddress().getPostalCode())
                        .city(restaurant.getAddress().getCity())
                        .country(restaurant.getAddress().getCountry())
                        .build())
                .owner(owner)
                .build();
    }
}
