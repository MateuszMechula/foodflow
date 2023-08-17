package pl.foodflow.domain;

import lombok.*;
import pl.foodflow.infrastructure.database.entity.AddressEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

@With
@Value
@Builder
@EqualsAndHashCode(of = "restaurantAddressId")
@ToString(of = {"restaurantAddressId"})
public class RestaurantAddress {

    Long restaurantAddressId;
    RestaurantEntity restaurant;
    AddressEntity address;
}
