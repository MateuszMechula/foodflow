package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.RestaurantDAO;
import pl.foodflow.business.exceptions.RestaurantAlreadyExistsException;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final OwnerService ownerService;

    public List<Restaurant> findAll() {
        return restaurantDAO.findAll();
    }

    @Transactional
    public Restaurant findRestaurantByNip(String nip) {
        final Optional<Restaurant> restaurant = restaurantDAO.findByNip(nip);
        if (restaurant.isEmpty()) {
            throw new EntityNotFoundException("Could not find Restaurant by nip: [%s]".formatted(nip));
        }
        return restaurant.get();
    }

    @Transactional
    public void createRestaurant(Restaurant restaurant) {
        Owner owner = ownerService.findByEmail(restaurant.getOwnerEmail());
        log.debug("Owner found: {}", owner);

        if (Objects.nonNull(owner.getRestaurant())) {
            throw new RestaurantAlreadyExistsException(
                    "Owner with email [%s] has already owns the restaurant".formatted(owner.getEmail()));
        }
        Restaurant updatedRestaurant = restaurant.withOwner(owner);

        restaurantDAO.saveRestaurant(updatedRestaurant);

        log.info("Restaurant added successfully.");
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

    public List<Restaurant> findAllWithMenuAndCategoriesAndItems() {
        return restaurantDAO.findAllWithMenuAndCategoriesAndItems();
    }
}
