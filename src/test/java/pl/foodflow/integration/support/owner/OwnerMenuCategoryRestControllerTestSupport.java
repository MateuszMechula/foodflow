package pl.foodflow.integration.support.owner;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.domain.MenuCategory;

import static pl.foodflow.api.controller.rest.owner.OwnerMenuCategoryRestController.*;

public interface OwnerMenuCategoryRestControllerTestSupport {
    RequestSpecification requestSpecification();

    default MenuCategory getMenuCategoryById(final Long menuCategoryId) {
        return requestSpecification()
                .pathParam("menuCategoryId", menuCategoryId)
                .get(MENU_CATEGORIES + MENU_CATEGORY_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(MenuCategory.class);
    }

    default Response addMenuCategory(final Long ownerId, final MenuCategoryDTO menuCategoryDTO) {
        return requestSpecification()
                .pathParam("ownerId", ownerId)
                .body(menuCategoryDTO)
                .post(MENU_CATEGORIES + OWNER_ID)
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
