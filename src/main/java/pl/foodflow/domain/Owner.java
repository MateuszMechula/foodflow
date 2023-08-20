package pl.foodflow.domain;

import lombok.*;

@With
@Setter
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
    Address address;
    Restaurant restaurant;
}
