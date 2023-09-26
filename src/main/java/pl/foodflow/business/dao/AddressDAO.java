package pl.foodflow.business.dao;

import pl.foodflow.domain.Address;

import java.util.Optional;

public interface AddressDAO {
    Address saveAddress(Address address);

    Optional<Address> findById(Long addressId);
}
