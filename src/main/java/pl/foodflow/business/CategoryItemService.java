package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.foodflow.business.dao.CategoryItemDAO;
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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryItemService {

    private final MenuCategoryService menuCategoryService;
    private final CategoryItemDAO categoryItemDAO;

    @Transactional
    public CategoryItem findById(Long categoryItemId) {
        return categoryItemDAO.findById(categoryItemId).orElseThrow();
    }

    @Transactional
    public void updateCategoryItem(CategoryItem updatedCategoryItem) {
        if (updatedCategoryItem.getCategoryItemId() == null) {
            throw new IllegalArgumentException("CategoryItem ID cannot be NULL");
        }
        CategoryItem existingCategoryItem = categoryItemDAO.findById(updatedCategoryItem.getCategoryItemId()).orElseThrow(() ->
                new EntityNotFoundException(
                        "CategoryItem with ID [%s] doesnt exists.".formatted(updatedCategoryItem.getCategoryItemId())));

        CategoryItem updatedItem = buildUpdatedCategoryItem(updatedCategoryItem, existingCategoryItem);

        categoryItemDAO.saveCategoryItem(updatedItem);
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

        MenuCategory menuCategory = owner.getRestaurant().getMenu().getMenuCategories().stream()
                .filter(category -> category.getMenuCategoryId().equals(menuCategoryId))
                .findAny().orElseThrow(() -> new EntityNotFoundException("MenuCategory with id: [%s] not found".formatted(menuCategoryId)));

        String url = uploadImage(imageFile);
        Menu menu = owner.getRestaurant().getMenu();

        CategoryItem updatedCategoryItem = buildCategoryItem(categoryItem, menuCategory, url);

        Set<CategoryItem> categoryItems = menuCategory.getCategories();
        categoryItems.add(updatedCategoryItem);

        MenuCategory updatedMenuCategory = menuCategory.withCategoryItems(categoryItems).withMenu(menu);
        categoryItemDAO.saveCategoryItem(updatedCategoryItem.withMenuCategory(updatedMenuCategory));
        menuCategoryService.saveMenuCategory(updatedMenuCategory);
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

    private static CategoryItem buildUpdatedCategoryItem(CategoryItem updatedCategoryItem, CategoryItem existingCategoryItem) {
        return CategoryItem.builder()
                .categoryItemId(existingCategoryItem.getCategoryItemId())
                .name(Optional.ofNullable(updatedCategoryItem.getName()).orElse(existingCategoryItem.getName()))
                .description(Optional.ofNullable(updatedCategoryItem.getDescription()).orElse(existingCategoryItem.getDescription()))
                .price(Optional.ofNullable(updatedCategoryItem.getPrice()).orElse(existingCategoryItem.getPrice()))
                .imageUrl(Optional.ofNullable(updatedCategoryItem.getImageUrl()).orElse(existingCategoryItem.getImageUrl()))
                .menuCategory(Optional.ofNullable(updatedCategoryItem.getMenuCategory()).orElse(existingCategoryItem.getMenuCategory()))
                .orderItems(Optional.ofNullable(updatedCategoryItem.getOrderItems()).orElse(existingCategoryItem.getOrderItems()))
                .build();
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
