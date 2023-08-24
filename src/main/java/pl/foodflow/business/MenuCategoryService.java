package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.MenuCategoryDAO;
import pl.foodflow.business.dao.MenuDAO;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;

import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class MenuCategoryService {

    private final MenuDAO menuDAO;
    private final MenuService menuService;
    private final MenuCategoryDAO menuCategoryDAO;


    @Transactional
    public void addCategoryToMenu(Owner owner, MenuCategory menuCategory) {

        if (Objects.isNull(owner.getRestaurant().getMenu().getMenuId())) {
            throw new RestaurantNotFound("To add a category you have to create menu first");
        }

        Long menuId = owner.getRestaurant().getMenu().getMenuId();
        Menu menu = menuService.findMenuById(menuId);

        Set<MenuCategory> menuCategories = menu.getCategories();
        menuCategories.add(menuCategory);

        menuDAO.saveMenu(menu.withMenuCategories(menuCategories));
        menuCategoryDAO.saveMenuCategory(menuCategory.withMenu(menu));
    }

    @Transactional
    public void saveMenuCategory(MenuCategory menuCategory) {
        menuCategoryDAO.saveMenuCategory(menuCategory);
    }

}
