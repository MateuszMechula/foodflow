package pl.foodflow.domain;

import lombok.*;
import pl.foodflow.infrastructure.database.entity.ItemImageEntity;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "menuItemId")
@ToString(of = {"menuItemId", "name", "description", "price"})
public class MenuItem {

    Long menuItemId;
    String name;
    String description;
    BigDecimal price;
    MenuCategoryEntity menuCategory;
    ItemImageEntity itemImage;
    Set<OrderItemEntity> orderItems;
}
