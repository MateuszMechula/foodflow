package pl.foodflow.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "menuCategoryId")
@ToString(of = {"menuCategoryId", "name", "description"})
public class MenuCategory {

    Long menuCategoryId;
    String name;
    String description;
    Menu menu;
    Set<CategoryItem> categoryItems;

}
