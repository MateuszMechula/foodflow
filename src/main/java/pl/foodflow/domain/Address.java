package pl.foodflow.domain;

import lombok.*;

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
    Owner owner;
    Customer customer;
    Restaurant restaurant;
    Set<RestaurantAddress> restaurantAddresses;
}
