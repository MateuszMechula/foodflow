package pl.foodflow.business.dao;

import pl.foodflow.domain.CategoryItem;

import java.util.Optional;

public interface CategoryItemDAO {
    Optional<CategoryItem> findById(Long categoryId);

    void saveCategoryItem(CategoryItem categoryItem);

    void deleteCategoryItemById(Long categoryItemId);
}
