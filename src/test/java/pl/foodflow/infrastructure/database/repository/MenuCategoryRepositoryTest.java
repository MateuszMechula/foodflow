package pl.foodflow.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;
import pl.foodflow.infrastructure.database.repository.jpa.MenuCategoryJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.MenuCategoryEntityMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.foodflow.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class MenuCategoryRepositoryTest {
    @InjectMocks
    private MenuCategoryRepository menuCategoryRepository;
    @Mock
    private MenuCategoryJpaRepository menuCategoryJpaRepository;
    @Mock
    private MenuCategoryEntityMapper menuCategoryEntityMapper;

    @Test
    void shouldFindMenuCategoryById() {
        //given
        Long menuCategoryId = 1L;
        MenuCategory menuCategory = someMenuCategory1();
        MenuCategoryEntity menuCategoryEntity = someMenuCategoryEntity1();

        when(menuCategoryJpaRepository.findById(menuCategoryId)).thenReturn(Optional.of(menuCategoryEntity));
        when(menuCategoryEntityMapper.mapFromEntity(menuCategoryEntity)).thenReturn(menuCategory);
        //when
        Optional<MenuCategory> foundMenuCategory = menuCategoryRepository.findMenuCategoryById(menuCategoryId);
        //then
        assertThat(foundMenuCategory)
                .isPresent()
                .contains(menuCategory);

        verify(menuCategoryJpaRepository).findById(menuCategoryId);
        verify(menuCategoryEntityMapper).mapFromEntity(menuCategoryEntity);
    }

    @Test
    void shouldFindAllCategoriesByMenuId() {
        //given
        Long menuId = 1L;
        var categoryEntities = List.of(someMenuCategoryEntity1(), someMenuCategoryEntity2(), someMenuCategoryEntity3());
        var expectedCategories = List.of(someMenuCategory1(), someMenuCategory2(), someMenuCategory3());

        when(menuCategoryJpaRepository.findAllByMenuMenuId(menuId)).thenReturn(categoryEntities);
        when(menuCategoryEntityMapper.mapFromEntity(any()))
                .thenAnswer(invocation -> {
                    MenuCategoryEntity entity = invocation.getArgument(0);
                    int index = categoryEntities.indexOf(entity);
                    return expectedCategories.get(index);
                });
        //when
        List<MenuCategory> categories = menuCategoryRepository.findAllCategoriesByMenuId(menuId);
        //then
        assertThat(categories)
                .isNotNull()
                .hasSize(expectedCategories.size());

        verify(menuCategoryJpaRepository).findAllByMenuMenuId(menuId);
        verify(menuCategoryEntityMapper, times(categoryEntities.size())).mapFromEntity(any());
    }

    @Test
    void shouldSaveMenuCategory() {
        //given
        MenuCategory menuCategory = someMenuCategory1();
        MenuCategoryEntity menuCategoryEntity = someMenuCategoryEntity1();

        when(menuCategoryEntityMapper.mapToEntity(menuCategory)).thenReturn(menuCategoryEntity);
        when(menuCategoryJpaRepository.save(menuCategoryEntity)).thenReturn(menuCategoryEntity);
        when(menuCategoryEntityMapper.mapFromEntity(menuCategoryEntity)).thenReturn(menuCategory);
        //when
        menuCategoryRepository.saveMenuCategory(menuCategory);
        //then
        verify(menuCategoryEntityMapper).mapToEntity(menuCategory);
        verify(menuCategoryJpaRepository).save(menuCategoryEntity);
        verify(menuCategoryEntityMapper).mapFromEntity(menuCategoryEntity);
    }

    @Test
    void shouldDeleteMenuCategoryById() {
        //given
        Long menuCategoryId = 1L;

        //when
        menuCategoryRepository.deleteMenuCategoryById(menuCategoryId);
        //then
        verify(menuCategoryJpaRepository).deleteById(menuCategoryId);
    }
}