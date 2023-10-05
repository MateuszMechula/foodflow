package pl.foodflow.integration.support.owner;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.enums.OrderStatus;

import java.util.List;

import static pl.foodflow.api.controller.rest.owner.OwnerOrderRecordRestController.COMPLETE_ORDER;
import static pl.foodflow.api.controller.rest.owner.OwnerOrderRecordRestController.ORDER_RECORDS;

public interface OwnerOrderRecordControllerTestSupport {

    RequestSpecification requestSpecification();

    default List<OrderRecord> getAllOwnerOrdersWithStatus(
            final Integer userId,
            final OrderStatus status
    ) {
        return requestSpecification()
                .queryParam("userId", userId)
                .queryParam("status", status)
                .get(ORDER_RECORDS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .jsonPath().getList("", OrderRecord.class);
    }

    default Response completeOrder(final Long orderRecordId) {
        return requestSpecification()
                .pathParam("orderRecordId", orderRecordId)
                .post(ORDER_RECORDS + COMPLETE_ORDER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();
    }
}
