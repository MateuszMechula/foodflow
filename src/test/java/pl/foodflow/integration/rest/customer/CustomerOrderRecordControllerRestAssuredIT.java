package pl.foodflow.integration.rest.customer;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.business.OrderRecordService;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.customer.CustomerOrderRecordRestControllerTestSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.foodflow.util.TestDataFactory.someOrderDTO;
import static pl.foodflow.util.TestDataFactory.someOrderIntegration2;

@TestPropertySource(properties = "test.name=CustomerOrderRecordControllerRestAssuredIT")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerOrderRecordControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements CustomerOrderRecordRestControllerTestSupport {

    private OrderRecordService orderRecordService;

    @Test
    @Tag("customer")
    void shouldAddOrderRecordCorrectly() {
        //given
        Long restaurantId = 1L;
        OrderDTO orderDTO = someOrderDTO();
        //when
        OrderRecord orderRecord = addOrderRecord(restaurantId, orderDTO);
        //then
        assertThat(orderRecord).isNotNull();
        assertThat(orderRecord.getOrderRecordId()).isNotNull().isGreaterThan(0);
        assertThat(orderRecord.getOrderNumber()).isNotNull();
        assertThat(orderRecord.getOrderStatus()).isEqualTo(OrderStatus.IN_PROGRESS.toString());
        assertThat(orderRecord.getDeliveryType()).isEqualTo(orderDTO.getDeliveryType().toString());
    }

    @Test
    @Tag("customer")
    void shouldDeleteOrderRecordCorrectly() {
        //given
        OrderRecord orderRecord = orderRecordService.saveOrderRecord(someOrderIntegration2());
        //when
        Response response = deleteOrderRecord(orderRecord.getOrderRecordId());
        //then
        response.then().statusCode(204);
    }

}
