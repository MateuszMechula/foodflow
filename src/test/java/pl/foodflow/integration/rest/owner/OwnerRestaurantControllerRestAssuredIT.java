package pl.foodflow.integration.rest.owner;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.owner.OwnerRestaurantControllerTestSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.foodflow.util.TestDataFactory.someRestaurantDTO2;
import static pl.foodflow.util.TestDataFactory.someRestaurantDTO3;

@TestPropertySource(properties = "test.name=OwnerRestaurantAddressControllerRestAssuredIT")
public class OwnerRestaurantControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements OwnerRestaurantControllerTestSupport {

    @Test
    @Tag("owner")
    void shouldAddRestaurant() {
        //given
        RestaurantDTO restaurantDTO = someRestaurantDTO3();
        //when
        Response response = addRestaurant(restaurantDTO);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @Tag("owner")
    void shouldUpdateRestaurant() {
        //given
        RestaurantDTO restaurantDTO = someRestaurantDTO2();
        //when
        Response response = updateRestaurant(restaurantDTO);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Tag("owner")
    void shouldDeleteRestaurant() {
        //given
        Long restaurantId = 1L;
        //when
        Response response = deleteRestaurant(restaurantId);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
