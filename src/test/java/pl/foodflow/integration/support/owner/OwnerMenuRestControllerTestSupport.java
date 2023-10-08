package pl.foodflow.integration.support.owner;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.api.dto.MenuDTO;

import static pl.foodflow.api.controller.rest.owner.OwnerMenuRestController.*;

public interface OwnerMenuRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default Response addMenu(final MenuDTO menuDTO, final Long ownerId) {
        return requestSpecification()
                .pathParam("ownerId", ownerId)
                .body(menuDTO)
                .post(MENUS + OWNER_ID)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();
    }

    default Response deleteMenu(final Long menuId) {
        return requestSpecification()
                .pathParam("menuId", menuId)
                .delete(MENUS + MENU_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract()
                .response();
    }
}
