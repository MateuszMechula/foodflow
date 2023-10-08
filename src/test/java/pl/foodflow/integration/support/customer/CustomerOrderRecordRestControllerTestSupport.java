package pl.foodflow.integration.support.customer;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.domain.OrderRecord;

import static pl.foodflow.api.controller.rest.customer.CustomerOrderRecordRestController.*;

public interface CustomerOrderRecordRestControllerTestSupport {
    RequestSpecification requestSpecification();


    default OrderRecord addOrderRecord(
            final Long restaurantId,
            final Long customerId,
            final OrderDTO orderDTO) {
        return requestSpecification()
                .pathParam("restaurantId", restaurantId)
                .pathParam("customerId", customerId)
                .body(orderDTO)
                .post(ORDER_RECORDS + RESTAURANT_ID + CUSTOMER_ID)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response()
                .as(OrderRecord.class);
    }

    default Response deleteOrderRecord(final Long orderRecordId) {
        return requestSpecification()
                .pathParam("orderRecordId", orderRecordId)
                .delete(ORDER_RECORDS + ORDER_RECORD_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract()
                .response();
    }
}
