package pl.foodflow.domain;

import lombok.*;
import pl.foodflow.infrastructure.database.entity.AddressEntity;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"customerId","name", "surname", "email"})
public class Customer {
    Long customerId;
    String name;
    String surname;
    String email;
    String phone;
    AddressEntity address;
    Set<OrderRecordEntity> orderRecords;
}
