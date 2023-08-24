package pl.foodflow.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "categoryItemId")
@ToString(of = {"categoryItemId", "name", "description", "price"})
public class CategoryItem {

    Long categoryItemId;
    String name;
    String description;
    BigDecimal price;
    String imageUrl;
    MenuCategory menuCategory;
    Set<OrderItem> orderItems;
}
