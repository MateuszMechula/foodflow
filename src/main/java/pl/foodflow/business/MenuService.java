package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.MenuDAO;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.business.exceptions.ThatRestaurantHasAMenu;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuDAO menuDAO;
    private final MenuCategoryService menuCategoryService;
    private final CategoryItemService categoryItemService;
    private final RestaurantService restaurantService;

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

        if (Objects.isNull(owner.getRestaurant()) ||
                Objects.isNull(owner.getRestaurant().getMenu().getMenuId())) {
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
    public void addMenuToRestaurant(Owner owner, Menu menu) {
        if (Objects.isNull(owner.getRestaurant())) {
            throw new RestaurantNotFound("To add a menu you have to create restaurant first");
        }
        Restaurant restaurant = owner.getRestaurant();

        if (Objects.nonNull(restaurant.getMenu())) {
            throw new ThatRestaurantHasAMenu
                    ("Restaurant with nip [%s] has a menu. You can't create more than one"
                            .formatted(restaurant.getNip()));
        }
        saveMenu(menu.withRestaurant(restaurant));
    }

    @Transactional
    public void saveMenu(Menu menu) {
        menuDAO.saveMenu(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        menuDAO.deleteMenuById(menuId);
    }
}
