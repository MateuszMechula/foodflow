package pl.foodflow.business.dao;

import pl.foodflow.domain.CategoryItem;

import java.util.Optional;

public interface CategoryItemDAO {
    Optional<CategoryItem> findById(Long categoryId);

    CategoryItem saveCategoryItem(CategoryItem categoryItem);
}
