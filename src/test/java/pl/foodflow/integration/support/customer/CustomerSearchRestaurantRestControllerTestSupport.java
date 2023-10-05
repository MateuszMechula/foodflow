package pl.foodflow.integration.support.customer;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import static pl.foodflow.api.controller.rest.customer.CustomerSearchRestaurantRestController.SEARCH_RESTAURANTS;

public interface CustomerSearchRestaurantRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default Response searchRestaurants(
            final String street,
            final String postalCode,
            final String city) {
        return requestSpecification()
                .queryParam("street", street)
                .queryParam("postalCode", postalCode)
                .queryParam("city", city)
                .get(SEARCH_RESTAURANTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

    }
}
