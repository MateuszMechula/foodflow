package pl.foodflow.business.dao;

import pl.foodflow.domain.RestaurantAddress;

import java.util.Optional;

public interface RestaurantAddressDAO {
    Optional<RestaurantAddress> findRestaurantAddressByAddressId(Long addressId);

    RestaurantAddress saveRestaurantAddress(RestaurantAddress address);

    void deleteRestaurantAddress(RestaurantAddress restaurantAddress);
}
