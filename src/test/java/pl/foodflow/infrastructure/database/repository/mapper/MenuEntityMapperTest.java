package pl.foodflow.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.Menu;
import pl.foodflow.infrastructure.database.entity.MenuEntity;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MenuEntityMapperTest {
    private MenuEntityMapper menuEntityMapper;

    @Test
    void shouldMapMenuToMenuEntity() {
        //given
        Menu menu = TestDataForMappers.someMenu();
        //when
        MenuEntity menuEntity = menuEntityMapper.mapToEntity(menu);
        //then
        assertEquals(menuEntity.getMenuId(), menu.getMenuId());
        assertEquals(menuEntity.getName(), menu.getName());
        assertEquals(menuEntity.getDescription(), menu.getDescription());
    }

    @Test
    void shouldMapMenuEntityToMenu() {
        //given
        MenuEntity menuEntity = TestDataForMappers.someMenuEntity();
        //when
        Menu menu = menuEntityMapper.mapFromEntity(menuEntity);
        //then
        assertEquals(menu.getMenuId(), menuEntity.getMenuId());
        assertEquals(menu.getName(), menuEntity.getName());
        assertEquals(menu.getDescription(), menuEntity.getDescription());
    }
}