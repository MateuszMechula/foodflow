package pl.foodflow.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "categoryId")
@ToString(of = {"categoryId", "name"})
public class Category {

    Long categoryId;
    String name;
    Set<RestaurantCategory> restaurantCategories;
}
