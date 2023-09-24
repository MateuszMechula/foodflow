package pl.foodflow.business.dao;

import pl.foodflow.domain.MenuCategory;

import java.util.List;
import java.util.Optional;

public interface MenuCategoryDAO {
    List<MenuCategory> findAllCategoriesByMenuId(Long menuId);

    void saveMenuCategory(MenuCategory menuCategory);

    void deleteMenuCategoryById(Long menuCategoryId);

    Optional<MenuCategory> findMenuCategoryById(Long menuCategoryId);
}
