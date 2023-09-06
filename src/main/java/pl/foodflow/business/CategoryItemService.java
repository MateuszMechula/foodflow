package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.CategoryItemDAO;
import pl.foodflow.business.exceptions.CategoryItemNotFoundException;
import pl.foodflow.domain.CategoryItem;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryItemService {

    private final CategoryItemDAO categoryItemDAO;

    public CategoryItem findById(Long categoryItemId) {
        return categoryItemDAO.findById(categoryItemId)
                .orElseThrow(() -> new CategoryItemNotFoundException
                        ("CategoryItem with ID: [%s] not found".formatted(categoryItemId)));
    }

    @Transactional
    public void updateCategoryItem(CategoryItem categoryItem) {
        if (categoryItem.getCategoryItemId() == null) {
            throw new IllegalArgumentException("CategoryItem ID cannot be NULL");
        }
        CategoryItem existingCategoryItem = findById(categoryItem.getCategoryItemId());
        CategoryItem updatedItem = buildUpdatedCategoryItem(categoryItem, existingCategoryItem);

        categoryItemDAO.saveCategoryItem(updatedItem);
    }

    @Transactional
    public void deleteCategoryItem(Long categoryItemId) {
        categoryItemDAO.deleteCategoryItemById(categoryItemId);
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

    public void saveCategoryItem(CategoryItem categoryItem) {
        categoryItemDAO.saveCategoryItem(categoryItem);
    }
}
