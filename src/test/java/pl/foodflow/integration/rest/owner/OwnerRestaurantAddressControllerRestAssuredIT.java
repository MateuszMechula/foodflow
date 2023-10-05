package pl.foodflow.integration.rest.owner;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.api.dto.AddressDTO;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.owner.OwnerRestaurantAddressControllerTestSupport;
import pl.foodflow.util.TestDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = "test.name=OwnerRestaurantAddressControllerRestAssuredIT")
public class OwnerRestaurantAddressControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements OwnerRestaurantAddressControllerTestSupport {

    @Test
    @Tag("owner")
    void shouldAddDeliveryAddressToRestaurant() {
        //given
        AddressDTO addressDTO = TestDataFactory.someAddressDTO1();
        //when
        Response response = addDeliveryAddressToRestaurant(addressDTO);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @Tag("owner")
    void shouldDeleteRestaurantAddress() {
        //given
        Long addressId = 4L;
        //when
        Response response = deleteRestaurantAddress(addressId);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
