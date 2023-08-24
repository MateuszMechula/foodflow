package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.foodflow.business.dao.CategoryItemDAO;
import pl.foodflow.business.dao.MenuCategoryDAO;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryItemService {

    private final MenuCategoryDAO menuCategoryDAO;
    private final MenuCategoryService menuCategoryService;
    private final CategoryItemDAO categoryItemDAO;
    private final ItemImageService itemImageService;

    @Transactional
    public void addItemToMenuCategory(
            Long menuCategoryId,
            Owner owner,
            CategoryItem categoryItem,
            MultipartFile imageFile) throws IOException
    {

        if (Objects.isNull(owner.getRestaurant().getMenu())) {
            throw new RestaurantNotFound("To add a category you have to create menu first");
        }

        MenuCategory menuCategory = menuCategoryDAO.findCategoryById(menuCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("MenuCategory with id: [%s] not found".formatted(menuCategoryId)));

        CategoryItem categoryItemSaved = categoryItemDAO.saveCategoryItem(categoryItem.withMenuCategory(menuCategory));

        Menu menu = owner.getRestaurant().getMenu();
        Set<CategoryItem> categories = menuCategory.getCategories();
        categories.add(categoryItemSaved.withMenuCategory(menuCategory));

        MenuCategory updatedMenuCategory = menuCategoryDAO.saveMenuCategory(menuCategory.withCategoryItems(categories))
                .withMenu(menu);

        CategoryItem newCategoryItem = categoryItemSaved.withMenuCategory(updatedMenuCategory);
        System.out.println(newCategoryItem);

        itemImageService.uploadImage(categoryItemSaved.withMenuCategory(updatedMenuCategory), imageFile);
    }
}
