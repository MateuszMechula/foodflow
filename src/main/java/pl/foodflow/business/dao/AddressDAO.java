package pl.foodflow.business.dao;

import pl.foodflow.domain.Address;

import java.util.Set;

public interface AddressDAO {

    Address saveAddress(Address address);

    Set<Address> findAddressByRestaurantId(Long restaurantId);
}
