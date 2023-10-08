package pl.foodflow.integration.rest.owner;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.owner.OwnerOrderRecordControllerTestSupport;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = "test.name=OwnerOrderRecordControllerRestAssuredIT")
public class OwnerOrderRecordControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements OwnerOrderRecordControllerTestSupport {

    @Test
    void shouldGetOrderRecordById() {
        //given
        Long orderRecordId = 1L;
        //when
        OrderRecord orderRecord = getOrderRecordById(orderRecordId);
        //then
        assertThat(orderRecord).isNotNull();
    }

    @Test
    void shouldGetAllOwnerOrdersWithStatus() {
        //given
        Integer userId = 1;
        OrderStatus inProgress = OrderStatus.IN_PROGRESS;
        //when
        List<OrderRecord> allOwnerOrdersWithStatusInProgress = getAllOwnerOrdersWithStatus(userId, inProgress);
        //then
        assertThat(allOwnerOrdersWithStatusInProgress).isNotNull();
    }

    @Test
    void shouldCompleteOrder() {
        //given
        Long orderRecordId = 2L;
        //when
        Response response = completeOrder(orderRecordId);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}
