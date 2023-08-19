package pl.foodflow.domain;

import lombok.*;

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
    Customer customer;
    Restaurant restaurant;
    Set<OrderItem> orderItems;
}
