package pl.foodflow.integration.support.owner;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.api.dto.AddressDTO;

import static pl.foodflow.api.controller.rest.owner.OwnerRestaurantAddressRestController.ADDRESS_ID;
import static pl.foodflow.api.controller.rest.owner.OwnerRestaurantAddressRestController.RESTAURANT_ADDRESSES;

public interface OwnerRestaurantAddressControllerTestSupport {

    RequestSpecification requestSpecification();

    default Response addDeliveryAddressToRestaurant(final AddressDTO addressDTO) {
        return requestSpecification()
                .body(addressDTO)
                .post(RESTAURANT_ADDRESSES)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();
    }

    default Response deleteRestaurantAddress(final Long addressId) {
        return requestSpecification()
                .pathParam("addressId", addressId)
                .delete(RESTAURANT_ADDRESSES + ADDRESS_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract()
                .response();
    }
}
