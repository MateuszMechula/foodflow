package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.MenuDAO;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuDAO menuDAO;
    private final MenuCategoryService menuCategoryService;
    private final CategoryItemService categoryItemService;

    public List<Menu> findAll() {
        return menuDAO.findAll();
    }

    public Menu findMenuById(Long menuId) {
        return menuDAO.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Menu with id: [%s] not found".formatted(menuId)
                ));
    }

    @Transactional
    public void addCategoryToMenu(Owner owner, MenuCategory menuCategory) {

        if (Objects.isNull(owner.getRestaurant().getMenu().getMenuId())) {
            throw new RestaurantNotFound("To add a category you have to create menu first");
        }

        Long menuId = owner.getRestaurant().getMenu().getMenuId();
        Menu menu = findMenuById(menuId);

        Set<MenuCategory> menuCategories = menu.getCategories();
        menuCategories.add(menuCategory);

        saveMenu(menu.withMenuCategories(menuCategories));
        menuCategoryService.saveMenuCategory(menuCategory.withMenu(menu));
    }

    @Transactional
    public void saveMenu(Menu menu) {
        menuDAO.saveMenu(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        Menu menuToDelete = findMenuById(menuId);

        menuToDelete.getMenuCategories().forEach(menuCategory ->
                menuCategory.getCategoryItems().forEach(categoryItemService::deleteCategoryItem));

        menuToDelete.getMenuCategories().forEach(menuCategoryService::deleteMenuCategory);

        menuDAO.deleteMenu(menuId);
    }
}
