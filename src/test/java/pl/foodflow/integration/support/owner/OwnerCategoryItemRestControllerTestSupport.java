package pl.foodflow.integration.support.owner;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.domain.CategoryItem;

import static pl.foodflow.api.controller.rest.owner.OwnerCategoryItemRestController.CATEGORY_ITEMS;
import static pl.foodflow.api.controller.rest.owner.OwnerCategoryItemRestController.CATEGORY_ITEM_ID;

public interface OwnerCategoryItemRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default CategoryItem getCategoryItem(final Long categoryItemId) {
        return requestSpecification()
                .pathParam("categoryItemId", categoryItemId)
                .get(CATEGORY_ITEMS + CATEGORY_ITEM_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(CategoryItem.class);
    }

    default Response deleteCategoryItem(final Long categoryItemId) {
        return requestSpecification()
                .pathParam("categoryItemId", categoryItemId)
                .delete(CATEGORY_ITEMS + CATEGORY_ITEM_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract()
                .response();
    }


}
