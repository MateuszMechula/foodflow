package pl.foodflow.integration.rest.owner;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.owner.OwnerCategoryItemRestControllerTestSupport;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = "test.name=OwnerCategoryItemControllerRestAssuredIT")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OwnerCategoryItemControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements OwnerCategoryItemRestControllerTestSupport {

    @Test
    void shouldFindCategoryItemById() {
        //given
        Long categoryItemId = 2L;
        //when
        CategoryItem categoryItem = getCategoryItem(categoryItemId);
        //then
        assertThat(categoryItem).isNotNull();
    }

    @Test
    void shouldAddCategoryItem() {
    }

    @Test
    void shouldDeleteCategoryItem() {
        // Given
        Long categoryItemIdToDelete = 1L;

        // When
        Response response = deleteCategoryItem(categoryItemIdToDelete);

        // Then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
