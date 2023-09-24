package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.business.dao.MenuDAO;
import pl.foodflow.business.exceptions.MenuNotFoundException;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.business.exceptions.ThatRestaurantHasAMenu;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static pl.foodflow.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    @InjectMocks
    private MenuService menuService;
    @Mock
    private MenuDAO menuDAO;
    @Mock
    private MenuCategoryService menuCategoryService;

    @Test
    void shouldGetMenuById() {
        //given
        Long menuId = 1L;
        Menu menu = someMenu1();

        when(menuDAO.findMenuById(menuId)).thenReturn(Optional.of(menu));
        //when
        Menu result = menuService.getMenuById(menuId);
        //then
        assertNotNull(result);
        assertEquals(menuId, result.getMenuId());
        assertEquals(menu.getName(), result.getName());
    }

    @Test
    void shouldThrowExceptionWhenMenuIdDoesNotExist() {
        //given
        Long menuId = 1L;
        when(menuDAO.findMenuById(menuId)).thenReturn(Optional.empty());

        //when,then
        assertThrows(MenuNotFoundException.class, () -> menuService.getMenuById(menuId));
        verify(menuDAO).findMenuById(menuId);
    }

    @Test
    void addMenuCategoryToMenuSuccessfully() {
        //given
        Long menuId = 1L;
        Owner owner = someOwner1();
        Menu menu = someMenu1();
        MenuCategory menuCategory = someMenuCategory1();

        when(menuDAO.findMenuById(menuId)).thenReturn(Optional.of(menu));
        //when
        menuService.addMenuCategoryToMenu(owner, menuCategory);
        //then
        verify(menuDAO).findMenuById(menuId);
        verify(menuCategoryService).saveMenuCategory(menuCategory);
        verify(menuDAO).saveMenu(menu);
    }

    @Test
    public void addMenuCategoryToMenuWhenRestaurantNotCreated() {
        // given
        Owner owner = someOwnerWithoutRestaurant1();
        MenuCategory menuCategory = someMenuCategory1();

        //when,then
        assertThrows(RestaurantNotFound.class, () -> menuService.addMenuCategoryToMenu(owner, menuCategory));
        verify(menuDAO, never()).findMenuById(anyLong());
        verify(menuCategoryService, never()).saveMenuCategory(any());
        verify(menuDAO, never()).saveMenu(any());
    }

    @Test
    public void createMenuForRestaurantWhenValid() {
        // given
        Owner owner = someOwner2();
        Menu menu = someMenu1();

        // when
        menuService.createMenuForRestaurant(owner, menu);

        // then
        verify(menuDAO).saveMenu(menu);
    }

    @Test
    public void shouldThrowExceptionWhenRestaurantAlreadyHasMenu() {
        // given
        Owner owner = someOwner1();
        Menu menu = someMenu1();

        //when,then
        assertThrows(ThatRestaurantHasAMenu.class, () -> menuService.createMenuForRestaurant(owner, menu));
        verify(menuDAO, never()).saveMenu(any());
    }

    @Test
    public void shouldThrowExceptionWhenRestaurantDoesNotExist() {
        // given
        Owner owner = someOwnerWithoutRestaurant1();
        Menu menu = someMenu1();

        //when,then
        assertThrows(RestaurantNotFound.class, () -> menuService.createMenuForRestaurant(owner, menu));
        verify(menuDAO, never()).saveMenu(any());
    }

    @Test
    void shouldSaveMenu() {
        //given
        Menu menu = someMenu1();
        //when
        menuService.saveMenu(menu);
        //then
        verify(menuDAO).saveMenu(menu);
    }

    @Test
    void shouldDeleteMenu() {
        //given
        Menu menu = someMenu1();
        //when
        menuService.deleteMenu(menu.getMenuId());
        //then
        verify(menuDAO).deleteMenuById(menu.getMenuId());
    }
}