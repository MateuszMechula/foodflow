package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.MenuDAO;
import pl.foodflow.business.exceptions.MenuNotFoundException;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.business.exceptions.ThatRestaurantHasAMenu;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.utils.ErrorMessages;

import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuDAO menuDAO;
    private final MenuCategoryService menuCategoryService;

    public Menu getMenuById(Long menuId) {
        log.info("Fetching menu by ID: {}", menuId);
        return menuDAO.findMenuById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(ErrorMessages.MENU_NOT_FOUND.formatted(menuId)));
    }

    @Transactional
    public void addMenuCategoryToMenu(Owner owner, MenuCategory menuCategory) {
        validateOwnerAndMenu(owner);
        log.info("Adding category to menu.");

        Long menuId = owner.getRestaurant().getMenu().getMenuId();
        Menu menu = getMenuById(menuId);

        Set<MenuCategory> menuCategories = menu.getCategories();
        menuCategories.add(menuCategory);

        saveMenu(menu.withMenuCategories(menuCategories));
        menuCategoryService.saveMenuCategory(menuCategory.withMenu(menu));
    }

    @Transactional
    public void createMenuForRestaurant(Owner owner, Menu menu) {
        validateOwnerAndMenuNotExist(owner);
        log.info("Adding menu to restaurant.");

        Restaurant restaurant = owner.getRestaurant();
        saveMenu(menu.withRestaurant(restaurant));
    }

    @Transactional
    public void saveMenu(Menu menu) {
        log.info("Saving menu.");
        menuDAO.saveMenu(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        log.info("Deleting menu with ID: {}", menuId);
        menuDAO.deleteMenuById(menuId);
    }

    private void validateOwnerAndMenu(Owner owner) {
        if (Objects.isNull(owner.getRestaurant()) || Objects.isNull(owner.getRestaurant().getMenu())) {
            throw new RestaurantNotFound(ErrorMessages.RESTAURANT_AND_MENU_NOT_CREATED);
        }
    }

    private void validateOwnerAndMenuNotExist(Owner owner) {

        if (Objects.isNull(owner.getRestaurant())) {
            throw new RestaurantNotFound(ErrorMessages.RESTAURANT_NOT_CREATED);
        }
        if (Objects.nonNull(owner.getRestaurant().getMenu())) {
            throw new ThatRestaurantHasAMenu
                    (ErrorMessages.RESTAURANT_ALREADY_HAS_MENU.formatted(owner.getRestaurant().getNip()));
        }
    }
}
