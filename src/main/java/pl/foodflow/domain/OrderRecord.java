package pl.foodflow.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
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
    String contactPhone;
    String deliveryAddress;
    String deliveryType;
    Customer customer;
    Restaurant restaurant;
    Set<OrderItem> orderItems;

    public Set<OrderItem> getOrderItems() {
        return Objects.isNull(orderItems) ? new HashSet<>() : orderItems;
    }
}
