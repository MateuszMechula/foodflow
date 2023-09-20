package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.CategoryItemDAO;
import pl.foodflow.business.exceptions.CategoryItemNotFoundException;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.utils.ErrorMessages;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryItemService {

    private final CategoryItemDAO categoryItemDAO;

    public CategoryItem getCategoryItemById(Long categoryItemId) {
        log.info("Fetching CategoryItem by ID: {}", categoryItemId);
        return categoryItemDAO.findById(categoryItemId)
                .orElseThrow(() -> new CategoryItemNotFoundException
                        (ErrorMessages.CATEGORY_ITEM_NOT_FOUND.formatted(categoryItemId)));
    }

    @Transactional
    public void updateCategoryItemById(CategoryItem categoryItem) {
        requireNonNullId(categoryItem);
        log.info("Updating CategoryItem by ID: {}", categoryItem.getCategoryItemId());
        CategoryItem existingCategoryItem = getCategoryItemById(categoryItem.getCategoryItemId());
        CategoryItem updatedItem = buildUpdatedCategoryItem(categoryItem, existingCategoryItem);
        categoryItemDAO.saveCategoryItem(updatedItem);
    }

    @Transactional
    public void saveCategoryItem(CategoryItem categoryItem) {
        log.info("Saving CategoryItem");
        categoryItemDAO.saveCategoryItem(categoryItem);
    }

    @Transactional
    public void deleteCategoryItemById(Long categoryItemId) {
        log.info("Deleting CategoryItem by ID: {}", categoryItemId);
        categoryItemDAO.deleteCategoryItemById(categoryItemId);
    }

    private void requireNonNullId(CategoryItem categoryItem) {
        if (categoryItem.getCategoryItemId() == null) {
            throw new CategoryItemNotFoundException(ErrorMessages.CATEGORY_ITEM_IS_NULL);
        }
    }

    private static CategoryItem buildUpdatedCategoryItem(
            CategoryItem updatedCategoryItem,
            CategoryItem existingCategoryItem) {

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
}
