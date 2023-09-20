package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.AddressDAO;
import pl.foodflow.business.dao.RestaurantDAO;
import pl.foodflow.business.exceptions.InvalidAddressException;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.domain.*;
import pl.foodflow.utils.ErrorMessages;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final AddressDAO addressDAO;
    private final RestaurantDAO restaurantDAO;
    private final RestaurantAddressService restaurantAddressService;

    public Restaurant getRestaurantById(Long restaurantId) {
        log.info("Fetching restaurant with ID: {}", restaurantId);
        return restaurantDAO.findRestaurantById(restaurantId).orElseThrow(() -> new RestaurantNotFound
                (ErrorMessages.RESTAURANT_BY_ID_NOT_FOUND.formatted(restaurantId)));
    }

    private Restaurant getRestaurantByNip(String restaurantNip) {
        log.info("Fetching restaurant by NIP: {}", restaurantNip);
        return restaurantDAO.findRestaurantByNip(restaurantNip).orElseThrow(() -> new RestaurantNotFound(
                ErrorMessages.RESTAURANT_BY_NIP_NOT_FOUND.formatted(restaurantNip)));
    }

    public List<Restaurant> findAll() {
        log.info("Fetching all restaurants");
        return restaurantDAO.findAllRestaurants();
    }

    @Transactional
    public void addRestaurant(Restaurant restaurant) {
        validateRestaurantAddress(restaurant.getAddress());
        restaurantDAO.saveRestaurant(restaurant);
        log.info("Restaurant added successfully");
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

        Restaurant updatedRestaurant = getUpdatedRestaurant(owner, restaurant, menu, address, restaurantAddresses);

        restaurantDAO.saveRestaurant(updatedRestaurant);
        log.info("Delivery address added successfully to restaurant NIP: {}", restaurant.getNip());
    }

    @Transactional
    public void updateRestaurant(Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = getRestaurantByNip(updatedRestaurant.getNip());

        if (existingRestaurant != null) {
            Restaurant toSave = buildUpdatedRestaurant(updatedRestaurant, existingRestaurant);
            restaurantDAO.saveRestaurant(toSave);
            log.info("Restaurant with NIP: {} updated", updatedRestaurant.getNip());
        }
    }

    @Transactional
    public void deleteRestaurantById(Long restaurantId) {
        restaurantDAO.deleteRestaurantById(restaurantId);
        log.info("Restaurant with ID: {} deleted", restaurantId);
    }

    private void validateRestaurantAddress(Address address) {
        if (address != null && isNotBlank(address.getCity()) && isNotBlank(address.getPostalCode())
                && isNotBlank(address.getStreet()) && isNotBlank(address.getCountry())) {
            return;
        }
        log.error("Invalid restaurant address. Restaurant not saved.");
        throw new InvalidAddressException("Invalid restaurant address.");
    }

    private RestaurantAddress buildRestaurantAddress(Address address, Restaurant restaurant) {
        return RestaurantAddress.builder()
                .restaurant(restaurant)
                .address(address)
                .build();
    }

    private static Restaurant getUpdatedRestaurant(Owner owner, Restaurant restaurant, Menu menu, Address address, Set<RestaurantAddress> restaurantAddresses) {
        return restaurant
                .withMenu(menu)
                .withAddress(address.withRestaurant(restaurant))
                .withRestaurantAddresses(restaurantAddresses)
                .withOwner(owner);
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
}
