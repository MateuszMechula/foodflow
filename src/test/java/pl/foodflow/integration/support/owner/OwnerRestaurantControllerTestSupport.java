package pl.foodflow.integration.support.owner;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.domain.Restaurant;

import static pl.foodflow.api.controller.rest.owner.OwnerRestaurantRestController.*;

public interface OwnerRestaurantControllerTestSupport {

    RequestSpecification requestSpecification();

    default Restaurant getRestaurantById(final Long restaurantId) {
        return requestSpecification()
                .pathParam("restaurantId", restaurantId)
                .get(RESTAURANTS + RESTAURANT_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(Restaurant.class);
    }

    default Response addRestaurant(final RestaurantDTO restaurantDTO, final Long ownerId) {
        return requestSpecification()
                .pathParam("ownerId", ownerId)
                .body(restaurantDTO)
                .post(RESTAURANTS + OWNER_ID)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();
    }

    default Response updateRestaurant(final RestaurantDTO restaurantDTO, final Long ownerId) {
        return requestSpecification()
                .pathParam("ownerId", ownerId)
                .body(restaurantDTO)
                .put(RESTAURANTS + OWNER_ID)
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
