package pl.foodflow.domain;


import lombok.*;
import pl.foodflow.infrastructure.database.entity.CategoryEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

@With
@Value
@Builder
@EqualsAndHashCode(of = "restaurantCategoryId")
@ToString(of = {"restaurantCategoryId"})

public class RestaurantCategory {

    Long restaurantCategoryId;
    RestaurantEntity restaurant;
    CategoryEntity category;
}
