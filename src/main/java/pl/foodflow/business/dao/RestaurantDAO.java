package pl.foodflow.business.dao;

import pl.foodflow.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantDAO {
    Optional<Restaurant> findRestaurantById(Long id);

    List<Restaurant> findAllRestaurants();

    Optional<Restaurant> findRestaurantByNip(String nip);

    void saveRestaurant(Restaurant restaurant);

    void deleteRestaurantById(Long restaurantId);
}
