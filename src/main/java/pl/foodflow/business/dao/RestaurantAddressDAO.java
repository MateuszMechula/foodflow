package pl.foodflow.business.dao;

import pl.foodflow.domain.RestaurantAddress;

import java.util.Optional;

public interface RestaurantAddressDAO {

    RestaurantAddress saveRestaurantAddress(RestaurantAddress address);

    Optional<RestaurantAddress> findById(Long addressId);

    void deleteRestaurantAddress(RestaurantAddress restaurantAddress);

    Optional<RestaurantAddress> findByAddressId(Long addressId);
}
