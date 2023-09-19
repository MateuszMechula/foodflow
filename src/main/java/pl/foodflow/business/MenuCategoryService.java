package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.foodflow.business.dao.MenuCategoryDAO;
import pl.foodflow.business.exceptions.MenuCategoryNotFoundException;
import pl.foodflow.business.exceptions.MissingImageFileException;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryDAO menuCategoryDAO;
    private final CategoryItemService categoryItemService;

    public MenuCategory findById(Long menuCategoryId) {
        return menuCategoryDAO.findCategoryById(menuCategoryId)
                .orElseThrow(() -> new MenuCategoryNotFoundException
                        ("MenuCategory with ID: [%s] not found".formatted(menuCategoryId)));
    }

    public List<MenuCategory> findAllByMenuId(Long menuId) {
        return menuCategoryDAO.findAllByMenuId(menuId);
    }

    @Transactional
    public void saveMenuCategory(MenuCategory menuCategory) {
        menuCategoryDAO.saveMenuCategory(menuCategory);
    }

    @Transactional
    public void deleteMenuCategoryById(Long menuCategoryId) {
        menuCategoryDAO.deleteMenuCategoryById(menuCategoryId);
    }

    @Transactional
    public void addItemToMenuCategory(
            Long menuCategoryId,
            Owner owner,
            CategoryItem categoryItem,
            MultipartFile imageFile) throws IOException {

        if (Objects.isNull(owner.getRestaurant().getMenu())) {
            throw new RestaurantNotFound("To add a category you have to create menu first");
        }
        if (imageFile == null || imageFile.isEmpty()) {
            throw new MissingImageFileException("Image file is empty or missing");
        }
        if (menuCategoryId == null) {
            throw new MenuCategoryNotFoundException("You need to choose category");
        }

        MenuCategory menuCategory = owner.getRestaurant().getMenu().getMenuCategories().stream()
                .filter(category -> category.getMenuCategoryId().equals(menuCategoryId))
                .findAny().orElseThrow(() -> new MenuCategoryNotFoundException("MenuCategory with id: [%s] not found"
                        .formatted(menuCategoryId)));

        String url = uploadImage(imageFile);
        Menu menu = owner.getRestaurant().getMenu();

        CategoryItem updatedCategoryItem = buildCategoryItem(categoryItem, menuCategory, url);

        Set<CategoryItem> categoryItems = menuCategory.getCategories();
        categoryItems.add(updatedCategoryItem);

        MenuCategory updatedMenuCategory = menuCategory.withCategoryItems(categoryItems).withMenu(menu);
        categoryItemService.saveCategoryItem(updatedCategoryItem.withMenuCategory(updatedMenuCategory));
        saveMenuCategory(updatedMenuCategory);
    }

    @Transactional
    public void deleteCategoryItemFromMenuCategory(Long categoryItemId) {
        categoryItemService.deleteCategoryItem(categoryItemId);
    }

    @Transactional
    public String uploadImage(MultipartFile imageFile) throws IOException {
        return saveImageToFileSystem(imageFile);
    }

    private String saveImageToFileSystem(MultipartFile imageFile) throws IOException {
        String uploadDir = "src/main/resources/static/images";
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        Files.createDirectories(filePath.getParent());

        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(imageFile.getBytes());
        }

        return fileName;
    }

    private static CategoryItem buildCategoryItem(CategoryItem categoryItem, MenuCategory menuCategory, String url) {
        return CategoryItem.builder()
                .categoryItemId(categoryItem.getCategoryItemId())
                .name(categoryItem.getName())
                .description(categoryItem.getDescription())
                .price(categoryItem.getPrice())
                .imageUrl(url)
                .menuCategory(menuCategory)
                .build();
    }
}
