package pl.foodflow.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "orderItemId")
@ToString(of = {"orderItemId", "unitPrice", "quantity"})
public class OrderItem {

    Long orderItemId;
    BigDecimal unitPrice;
    Integer quantity;
    CategoryItem categoryItem;
    OrderRecord orderRecord;

}
