package pl.foodflow.domain;

import lombok.*;

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
    Restaurant restaurant;
    Set<MenuCategory> menuCategories;
}
