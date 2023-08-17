package pl.foodflow.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.foodflow.infrastructure.database.entity.MenuEntity;
import pl.foodflow.infrastructure.database.entity.MenuItemEntity;

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
    MenuEntity menu;
    Set<MenuItemEntity> menuItems;
}
