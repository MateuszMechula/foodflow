package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.api.dto.MenuDTO;
import pl.foodflow.api.dto.mapper.MenuMapper;
import pl.foodflow.business.MenuService;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.user.UserService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.owner.OwnerMenuController.*;
import static pl.foodflow.util.TestDataFactory.*;

@WebMvcTest(OwnerMenuController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerMenuControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private MenuMapper menuMapper;
    @MockBean
    private MenuService menuService;
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "testOwner", roles = "OWNER")
    void menuSectionWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();

        when(userService.getCurrentOwner()).thenReturn(owner);
        //when,then
        mockMvc.perform(get(OWNER + MENU))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("menuDTO"))
                .andExpect(view().name("owner_menu"));
    }

    @Test
    @WithMockUser(username = "testOwner", roles = "OWNER")
    void addMenuWorksCorrectly() throws Exception {
        //given
        Owner owner = someOwner1();
        Menu menu = someMenu1();
        MenuDTO menuDTO = someMenuDTO();

        when(userService.getCurrentOwner()).thenReturn(owner);
        when(menuMapper.map(menuDTO)).thenReturn(menu);
        //when,then
        mockMvc.perform(post(OWNER + MENU)
                        .flashAttr("menuDTO", menuDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + MENU));
        verify(menuService).createMenuForRestaurant(owner, menu);
    }

    @Test
    void deleteMenuWorksCorrectly() throws Exception {
        //given
        Long menuId = 1L;
        //when,then
        mockMvc.perform(post(OWNER + DELETE_MENU)
                        .param("menuId", String.valueOf(menuId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + MENU));
        verify(menuService).deleteMenu(menuId);
    }
}