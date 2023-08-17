package pl.foodflow.domain;

import lombok.*;
import pl.foodflow.infrastructure.database.entity.RestaurantCategoryEntity;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "categoryId")
@ToString(of = {"categoryId", "name"})
public class Category {

    Long categoryId;
    String name;
    Set<RestaurantCategoryEntity> restaurantCategories;
}
