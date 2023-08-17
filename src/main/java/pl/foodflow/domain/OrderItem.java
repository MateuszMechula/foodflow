package pl.foodflow.domain;

import lombok.*;
import pl.foodflow.infrastructure.database.entity.MenuItemEntity;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;

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
    MenuItemEntity menuItem;
    OrderRecordEntity orderRecord;

}
