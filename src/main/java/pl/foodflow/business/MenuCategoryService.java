package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import pl.foodflow.utils.ErrorMessages;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryDAO menuCategoryDAO;
    private final CategoryItemService categoryItemService;

    public List<MenuCategory> findAllByMenuCategoryId(Long menuCategoryId) {
        log.info("Fetching all MenuCategories by menuCategoryId: {}", menuCategoryId);
        return menuCategoryDAO.findAllByMenuCategoryId(menuCategoryId);
    }

    @Transactional
    public void saveMenuCategory(MenuCategory menuCategory) {
        log.info("Saving Menu Category");
        menuCategoryDAO.saveMenuCategory(menuCategory);
    }

    @Transactional
    public void deleteMenuCategoryById(Long menuCategoryId) {
        log.info("Deleting MenuCategory by ID: {}", menuCategoryId);
        menuCategoryDAO.deleteMenuCategoryById(menuCategoryId);
    }

    @Transactional
    public void addCategoryItemToMenuCategory(
            Long menuCategoryId,
            Owner owner,
            CategoryItem categoryItem,
            MultipartFile imageFile) throws IOException {

        validateAddItemToMenuCategory(owner, menuCategoryId, imageFile);
        log.info("Adding CategoryItem to MenuCategory by menuCategoryId: {}", menuCategoryId);

        MenuCategory menuCategory = findMenuCategoryOrThrow(menuCategoryId, owner);

        String url = uploadImage(imageFile);
        Menu menu = owner.getRestaurant().getMenu();

        CategoryItem updatedCategoryItem = createCategoryItem(categoryItem, menuCategory, url);
        Set<CategoryItem> categoryItems = menuCategory.getCategories();
        categoryItems.add(updatedCategoryItem);

        MenuCategory updatedMenuCategory = menuCategory.withCategoryItems(categoryItems).withMenu(menu);
        categoryItemService.saveCategoryItem(updatedCategoryItem.withMenuCategory(updatedMenuCategory));
        saveMenuCategory(updatedMenuCategory);
    }

    @Transactional
    public void deleteCategoryItemFromMenuCategory(Long categoryItemId) {
        log.info("Deleting CategoryItem from MenuCategory by categoryItemId: {}", categoryItemId);
        categoryItemService.deleteCategoryItemById(categoryItemId);
    }

    @Transactional
    public String uploadImage(MultipartFile imageFile) throws IOException {
        log.info("Uploading image");
        return saveImageToFileSystem(imageFile);
    }

    private static MenuCategory findMenuCategoryOrThrow(Long menuCategoryId, Owner owner) {
        log.info("Fetching MenuCategory by ID: {}", menuCategoryId);
        return owner.getRestaurant().getMenu().getMenuCategories().stream()
                .filter(category -> category.getMenuCategoryId().equals(menuCategoryId))
                .findAny().orElseThrow(() -> new MenuCategoryNotFoundException
                        (ErrorMessages.MENU_CATEGORY_NOT_FOUND.formatted(menuCategoryId)));
    }

    private void validateAddItemToMenuCategory(
            Owner owner,
            Long menuCategoryId,
            MultipartFile imageFile) {

        if (Objects.isNull(owner.getRestaurant().getMenu())) {
            throw new RestaurantNotFound(ErrorMessages.MENU_NOT_CREATED);
        }
        if (imageFile == null || imageFile.isEmpty()) {
            throw new MissingImageFileException(ErrorMessages.IMAGE_FILE_IS_MISSING);
        }
        if (menuCategoryId == null) {
            throw new MenuCategoryNotFoundException(ErrorMessages.MENU_CATEGORY_NEED_TO_CHOOSE);
        }
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

    private static CategoryItem createCategoryItem(
            CategoryItem categoryItem,
            MenuCategory menuCategory,
            String url) {

        return CategoryItem.builder()
                .categoryItemId(categoryItem != null ? categoryItem.getCategoryItemId() : null)
                .name(Optional.ofNullable(categoryItem).map(CategoryItem::getName).orElse(null))
                .description(Optional.ofNullable(categoryItem).map(CategoryItem::getDescription).orElse(null))
                .price(Optional.ofNullable(categoryItem).map(CategoryItem::getPrice).orElse(null))
                .imageUrl(url)
                .menuCategory(menuCategory)
                .build();
    }
}
