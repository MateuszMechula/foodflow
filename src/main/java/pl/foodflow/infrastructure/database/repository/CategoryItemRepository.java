package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.CategoryItemDAO;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.infrastructure.database.entity.CategoryItemEntity;
import pl.foodflow.infrastructure.database.repository.jpa.CategoryItemJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.CategoryItemEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryItemRepository implements CategoryItemDAO {

    private final CategoryItemJpaRepository categoryItemJpaRepository;
    private final CategoryItemEntityMapper categoryItemEntityMapper;

    @Override
    public Optional<CategoryItem> findCategoryItemById(Long categoryId) {
        return categoryItemJpaRepository.findById(categoryId)
                .map(categoryItemEntityMapper::mapFromEntity);
    }

    @Override
    public void saveCategoryItem(CategoryItem categoryItem) {
        CategoryItemEntity toSave = categoryItemEntityMapper.mapToEntity(categoryItem);
        CategoryItemEntity saved = categoryItemJpaRepository.save(toSave);
        categoryItemEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void deleteCategoryItemById(Long categoryItemId) {
        categoryItemJpaRepository.deleteById(categoryItemId);
    }
}
