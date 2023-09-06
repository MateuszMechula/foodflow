package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.AddressDAO;
import pl.foodflow.business.dao.RestaurantDAO;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final RestaurantAddressService restaurantAddressService;
    private final AddressDAO addressDAO;

    public Restaurant findById(Long restaurantId) {
        return restaurantDAO.findById(restaurantId).orElseThrow(() -> new RestaurantNotFound
                ("Restaurant with ID: [%s] not found".formatted(restaurantId)));
    }

    public List<Restaurant> findAll() {
        return restaurantDAO.findAll();
    }

    @Transactional
    public void createRestaurant(Restaurant restaurant) {
        restaurantDAO.saveRestaurant(restaurant);
        log.info("Restaurant added successfully.");
    }

    @Transactional
    public void addDeliveryAddressToRestaurant(Address deliveryAddress, Owner owner) {

        Restaurant restaurant = owner.getRestaurant();
        Menu menu = owner.getRestaurant().getMenu();
        Address address = owner.getRestaurant().getAddress();

        RestaurantAddress restaurantAddress = buildRestaurantAddress(deliveryAddress, restaurant);

        Address savedAddress = addressDAO.saveAddress(deliveryAddress);
        RestaurantAddress savedRestaurantAddress = restaurantAddressService
                .saveRestaurantAddress(restaurantAddress.withAddress(savedAddress));

        Set<RestaurantAddress> restaurantAddresses = restaurant.getRestaurantAddresses();
        restaurantAddresses.add(savedRestaurantAddress);

        Restaurant updatedRestaurant = restaurant
                .withMenu(menu)
                .withAddress(address.withRestaurant(restaurant))
                .withRestaurantAddresses(restaurantAddresses)
                .withOwner(owner);

        restaurantDAO.saveRestaurant(updatedRestaurant);
    }

    private RestaurantAddress buildRestaurantAddress(Address address, Restaurant restaurant) {
        return RestaurantAddress.builder()
                .restaurant(restaurant)
                .address(address)
                .build();
    }

    public void updateRestaurant(Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = findRestaurantByNip(updatedRestaurant.getNip());

        if (existingRestaurant != null) {
            Restaurant toSave = buildUpdatedRestaurant(updatedRestaurant, existingRestaurant);

            restaurantDAO.saveRestaurant(toSave);
        }
    }

    private Restaurant findRestaurantByNip(String nip) {
        return restaurantDAO.findRestaurantByNip(nip).orElseThrow(() -> new RestaurantNotFound(
                "Restaurant with NIP: [%s] not found".formatted(nip)));
    }

    private static Restaurant buildUpdatedRestaurant(Restaurant updatedRestaurant, Restaurant existingRestaurant) {
        return Restaurant.builder()
                .restaurantId(Optional.ofNullable(updatedRestaurant.getRestaurantId())
                        .orElse(existingRestaurant.getRestaurantId()))
                .nip(Optional.ofNullable(updatedRestaurant.getNip()).orElse(existingRestaurant.getNip()))
                .name(Optional.ofNullable(updatedRestaurant.getName()).orElse(existingRestaurant.getName()))
                .deliveryOption(Optional.ofNullable(updatedRestaurant.getDeliveryOption())
                        .orElse(existingRestaurant.getDeliveryOption()))
                .openTime(Optional.ofNullable(updatedRestaurant.getOpenTime()).orElse(existingRestaurant.getOpenTime()))
                .closeTime(Optional.ofNullable(updatedRestaurant.getCloseTime()).orElse(existingRestaurant.getCloseTime()))
                .phone(Optional.ofNullable(updatedRestaurant.getPhone()).orElse(existingRestaurant.getPhone()))
                .minimumOrderAmount(Optional.ofNullable(updatedRestaurant.getMinimumOrderAmount())
                        .orElse(existingRestaurant.getMinimumOrderAmount()))
                .deliveryPrice(Optional.ofNullable(updatedRestaurant.getDeliveryPrice())
                        .orElse(existingRestaurant.getDeliveryPrice()))
                .deliveryOption(Optional.ofNullable(updatedRestaurant.getDeliveryOption())
                        .orElse(existingRestaurant.getDeliveryOption()))
                .address(Optional.ofNullable(updatedRestaurant.getAddress()).orElse(existingRestaurant.getAddress()))
                .owner(Optional.ofNullable(updatedRestaurant.getOwner()).orElse(existingRestaurant.getOwner()))
                .build();
    }

    public void deleteRestaurantById(Long restaurantId) {
        restaurantDAO.deleteRestaurantById(restaurantId);
    }
}
