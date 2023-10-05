package pl.foodflow.integration.support.owner;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.api.dto.RestaurantDTO;

import static pl.foodflow.api.controller.rest.owner.OwnerRestaurantRestController.RESTAURANTS;
import static pl.foodflow.api.controller.rest.owner.OwnerRestaurantRestController.RESTAURANT_ID;

public interface OwnerRestaurantControllerTestSupport {

    RequestSpecification requestSpecification();

    default Response addRestaurant(final RestaurantDTO restaurantDTO) {
        return requestSpecification()
                .body(restaurantDTO)
                .post(RESTAURANTS)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();
    }

    default Response updateRestaurant(final RestaurantDTO restaurantDTO) {
        return requestSpecification()
                .body(restaurantDTO)
                .put(RESTAURANTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();
    }

    default Response deleteRestaurant(final Long restaurantId) {
        return requestSpecification()
                .pathParam("restaurantId", restaurantId)
                .delete(RESTAURANTS + RESTAURANT_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract()
                .response();
    }
}
