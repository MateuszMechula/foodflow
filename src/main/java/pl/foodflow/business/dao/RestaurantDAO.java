package pl.foodflow.business.dao;

import pl.foodflow.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantDAO {

    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);
    Restaurant saveRestaurant(Restaurant restaurant);
}
