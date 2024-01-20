package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.api.dto.mapper.MenuCategoryMapper;
import pl.foodflow.business.MenuCategoryService;
import pl.foodflow.business.MenuService;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.owner.OwnerMenuCategoryController.*;
import static pl.foodflow.util.TestDataFactory.*;

@WebMvcTest(OwnerMenuCategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerMenuCategoryControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private MenuService menuService;
    @MockBean
    private UserService userService;
    @MockBean
    private MenuCategoryMapper menuCategoryMapper;
    @MockBean
    private MenuCategoryService menuCategoryService;

    @Test
    void menuCategorySectionWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();
        Menu menu = owner.getRestaurant().getMenu();
        List<MenuCategory> menuCategories = List.of(someMenuCategory1());

        when(userService.getCurrentOwner()).thenReturn(owner);
        when(menuCategoryService.findAllCategoriesByMenuId(menu.getMenuId()))
                .thenReturn(menuCategories);
        //when,then
        mockMvc.perform(MockMvcRequestBuilders.get(OWNER + CATEGORY))
                .andExpect(status().isOk())
                .andExpect(view().name("owner_menu_category"))
                .andExpect(model().attributeExists("categoryDTO", "allMenuCategories"));
    }

    @Test
    void addMenuCategoryWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();
        MenuCategoryDTO menuCategoryDTO = someMenuCategoryDTO();
        MenuCategory menuCategory = someMenuCategory1();

        when(userService.getCurrentOwner()).thenReturn(owner);
        when(menuCategoryMapper.map(menuCategoryDTO)).thenReturn(menuCategory);
        //when,then
        mockMvc.perform(post(OWNER + CATEGORY)
                        .flashAttr("categoryDTO", menuCategoryDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + CATEGORY));
        verify(menuService).addMenuCategoryToMenu(owner, menuCategory);
    }

    @Test
    void deleteMenuCategoryWorksCorrectly() throws Exception {
        //given
        Long menuCategoryIdToDelete = 1L;

        //when,then
        mockMvc.perform(post(OWNER + DELETE_CATEGORY)
                        .param("menuCategoryId", String.valueOf(menuCategoryIdToDelete)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + CATEGORY));
        verify(menuCategoryService).deleteMenuCategoryById(menuCategoryIdToDelete);
    }
}