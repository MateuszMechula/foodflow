package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.AddressDAO;
import pl.foodflow.business.dao.RestaurantAddressDAO;
import pl.foodflow.business.dao.RestaurantDAO;
import pl.foodflow.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final RestaurantAddressDAO restaurantAddressDAO;
    private final AddressDAO addressDAO;

    @Transactional
    public List<Restaurant> findAll() {
        return restaurantDAO.findAll();
    }

    @Transactional
    public Restaurant findById(Long id) {
        return restaurantDAO.findById(id).orElseThrow();
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
        RestaurantAddress savedRestaurantAddress = restaurantAddressDAO
                .saveRestaurantAddress(restaurantAddress.withAddress(savedAddress));

        Set<RestaurantAddress> restaurantAddresses = restaurant.getRestaurantAddresses();
        restaurantAddresses.add(savedRestaurantAddress);

        Restaurant updatedRestaurant = restaurant
                .withMenu(menu)
                .withAddress(address.withRestaurant(restaurant))
                .withRestaurantAddresses(restaurantAddresses)
                .withOwner(owner);

        Restaurant restaurant1 = restaurantDAO.saveRestaurant(updatedRestaurant);

        System.out.println(restaurant1);
    }

    private RestaurantAddress buildRestaurantAddress(Address address, Restaurant restaurant) {
        return RestaurantAddress.builder()
                .restaurant(restaurant)
                .address(address)
                .build();
    }

    public List<Restaurant> findAllWithMenuAndCategoriesAndItems() {
        return restaurantDAO.findAllWithMenuAndCategoriesAndItems();
    }
}
