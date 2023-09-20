package pl.foodflow.business.dao;

import pl.foodflow.domain.MenuCategory;

import java.util.List;

public interface MenuCategoryDAO {
    List<MenuCategory> findAllByMenuCategoryId(Long menuId);

    void saveMenuCategory(MenuCategory menuCategory);

    void deleteMenuCategoryById(Long menuCategoryId);
}
