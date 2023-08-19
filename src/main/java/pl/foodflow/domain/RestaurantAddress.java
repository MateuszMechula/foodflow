package pl.foodflow.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "restaurantAddressId")
@ToString(of = {"restaurantAddressId"})
public class RestaurantAddress {

    Long restaurantAddressId;
    Restaurant restaurant;
    Address address;
}
