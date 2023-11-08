package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.api.dto.CategoryItemDTO;
import pl.foodflow.api.dto.mapper.CategoryItemMapper;
import pl.foodflow.business.CategoryItemService;
import pl.foodflow.business.MenuCategoryService;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.user.UserService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.owner.OwnerCategoryItemController.*;
import static pl.foodflow.util.TestDataFactory.*;

@WebMvcTest(OwnerCategoryItemController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerCategoryItemControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private MenuCategoryService menuCategoryService;
    @MockBean
    private CategoryItemService categoryItemService;
    @MockBean
    private CategoryItemMapper categoryItemMapper;

    @Test
    void showCreateCategoryItemFormWorksCorrectly() throws Exception {
        // given
        Owner owner = someOwner1();
        when(userService.getCurrentOwner()).thenReturn(owner);

        // when and Then
        mockMvc.perform(get(OWNER + CATEGORY_ITEM))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categoryItemDTO"))
                .andExpect(model().attributeExists("allCategories"))
                .andExpect(model().attributeExists("menuCategoryItems"))
                .andExpect(view().name("owner_category_item"));
    }

    @Test
    void addCategoryItemWorksCorrectly() throws Exception {
        //given
        Long menuCategoryId = 1L;
        Owner owner = someOwner1();
        CategoryItemDTO categoryItemDTO = someCategoryItemDTO();
        CategoryItem categoryItem = someCategoryItem1();
        MockMultipartFile imageFile = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg",
                "Test Image".getBytes());

        when(userService.getCurrentOwner()).thenReturn(owner);
        when(categoryItemMapper.map(categoryItemDTO)).thenReturn(categoryItem);
        //when,then
        mockMvc.perform(multipart(OWNER + CATEGORY_ITEM)
                        .file(imageFile)
                        .param("menuCategory", String.valueOf(menuCategoryId))
                        .flashAttr("categoryItemDTO", categoryItemDTO)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + CATEGORY_ITEM));
        verify(menuCategoryService).addCategoryItemToMenuCategory(menuCategoryId, owner, categoryItem, imageFile);
    }

    @Test
    void deleteCategoryItemWorksCorrectly() throws Exception {
        //given
        Long categoryItemIdToDelete = 1L;

        //when,then
        mockMvc.perform(post(OWNER + DELETE_CATEGORY_ITEM)
                        .param("categoryItemId", String.valueOf(categoryItemIdToDelete)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(OWNER + CATEGORY_ITEM));

        verify(categoryItemService).deleteCategoryItemById(categoryItemIdToDelete);

    }
}