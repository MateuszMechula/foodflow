package pl.foodflow.integration.support.owner;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.api.dto.AddressDTO;

import static pl.foodflow.api.controller.rest.owner.OwnerRestaurantAddressRestController.*;

public interface OwnerRestaurantAddressControllerTestSupport {

    RequestSpecification requestSpecification();

    default Response addDeliveryAddressToRestaurant(
            final AddressDTO addressDTO,
            final Long ownerId) {

        return requestSpecification()
                .pathParam("ownerId", ownerId)
                .body(addressDTO)
                .post(RESTAURANT_ADDRESSES + OWNER_ID)
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
