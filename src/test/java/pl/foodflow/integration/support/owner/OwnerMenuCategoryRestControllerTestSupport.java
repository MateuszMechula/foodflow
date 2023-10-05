package pl.foodflow.integration.support.owner;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.api.dto.MenuCategoryDTO;

import static pl.foodflow.api.controller.rest.owner.OwnerMenuCategoryRestController.MENU_CATEGORIES;
import static pl.foodflow.api.controller.rest.owner.OwnerMenuCategoryRestController.MENU_CATEGORY_ID;

public interface OwnerMenuCategoryRestControllerTestSupport {
    RequestSpecification requestSpecification();

    default Response addMenuCategory(final MenuCategoryDTO menuCategoryDTO) {
        return requestSpecification()
                .body(menuCategoryDTO)
                .post(MENU_CATEGORIES)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();
    }

    default Response deleteMenuCategory(Long menuCategoryId) {
        return requestSpecification()
                .pathParam("menuCategoryId", menuCategoryId)
                .delete(MENU_CATEGORIES + MENU_CATEGORY_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract()
                .response();
    }
}
