package pl.foodflow.domain;


import lombok.*;
import pl.foodflow.infrastructure.database.entity.CustomerEntity;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

import java.util.Set;
@With
@Value
@Builder
@EqualsAndHashCode(of = "addressId")
@ToString(of = {"addressId", "street", "postalCode", "city", "country"})
public class Address {

    Long addressId;
    String street;
    String postalCode;
    String city;
    String country;
    OwnerEntity owner;
    CustomerEntity customer;
    RestaurantEntity restaurant;
    Set<RestaurantAddressEntity> restaurantAddresses;
}
