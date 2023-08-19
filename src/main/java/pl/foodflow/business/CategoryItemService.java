package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;

import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryItemService {

    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;

    @Transactional
    public void addItemToMenuCategory(Long menuId, Long categoryId, CategoryItem categoryItem) {
        Menu menu = menuService.findMenuById(menuId);

        Set<MenuCategory> menuCategories = menu.getMenuCategories();

        MenuCategory menuCategory = menuCategories.stream()
                .filter(a -> a.getMenuCategoryId().equals(categoryId))
                .findFirst().orElseThrow();

        Set<CategoryItem> categoryItems = menuCategory.getCategoryItems();

        CategoryItem categoryItemToAdd = buildNewMenuCategoryItem(categoryItem);
        categoryItems.add(categoryItemToAdd);

        MenuCategory updatedMenuCategory = menuCategory.withCategoryItems(categoryItems);
        menuCategoryService.saveMenuCategory(updatedMenuCategory);
    }

    private CategoryItem buildNewMenuCategoryItem(CategoryItem categoryItem) {
        return CategoryItem.builder()
                .name(categoryItem.getName())
                .description(categoryItem.getDescription())
                .price(categoryItem.getPrice())
                .build();
    }
}
