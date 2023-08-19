package pl.foodflow.business.dao;

import pl.foodflow.domain.Restaurant;

import java.util.Optional;

public interface RestaurantDAO {

    Restaurant saveRestaurant(Restaurant restaurant);

    Optional<Restaurant> findByNip(String nip);
}
