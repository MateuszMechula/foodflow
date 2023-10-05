package pl.foodflow.integration.rest.owner;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.owner.OwnerMenuCategoryRestControllerTestSupport;
import pl.foodflow.util.TestDataFactory;

@TestPropertySource(properties = "test.name=OwnerMenuCategoryControllerRestAssuredIT")
public class OwnerMenuCategoryControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements OwnerMenuCategoryRestControllerTestSupport {

    @Test
    @Tag("owner")
    void shouldAddMenuCategory() {
        //given
        MenuCategoryDTO menuCategoryDTO = TestDataFactory.someMenuCategoryDTO();
        //when
        Response response = addMenuCategory(menuCategoryDTO);
        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @Tag("owner")
    void shouldDeleteMenuCategory() {
        //given
        Long menuCategoryId = 1L;
        //when
        Response response = deleteMenuCategory(menuCategoryId);
        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
