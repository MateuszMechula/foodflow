package pl.foodflow.integration.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.infrastructure.database.entity.CategoryItemEntity;
import pl.foodflow.infrastructure.database.repository.mapper.CategoryItemEntityMapper;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CategoryItemEntityMapperTest {
    private CategoryItemEntityMapper categoryItemEntityMapper;

    @Test
    void shouldMapCategoryItemToCategoryItemEntity() {
        //given
        CategoryItem categoryItem = TestDataForMappers.someCategoryItem();

        //when
        CategoryItemEntity categoryItemEntity = categoryItemEntityMapper.mapToEntity(categoryItem);

        //then
        assertEquals(categoryItem.getName(), categoryItemEntity.getName());
        assertEquals(categoryItem.getDescription(), categoryItemEntity.getDescription());
        assertEquals(categoryItem.getPrice(), categoryItemEntity.getPrice());
        assertEquals(categoryItem.getImageUrl(), categoryItemEntity.getImageUrl());
    }

    @Test
    void shouldMapCategoryItemEntityToCategoryItem() {
        //given
        CategoryItemEntity categoryItemEntity = TestDataForMappers.someCategoryItemEntity();
        //when
        CategoryItem categoryItem = categoryItemEntityMapper.mapFromEntity(categoryItemEntity);

        //then
        assertEquals(categoryItemEntity.getName(), categoryItem.getName());
        assertEquals(categoryItemEntity.getDescription(), categoryItem.getDescription());
        assertEquals(categoryItemEntity.getPrice(), categoryItem.getPrice());
        assertEquals(categoryItemEntity.getImageUrl(), categoryItem.getImageUrl());
    }
}