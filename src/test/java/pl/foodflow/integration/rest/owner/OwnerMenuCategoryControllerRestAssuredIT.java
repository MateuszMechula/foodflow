package pl.foodflow.integration.rest.owner;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.owner.OwnerMenuCategoryRestControllerTestSupport;
import pl.foodflow.util.TestDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = "test.name=OwnerMenuCategoryControllerRestAssuredIT")
public class OwnerMenuCategoryControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements OwnerMenuCategoryRestControllerTestSupport {

    @Test
    void shouldGetMenuCategoryById() {
        //given
        Long menuCategoryId = 1L;
        //when
        MenuCategory menuCategory = getMenuCategoryById(menuCategoryId);
        //then
        assertThat(menuCategory).isNotNull();
    }

    @Test
    void shouldAddMenuCategory() {
        //given
        Long ownerId = 1L;
        MenuCategoryDTO menuCategoryDTO = TestDataFactory.someMenuCategoryDTO();
        //when
        Response response = addMenuCategory(ownerId, menuCategoryDTO);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void shouldDeleteMenuCategory() {
        //given
        Long menuCategoryId = 1L;
        //when
        Response response = deleteMenuCategory(menuCategoryId);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
