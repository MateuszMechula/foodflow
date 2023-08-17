package pl.foodflow.domain;

import lombok.*;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "menuId")
@ToString(of = {"menuId", "name", "description"})
public class Menu {

    Long menuId;
    String name;
    String description;
    RestaurantEntity restaurant;
    Set<MenuCategoryEntity> menuCategories;
}
