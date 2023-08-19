package pl.foodflow.domain;


import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "restaurantCategoryId")
@ToString(of = {"restaurantCategoryId"})

public class RestaurantCategory {

    Long restaurantCategoryId;
    Restaurant restaurant;
    Category category;
}
