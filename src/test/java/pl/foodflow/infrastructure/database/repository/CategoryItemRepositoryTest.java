package pl.foodflow.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.infrastructure.database.entity.CategoryItemEntity;
import pl.foodflow.infrastructure.database.repository.jpa.CategoryItemJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.CategoryItemEntityMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someCategoryItem1;
import static pl.foodflow.util.TestDataFactory.someCategoryItemEntity1;

@ExtendWith(MockitoExtension.class)
class CategoryItemRepositoryTest {
    @InjectMocks
    private CategoryItemRepository categoryItemRepository;
    @Mock
    private CategoryItemJpaRepository categoryItemJpaRepository;
    @Mock
    private CategoryItemEntityMapper categoryItemEntityMapper;


    @Test
    void shouldFindCategoryItemById() {
        //given
        Long categoryItemId = 1L;
        CategoryItem categoryItem = someCategoryItem1();
        CategoryItemEntity categoryItemEntity = someCategoryItemEntity1();

        when(categoryItemJpaRepository.findById(categoryItemId)).thenReturn(Optional.ofNullable(categoryItemEntity));
        when(categoryItemEntityMapper.mapFromEntity(categoryItemEntity)).thenReturn(categoryItem);
        //when
        Optional<CategoryItem> categoryItemFound = categoryItemRepository.findCategoryItemById(categoryItemId);
        //then
        assertTrue(categoryItemFound.isPresent());
        assertEquals(categoryItem, categoryItemFound.get());

        verify(categoryItemJpaRepository).findById(categoryItemId);
        verify(categoryItemEntityMapper).mapFromEntity(categoryItemEntity);
    }

    @Test
    void shouldSaveCategoryItem() {
        CategoryItem categoryItem = someCategoryItem1();
        CategoryItemEntity categoryItemEntity = someCategoryItemEntity1();

        when(categoryItemEntityMapper.mapToEntity(categoryItem)).thenReturn(categoryItemEntity);
        when(categoryItemJpaRepository.save(categoryItemEntity)).thenReturn(categoryItemEntity);
        when(categoryItemEntityMapper.mapFromEntity(categoryItemEntity)).thenReturn(categoryItem);
        //when
        categoryItemRepository.saveCategoryItem(categoryItem);
        //then
        verify(categoryItemEntityMapper).mapToEntity(categoryItem);
        verify(categoryItemJpaRepository).save(categoryItemEntity);
        verify(categoryItemEntityMapper).mapFromEntity(categoryItemEntity);
    }

    @Test
    void shouldDeleteCategoryItemById() {
        //given
        Long categoryItemId = 1L;
        //when
        categoryItemRepository.deleteCategoryItemById(categoryItemId);
        //then
        verify(categoryItemJpaRepository).deleteById(categoryItemId);
    }
}