package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.business.dao.CategoryItemDAO;
import pl.foodflow.business.exceptions.CategoryItemNotFoundException;
import pl.foodflow.domain.CategoryItem;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someCategoryItem1;
import static pl.foodflow.util.TestDataFactory.someCategoryItem2;

@ExtendWith(MockitoExtension.class)
class CategoryItemServiceTest {

    @InjectMocks
    private CategoryItemService categoryItemService;
    @Mock
    private CategoryItemDAO categoryItemDAO;

    @Test
    void shouldGetCategoryItemByIdIfExists() {
        //given
        CategoryItem categoryItem = someCategoryItem1();

        when(categoryItemDAO.findCategoryItemById(categoryItem.getCategoryItemId()))
                .thenReturn(Optional.of(categoryItem));
        //when
        CategoryItem expected = categoryItemService.getCategoryItemById(categoryItem.getCategoryItemId());
        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getCategoryItemId()).isEqualTo(categoryItem.getCategoryItemId());
    }

    @Test
    void shouldThrowExceptionWhenCategoryItemByIdDoesNotExist() {
        //given
        Long categoryId = 1L;
        when(categoryItemDAO.findCategoryItemById(categoryId)).thenReturn(Optional.empty());

        //when,then
        assertThrows(CategoryItemNotFoundException.class, () -> categoryItemService.getCategoryItemById(categoryId));
        verify(categoryItemDAO).findCategoryItemById(categoryId);
    }

    @Test
    void shouldUpdateCategoryItemById() {
        //given
        CategoryItem categoryItem = someCategoryItem1();
        CategoryItem existingCategoryItem = someCategoryItem2();

        when(categoryItemDAO.findCategoryItemById(categoryItem.getCategoryItemId()))
                .thenReturn(Optional.ofNullable(existingCategoryItem));
        //when
        categoryItemService.updateCategoryItemById(categoryItem);

        //then
        verify(categoryItemDAO).saveCategoryItem(categoryItem);
    }

    @Test
    void saveCategoryItem() {
        //given
        CategoryItem categoryItem = someCategoryItem1();

        //when
        categoryItemService.saveCategoryItem(categoryItem);

        //then
        verify(categoryItemDAO).saveCategoryItem(categoryItem);
    }

    @Test
    void deleteCategoryItemById() {
        //given
        Long categoryId = 1L;
        CategoryItem categoryItem = someCategoryItem1();

        //when
        categoryItemService.deleteCategoryItemById(categoryId);
        //then
        verify(categoryItemDAO).deleteCategoryItemById(categoryItem.getCategoryItemId());
    }
}