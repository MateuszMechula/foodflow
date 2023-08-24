package pl.foodflow.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
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

    public Set<CategoryItem> getCategories() {
        return Objects.isNull(categoryItems) ? new HashSet<>() : categoryItems;
    }

}
