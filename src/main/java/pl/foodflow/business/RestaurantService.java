package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.RestaurantDAO;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final OwnerService ownerService;

    @Transactional
    public Restaurant findRestaurantByNip(String nip) {
        final Optional<Restaurant> restaurant = restaurantDAO.findByNip(nip);
        if (restaurant.isEmpty()) {
            throw new EntityNotFoundException("Could not find Restaurant by nip: [%s]".formatted(nip));
        }
        return restaurant.get();
    }

    @Transactional
    public void addRestaurant(Restaurant restaurant) {

        restaurantDAO.saveRestaurant(restaurant);
    }

    @Transactional
    public void addDeliveryAddressToRestaurant(String nip, Address address) {

        Restaurant existingRestaurant = findRestaurantByNip(nip);

        if (!existingRestaurant.getDeliveryOption()) {
            throw new RuntimeException("Restaurant with NIP: [%s] do not deliver food");
        }

        RestaurantAddress newRestaurantAddress = buildRestaurantAddress(address, existingRestaurant);

        Set<RestaurantAddress> restaurantAddresses = existingRestaurant.getRestaurantAddresses();
        restaurantAddresses.add(newRestaurantAddress);

    }

    private RestaurantAddress buildRestaurantAddress(Address address, Restaurant restaurant) {
        return RestaurantAddress.builder()
                .restaurantAddressId(restaurant.getRestaurantId())
                .address(address)
                .build();
    }
}
