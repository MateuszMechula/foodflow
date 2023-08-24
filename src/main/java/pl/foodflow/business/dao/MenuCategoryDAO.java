package pl.foodflow.business.dao;

import pl.foodflow.domain.MenuCategory;

import java.util.Optional;

public interface MenuCategoryDAO {

    MenuCategory saveMenuCategory(MenuCategory menuCategory);

    Optional<MenuCategory> findCategoryById(Long menuCategoryId);
}
