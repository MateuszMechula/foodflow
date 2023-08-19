package pl.foodflow.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"customerId", "name", "surname", "email"})
public class Customer {
    Long customerId;
    String name;
    String surname;
    String email;
    String phone;
    Address address;
    Set<OrderRecord> orderRecords;
}
