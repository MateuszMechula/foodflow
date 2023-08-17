package pl.foodflow.domain;

import lombok.*;
import pl.foodflow.infrastructure.database.entity.AddressEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

@With
@Value
@Builder
@EqualsAndHashCode(of = "ownerId")
@ToString(of = {"ownerId", "name", "surname", "email", "phone"})

public class Owner {
    Long ownerId;
    String name;
    String surname;
    String email;
    String phone;
    AddressEntity address;
    RestaurantEntity restaurant;
}
