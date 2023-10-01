package pl.foodflow.integration.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;
import pl.foodflow.infrastructure.database.repository.mapper.MenuCategoryEntityMapper;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MenuCategoryEntityMapperTest {
    private MenuCategoryEntityMapper menuCategoryEntityMapper;

    @Test
    void shouldMapMenuCategoryToMenuCategoryEntity() {
        //given
        MenuCategory menuCategory = TestDataForMappers.someMenuCategory();
        //when
        MenuCategoryEntity menuCategoryEntity = menuCategoryEntityMapper.mapToEntity(menuCategory);
        //then
        assertEquals(menuCategoryEntity.getMenuCategoryId(), menuCategory.getMenuCategoryId());
        assertEquals(menuCategoryEntity.getName(), menuCategory.getName());
        assertEquals(menuCategoryEntity.getDescription(), menuCategory.getDescription());
    }

    @Test
    void shouldMapMenuCategoryEntityToMenuCategory() {
        //given
        MenuCategoryEntity menuCategoryEntity = TestDataForMappers.someMenuCategoryEntity();
        //when
        MenuCategory menuCategory = menuCategoryEntityMapper.mapFromEntity(menuCategoryEntity);
        //then
        assertEquals(menuCategory.getMenuCategoryId(), menuCategoryEntity.getMenuCategoryId());
        assertEquals(menuCategory.getName(), menuCategoryEntity.getName());
        assertEquals(menuCategory.getDescription(), menuCategoryEntity.getDescription());
    }
}