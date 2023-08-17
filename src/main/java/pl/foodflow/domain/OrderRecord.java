package pl.foodflow.domain;

import lombok.*;
import pl.foodflow.infrastructure.database.entity.CustomerEntity;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "orderNumber")
@ToString(of = {"orderRecordId", "orderDateTime", "orderStatus", "orderNotes", "totalAmount", "contactPhone",
        "deliveryAddress", "deliveryType"})
public class OrderRecord {

    Long orderRecordId;
    String orderNumber;
    OffsetDateTime orderDateTime;
    String orderStatus;
    String orderNotes;
    BigDecimal totalAmount;
    String contactPhone;
    String deliveryAddress;
    Boolean deliveryType;
    CustomerEntity customer;
    RestaurantEntity restaurant;
    Set<OrderItemEntity> orderItems;
}
