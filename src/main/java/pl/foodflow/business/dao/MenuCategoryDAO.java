package pl.foodflow.business.dao;

import pl.foodflow.domain.MenuCategory;

import java.util.List;
import java.util.Optional;

public interface MenuCategoryDAO {

    void saveMenuCategory(MenuCategory menuCategory);

    Optional<MenuCategory> findCategoryById(Long menuCategoryId);

    void deleteMenuCategoryById(Long menuCategoryId);

    List<MenuCategory> findAllByMenuId(Long menuId);
}
