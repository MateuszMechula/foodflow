package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import pl.foodflow.business.dao.MenuCategoryDAO;
import pl.foodflow.business.exceptions.MenuCategoryNotFoundException;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static pl.foodflow.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class MenuCategoryServiceTest {
    @InjectMocks
    private MenuCategoryService menuCategoryService;
    @Mock
    private CategoryItemService categoryItemService;
    @Mock
    private MenuCategoryDAO menuCategoryDAO;
    @Mock
    private MultipartFile imageFile;

    @Test
    void shouldThrowExceptionWhenMenuCategoryDoesNotExists() {
        //given
        Long menuCategoryId = 1L;
        when(menuCategoryDAO.findMenuCategoryById(menuCategoryId)).thenReturn(Optional.empty());

        //when,then
        assertThrows(MenuCategoryNotFoundException.class, () ->
                menuCategoryService.findMenuCategoryById(menuCategoryId));
        verify(menuCategoryDAO).findMenuCategoryById(menuCategoryId);
    }

    @Test
    void shouldFindAllMenuCategoriesById() {
        //given
        Menu menu = someMenu1();
        var menuCategories = List.of(someMenuCategory1(), someMenuCategory2(), someMenuCategory3());

        when(menuCategoryService.findAllCategoriesByMenuId(menu.getMenuId()))
                .thenReturn(menuCategories);
        //when
        List<MenuCategory> allMenuCategories = menuCategoryService.findAllCategoriesByMenuId(menu.getMenuId());
        //then
        assertThat(allMenuCategories).hasSize(3);
    }

    @Test
    void shouldSaveMenuCategory() {
        //given
        MenuCategory menuCategory1 = someMenuCategory1();
        //when
        menuCategoryService.saveMenuCategory(menuCategory1);
        //then
        verify(menuCategoryDAO, times(1)).saveMenuCategory(menuCategory1);
    }

    @Test
    void shouldDeleteMenuCategoryById() {
        //given
        Long menuCategoryId = 1L;
        //when
        menuCategoryService.deleteMenuCategoryById(menuCategoryId);
        //then
        verify(menuCategoryDAO).deleteMenuCategoryById(menuCategoryId);
    }

    @Test
    void shouldAddCategoryItemToMenuCategory() throws IOException {
        //given
        Long menuCategoryId = 1L;
        Owner owner = someOwner1();
        MenuCategory menuCategory = someMenuCategory1();
        CategoryItem categoryItem = someCategoryItem1();

        when(menuCategoryDAO.findMenuCategoryById(menuCategoryId)).thenReturn(Optional.of(menuCategory));
        doNothing().when(menuCategoryDAO).saveMenuCategory(any());

        MenuCategoryService spyMenuCategoryService = spy(menuCategoryService);

        doReturn("url").when(spyMenuCategoryService).uploadImage(imageFile);

        //when
        spyMenuCategoryService.addCategoryItemToMenuCategory(menuCategoryId, owner, categoryItem, imageFile);
        //then
        verify(menuCategoryDAO).findMenuCategoryById(menuCategoryId);
        verify(spyMenuCategoryService).uploadImage(imageFile);
        verify(categoryItemService).saveCategoryItem(any());
    }
}