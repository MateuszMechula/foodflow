package pl.foodflow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRecordDTO {
    String orderNumber;
    OffsetDateTime orderDateTime;
    String orderStatus;
    String orderNotes;
    BigDecimal totalAmount;
    String contactPhone;
    String deliveryAddress;
    String deliveryType;
    Long customerId;
    Long restaurantId;
    Set<OrderItemDTO> orderItems;
}
