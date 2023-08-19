package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.MenuCategoryDAO;
import pl.foodflow.business.dao.MenuDAO;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;

import java.util.Set;

@Service
@AllArgsConstructor
public class MenuCategoryService {

    private final MenuService menuService;
    private final MenuDAO menuDAO;
    private final MenuCategoryDAO menuCategoryDAO;


    @Transactional
    public void addCategoryToMenu(Long menuId, MenuCategory menuCategory) {

        Menu menu = menuService.findMenuById(menuId);

        Set<MenuCategory> menuCategories = menu.getMenuCategories();
        menuCategories.add(menuCategory);

        menuDAO.saveMenu(menu.withMenuCategories(menuCategories));
    }

    @Transactional
    public void saveMenuCategory(MenuCategory menuCategory) {
        menuCategoryDAO.saveMenuCategory(menuCategory);
    }

}
